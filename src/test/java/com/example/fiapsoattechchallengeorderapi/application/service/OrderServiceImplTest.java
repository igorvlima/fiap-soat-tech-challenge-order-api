package com.example.fiapsoattechchallengeorderapi.application.service;

import com.example.fiapsoattechchallengeorderapi.adapters.inbound.request.OrderPaymentUpdateRequest;
import com.example.fiapsoattechchallengeorderapi.application.usecase.customer.CustomerUseCase;
import com.example.fiapsoattechchallengeorderapi.application.usecase.product.ProductUseCase;
import com.example.fiapsoattechchallengeorderapi.domain.customer.Customer;
import com.example.fiapsoattechchallengeorderapi.domain.order.*;
import com.example.fiapsoattechchallengeorderapi.domain.product.Product;
import com.example.fiapsoattechchallengeorderapi.exception.OrderPaymentUpdateException;
import com.example.fiapsoattechchallengeorderapi.exception.OrderStatusUpdateException;
import com.example.fiapsoattechchallengeorderapi.utils.mappers.OrderMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private CustomerUseCase customerUseCase;

    @Mock
    private ProductUseCase productUseCase;

    @InjectMocks
    private OrderServiceImpl orderService;

    private OrderDTO orderDTO;
    private Order order;
    private Customer customer;
    private Product product;
    private OrderItemDTO orderItemDTO;
    private OrderItem orderItem;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId(1L);
        customer.setEmail("test@example.com");
        customer.setName("Test Customer");

        product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setPrice(BigDecimal.valueOf(10.0));

        orderItemDTO = new OrderItemDTO();
        orderItemDTO.setProductId(1L);
        orderItemDTO.setQuantity(2);

        orderItem = new OrderItem();
        orderItem.setId(1L);
        orderItem.setProductId(1L);
        orderItem.setQuantity(2);

        orderDTO = new OrderDTO();
        orderDTO.setId(1L);
        orderDTO.setCustomerId(1L);
        orderDTO.setItems(Collections.singletonList(orderItemDTO));
        orderDTO.setPaymentType(OrderPaymentType.PIX);
        orderDTO.setStatus(OrderStatus.AGUARDANDO_PAGAMENTO);
        orderDTO.setPaymentStatus(OrderPaymentStatus.PENDING);
        orderDTO.setTotal(BigDecimal.valueOf(20.0));
        orderDTO.setCustomerEmail("test@example.com");
        orderDTO.setCreatedAt(LocalDateTime.now());

        order = new Order();
        order.setId(1L);
        order.setCustomerId(1L);
        order.setItems(Collections.singletonList(orderItem));
        order.setPaymentType(OrderPaymentType.PIX);
        order.setStatus(OrderStatus.AGUARDANDO_PAGAMENTO);
        order.setPaymentStatus(OrderPaymentStatus.PENDING);
        order.setTotal(BigDecimal.valueOf(20.0));
        order.setCustomerEmail("test@example.com");
        order.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void createOrder_ShouldCreateOrderSuccessfully() {
        when(customerUseCase.fetchCustomerById(anyLong())).thenReturn(customer);
        when(productUseCase.fetchProductById(anyLong())).thenReturn(product);
        when(orderMapper.DTOToDomain(any(OrderDTO.class))).thenReturn(order);
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderMapper.domainToDTO(any(Order.class))).thenReturn(orderDTO);

        OrderDTO result = orderService.createOrder(orderDTO);

        assertNotNull(result);
        assertEquals(OrderStatus.AGUARDANDO_PAGAMENTO, result.getStatus());
        assertEquals(OrderPaymentStatus.PENDING, result.getPaymentStatus());
        assertEquals(BigDecimal.valueOf(20.0), result.getTotal());
        assertEquals("test@example.com", result.getCustomerEmail());

        verify(customerUseCase).fetchCustomerById(1L);
        verify(productUseCase).fetchProductById(1L);
        verify(orderMapper).DTOToDomain(any(OrderDTO.class));
        verify(orderRepository).save(any(Order.class));
        verify(orderMapper).domainToDTO(any(Order.class));
    }

    @Test
    void updateOrderStatus_ShouldUpdateStatusSuccessfully() {
        order.setStatus(OrderStatus.RECEBIDO);
        when(orderRepository.findById(anyLong())).thenReturn(order);
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderMapper.domainToDTO(any(Order.class))).thenReturn(orderDTO);

        OrderDTO result = orderService.updateOrderStatus(1L);

        assertNotNull(result);
        verify(orderRepository).findById(1L);
        verify(orderRepository).save(any(Order.class));
        verify(orderMapper).domainToDTO(any(Order.class));
    }

    @Test
    void updateOrderStatus_ShouldThrowExceptionWhenOrderIsFinalized() {
        order.setStatus(OrderStatus.FINALIZADO);
        when(orderRepository.findById(anyLong())).thenReturn(order);

        assertThrows(OrderStatusUpdateException.class, () -> orderService.updateOrderStatus(1L));
        verify(orderRepository).findById(1L);
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void cancelOrder_ShouldCancelOrderSuccessfully() {
        order.setStatus(OrderStatus.RECEBIDO);
        when(orderRepository.findById(anyLong())).thenReturn(order);
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderMapper.domainToDTO(any(Order.class))).thenReturn(orderDTO);

        OrderDTO result = orderService.cancelOrder(1L);

        assertNotNull(result);
        verify(orderRepository).findById(1L);
        verify(orderRepository).save(any(Order.class));
        verify(orderMapper).domainToDTO(any(Order.class));
    }

    @Test
    void cancelOrder_ShouldThrowExceptionWhenOrderIsFinalized() {
        order.setStatus(OrderStatus.FINALIZADO);
        when(orderRepository.findById(anyLong())).thenReturn(order);

        assertThrows(OrderStatusUpdateException.class, () -> orderService.cancelOrder(1L));
        verify(orderRepository).findById(1L);
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void updateOrderPayment_ShouldUpdatePaymentSuccessfully() {
        OrderPaymentUpdateRequest request = new OrderPaymentUpdateRequest();
        request.setOrderId(1L);
        request.setPaymentStatus("APPROVED");
        request.setPaymentQrCode("qrcode");

        when(orderRepository.findById(anyLong())).thenReturn(order);
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderMapper.domainToDTO(any(Order.class))).thenReturn(orderDTO);

        OrderDTO result = orderService.updateOrderPayment(request);

        assertNotNull(result);
        verify(orderRepository).findById(1L);
        verify(orderRepository).save(any(Order.class));
        verify(orderMapper).domainToDTO(any(Order.class));
    }

    @Test
    void updateOrderPayment_ShouldCancelOrderWhenPaymentIsRejected() {
        OrderPaymentUpdateRequest request = new OrderPaymentUpdateRequest();
        request.setOrderId(1L);
        request.setPaymentStatus("REJECTED");

        when(orderRepository.findById(anyLong())).thenReturn(order);
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderMapper.domainToDTO(any(Order.class))).thenReturn(orderDTO);

        OrderDTO result = orderService.updateOrderPayment(request);

        assertNotNull(result);

        verify(orderRepository, times(2)).findById(1L);
        verify(orderRepository).save(any(Order.class));
        verify(orderMapper).domainToDTO(any(Order.class));
    }

    @Test
    void updateOrderPayment_ShouldThrowExceptionWhenOrderCannotBeUpdated() {
        OrderPaymentUpdateRequest request = new OrderPaymentUpdateRequest();
        request.setOrderId(1L);
        request.setPaymentStatus("APPROVED");

        order.setStatus(OrderStatus.RECEBIDO);
        when(orderRepository.findById(anyLong())).thenReturn(order);

        assertThrows(OrderPaymentUpdateException.class, () -> orderService.updateOrderPayment(request));
        verify(orderRepository).findById(1L);
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void getNextOrderStatus_ShouldReturnNextStatus() {
        OrderStatus result = orderService.getNextOrderStatus(OrderStatus.RECEBIDO);

        assertEquals(OrderStatus.PREPARACAO, result);
    }

    @Test
    void getNextOrderStatus_ShouldThrowExceptionWhenNoNextStatus() {
        assertThrows(IllegalStateException.class, () -> orderService.getNextOrderStatus(OrderStatus.FINALIZADO));
    }

    @Test
    void findOrderById_ShouldReturnOrder() {
        when(orderRepository.findById(anyLong())).thenReturn(order);
        when(orderMapper.domainToDTO(any(Order.class))).thenReturn(orderDTO);

        OrderDTO result = orderService.findOrderById(1L);

        assertNotNull(result);
        verify(orderRepository).findById(1L);
        verify(orderMapper).domainToDTO(any(Order.class));
    }

    @Test
    void calculateTotalValue_ShouldCalculateCorrectTotal() {
        List<Product> products = Collections.singletonList(product);
        List<OrderItemDTO> items = Collections.singletonList(orderItemDTO);

        BigDecimal result = orderService.calculateTotalValue(products, items);

        assertEquals(BigDecimal.valueOf(20.0), result);
    }

    @Test
    void findOrderByStatus_ShouldReturnOrdersWithStatus() {
        List<Order> orders = Collections.singletonList(order);
        List<OrderDTO> orderDTOs = Collections.singletonList(orderDTO);

        when(orderRepository.findOrderByStatus(any(OrderStatus.class))).thenReturn(orders);
        when(orderMapper.domainToDTOList(anyList())).thenReturn(orderDTOs);

        List<OrderDTO> result = orderService.findOrderByStatus(OrderStatus.AGUARDANDO_PAGAMENTO);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(orderRepository).findOrderByStatus(OrderStatus.AGUARDANDO_PAGAMENTO);
        verify(orderMapper).domainToDTOList(orders);
    }

    @Test
    void validateCustomer_ShouldReturnCustomer() {
        when(customerUseCase.fetchCustomerById(anyLong())).thenReturn(customer);

        Customer result = orderService.validateCustomer(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(customerUseCase).fetchCustomerById(1L);
    }

    @Test
    void getProducts_ShouldReturnProducts() {
        when(productUseCase.fetchProductById(anyLong())).thenReturn(product);

        List<Product> result = orderService.getProducts(Collections.singletonList(1L));

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        verify(productUseCase).fetchProductById(1L);
    }
}