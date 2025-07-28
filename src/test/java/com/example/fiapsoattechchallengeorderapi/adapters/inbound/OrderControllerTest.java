package com.example.fiapsoattechchallengeorderapi.adapters.inbound;

import com.example.fiapsoattechchallengeorderapi.adapters.inbound.request.OrderPaymentUpdateRequest;
import com.example.fiapsoattechchallengeorderapi.application.usecase.order.OrderUseCase;
import com.example.fiapsoattechchallengeorderapi.domain.order.OrderDTO;
import com.example.fiapsoattechchallengeorderapi.domain.order.OrderItemDTO;
import com.example.fiapsoattechchallengeorderapi.domain.order.OrderPaymentStatus;
import com.example.fiapsoattechchallengeorderapi.domain.order.OrderStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private OrderUseCase orderUseCase;

    private OrderDTO orderDTO;
    private OrderPaymentUpdateRequest paymentUpdateRequest;

    @BeforeEach
    void setUp() {
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setProductId(1L);
        orderItemDTO.setQuantity(2);

        orderDTO = new OrderDTO();
        orderDTO.setId(1L);
        orderDTO.setCustomerId(1L);
        orderDTO.setItems(Collections.singletonList(orderItemDTO));
        orderDTO.setStatus(OrderStatus.AGUARDANDO_PAGAMENTO);
        orderDTO.setPaymentStatus(OrderPaymentStatus.PENDING);
        orderDTO.setTotal(BigDecimal.valueOf(20.0));
        orderDTO.setCustomerEmail("test@example.com");
        orderDTO.setCreatedAt(LocalDateTime.now());

        paymentUpdateRequest = new OrderPaymentUpdateRequest();
        paymentUpdateRequest.setOrderId(1L);
        paymentUpdateRequest.setPaymentStatus("APPROVED");
        paymentUpdateRequest.setPaymentQrCode("qrcode");
    }

    @Test
    void createOrder_ShouldReturnCreatedOrder() throws Exception {
        when(orderUseCase.createOrder(any(OrderDTO.class))).thenReturn(orderDTO);

        mockMvc.perform(post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.customerId").value(1))
                .andExpect(jsonPath("$.status").value("AGUARDANDO_PAGAMENTO"))
                .andExpect(jsonPath("$.paymentStatus").value("PENDING"))
                .andExpect(jsonPath("$.total").value(20.0))
                .andExpect(jsonPath("$.customerEmail").value("test@example.com"));
    }

    @Test
    void updateOrderStatus_ShouldReturnUpdatedOrder() throws Exception {
        orderDTO.setStatus(OrderStatus.RECEBIDO);
        when(orderUseCase.updateOrderStatus(anyLong())).thenReturn(orderDTO);

        mockMvc.perform(patch("/order/order-status/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value("RECEBIDO"));
    }

    @Test
    void findOrderById_ShouldReturnOrder() throws Exception {
        when(orderUseCase.findOrderById(anyLong())).thenReturn(orderDTO);

        mockMvc.perform(get("/order/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.customerId").value(1))
                .andExpect(jsonPath("$.status").value("AGUARDANDO_PAGAMENTO"));
    }

    @Test
    void findOrderByStatus_ShouldReturnOrdersWithStatus() throws Exception {
        List<OrderDTO> orders = Collections.singletonList(orderDTO);
        when(orderUseCase.findOrderByStatus(any(OrderStatus.class))).thenReturn(orders);

        mockMvc.perform(get("/order/order-status/AGUARDANDO_PAGAMENTO"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].status").value("AGUARDANDO_PAGAMENTO"));
    }

    @Test
    void cancelOrder_ShouldReturnCancelledOrder() throws Exception {
        orderDTO.setStatus(OrderStatus.CANCELADO);
        orderDTO.setPaymentStatus(OrderPaymentStatus.REJECTED);
        when(orderUseCase.cancelOrder(anyLong())).thenReturn(orderDTO);

        mockMvc.perform(patch("/order/cancel/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value("CANCELADO"))
                .andExpect(jsonPath("$.paymentStatus").value("REJECTED"));
    }

    @Test
    void updateOrderPayment_ShouldReturnUpdatedOrder() throws Exception {
        orderDTO.setStatus(OrderStatus.RECEBIDO);
        orderDTO.setPaymentStatus(OrderPaymentStatus.APPROVED);
        when(orderUseCase.updateOrderPayment(any(OrderPaymentUpdateRequest.class))).thenReturn(orderDTO);

        mockMvc.perform(patch("/order/payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentUpdateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value("RECEBIDO"))
                .andExpect(jsonPath("$.paymentStatus").value("APPROVED"));
    }
}