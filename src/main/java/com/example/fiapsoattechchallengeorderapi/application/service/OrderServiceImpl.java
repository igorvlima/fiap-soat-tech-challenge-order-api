package com.example.fiapsoattechchallengeorderapi.application.service;

import com.example.fiapsoattechchallengeorderapi.adapters.inbound.request.OrderPaymentUpdateRequest;
import com.example.fiapsoattechchallengeorderapi.application.usecase.customer.CustomerUseCase;
import com.example.fiapsoattechchallengeorderapi.application.usecase.order.OrderUseCase;
import com.example.fiapsoattechchallengeorderapi.application.usecase.product.ProductUseCase;
import com.example.fiapsoattechchallengeorderapi.domain.customer.Customer;
import com.example.fiapsoattechchallengeorderapi.domain.order.*;
import com.example.fiapsoattechchallengeorderapi.domain.product.Product;
import com.example.fiapsoattechchallengeorderapi.exception.OrderPaymentUpdateException;
import com.example.fiapsoattechchallengeorderapi.exception.OrderStatusUpdateException;
import com.example.fiapsoattechchallengeorderapi.utils.mappers.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderUseCase {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final CustomerUseCase customerUseCase;
    private final ProductUseCase productUseCase;

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {

        var customer = validateCustomer(orderDTO.getCustomerId());
        var products = getProducts(orderDTO.getItems().stream().map(OrderItemDTO::getProductId).toList());

        orderDTO.setPaymentStatus(OrderPaymentStatus.PENDING);
        orderDTO.setStatus(OrderStatus.AGUARDANDO_PAGAMENTO);
        orderDTO.setPaymentType(orderDTO.getPaymentType());
        orderDTO.setTotal(calculateTotalValue(products, orderDTO.getItems()));
        orderDTO.setCustomerEmail(customer.getEmail());
        orderDTO.setCreatedAt(LocalDateTime.now());

        var order = orderRepository.save(orderMapper.DTOToDomain(orderDTO));
        return orderMapper.domainToDTO(order);
    }

    @Override
    public OrderDTO updateOrderStatus(Long id) {

        var order = orderRepository.findById(id);

        if (order.getStatus() == OrderStatus.FINALIZADO || order.getStatus() == OrderStatus.AGUARDANDO_PAGAMENTO) {
            var message = String.format("O pedido não pode ser atualizado pois está com o status %s.", order.getStatus());
            throw new OrderStatusUpdateException(message);
        }

        OrderStatus nextStatus = getNextOrderStatus(order.getStatus());

        order.setStatus(nextStatus);

        return orderMapper.domainToDTO(orderRepository.save(order));
    }

    @Override
    public OrderDTO cancelOrder(Long id) {
        var order = orderRepository.findById(id);
        if (order.getStatus() == OrderStatus.FINALIZADO || order.getStatus() == OrderStatus.CANCELADO) {
            var message = String.format("O pedido não pode ser cancelado pois está com o status %s.", order.getStatus());
            throw new OrderStatusUpdateException(message);
        }
        order.setStatus(OrderStatus.CANCELADO);
        order.setPaymentStatus(OrderPaymentStatus.REJECTED);
        return orderMapper.domainToDTO(orderRepository.save(order));
    }

    @Override
    public OrderDTO updateOrderPayment(OrderPaymentUpdateRequest request) {
        Order order = orderRepository.findById(request.getOrderId());

        if (isPaymentRejected(request.getPaymentStatus())) {
            return cancelOrder(order.getId());
        }

        validateOrderCanBeUpdated(order);
        updateOrderPaymentDetails(order, request);

        return orderMapper.domainToDTO(orderRepository.save(order));
    }

    @Override
    public OrderStatus getNextOrderStatus(OrderStatus status) {
        OrderStatus[] statuses = OrderStatus.values();
        int currentIndex = status.ordinal();

        if (currentIndex + 1 >= statuses.length) {
            throw new IllegalStateException("Não é possível avançar o status do pedido.");
        }

        return statuses[currentIndex + 1];
    }

    @Override
    public OrderDTO findOrderById(Long orderId) {
        return orderMapper.domainToDTO(orderRepository.findById(orderId));
    }

    @Override
    public BigDecimal calculateTotalValue(List<Product> productList, List<OrderItemDTO> orderItemDTOS) {
        Map<Long, BigDecimal> productPriceMap = productList.stream()
                .collect(Collectors.toMap(Product::getId, Product::getPrice));

        return orderItemDTOS.stream()
                .map(item -> {
                    BigDecimal price = productPriceMap.getOrDefault(item.getProductId(), BigDecimal.ZERO);
                    return price.multiply(BigDecimal.valueOf(item.getQuantity()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public List<OrderDTO> findOrderByStatus(OrderStatus status) {
        var orders =  orderRepository.findOrderByStatus(status);
        return orderMapper.domainToDTOList(orders);
    }

    @Override
    public Customer validateCustomer(Long customerId) {
        return customerUseCase.fetchCustomerById(customerId);
    }

    @Override
    public List<Product> getProducts(List<Long> productIds) {
        return productIds.stream()
                .map(productUseCase::fetchProductById)
                .collect(Collectors.toList());
    }

    private void validateOrderCanBeUpdated(Order order) {
        if (order.getStatus() != OrderStatus.AGUARDANDO_PAGAMENTO ||
                order.getPaymentStatus() == OrderPaymentStatus.APPROVED) {

            String message = String.format(
                    "O pedido não pode ser atualizado pois está com o status %s.",
                    order.getStatus()
            );
            throw new OrderPaymentUpdateException(message);
        }
    }

    private void updateOrderPaymentDetails(Order order, OrderPaymentUpdateRequest request) {
        order.setPaymentQrCode(request.getPaymentQrCode());

        OrderPaymentStatus newPaymentStatus = OrderPaymentStatus.valueOf(request.getPaymentStatus());
        order.setPaymentStatus(newPaymentStatus);

        if (newPaymentStatus == OrderPaymentStatus.APPROVED) {
            order.setStatus(OrderStatus.RECEBIDO);
        }
    }

    private boolean isPaymentRejected(String paymentStatus) {
        return "REJECTED".equals(paymentStatus);
    }
}
