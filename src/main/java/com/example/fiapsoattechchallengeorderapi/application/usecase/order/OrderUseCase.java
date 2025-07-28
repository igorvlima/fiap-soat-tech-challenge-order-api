package com.example.fiapsoattechchallengeorderapi.application.usecase.order;

import com.example.fiapsoattechchallengeorderapi.adapters.inbound.request.OrderPaymentUpdateRequest;
import com.example.fiapsoattechchallengeorderapi.domain.customer.Customer;
import com.example.fiapsoattechchallengeorderapi.domain.order.OrderDTO;
import com.example.fiapsoattechchallengeorderapi.domain.order.OrderItemDTO;
import com.example.fiapsoattechchallengeorderapi.domain.order.OrderStatus;
import com.example.fiapsoattechchallengeorderapi.domain.product.Product;

import java.math.BigDecimal;
import java.util.List;

public interface OrderUseCase {

    OrderDTO createOrder(OrderDTO customerDTO);

    OrderDTO updateOrderStatus(Long id);

    OrderDTO cancelOrder(Long id);

    OrderDTO updateOrderPayment(OrderPaymentUpdateRequest orderPaymentUpdateRequest);

    OrderDTO findOrderById(Long id);

    BigDecimal calculateTotalValue(List<Product> productList, List<OrderItemDTO> orderItemDTOS);

    List<OrderDTO> findOrderByStatus(OrderStatus status);

    OrderStatus getNextOrderStatus(OrderStatus status);

    Customer validateCustomer(Long customerId);

    List<Product> getProducts(List<Long> productIds);
}
