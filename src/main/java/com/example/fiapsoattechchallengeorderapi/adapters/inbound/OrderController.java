package com.example.fiapsoattechchallengeorderapi.adapters.inbound;

import com.example.fiapsoattechchallengeorderapi.adapters.inbound.request.OrderPaymentUpdateRequest;
import com.example.fiapsoattechchallengeorderapi.application.usecase.order.OrderUseCase;
import com.example.fiapsoattechchallengeorderapi.domain.order.OrderDTO;
import com.example.fiapsoattechchallengeorderapi.domain.order.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderUseCase orderUseCase;

    @PostMapping()
    public OrderDTO createOrder(@RequestBody OrderDTO orderDTO) {
        return orderUseCase.createOrder(orderDTO);
    }

    @PatchMapping("/order-status/{id}")
    public OrderDTO updateOrderStatus(@PathVariable Long id) {
        return orderUseCase.updateOrderStatus(id);
    }

    @GetMapping("/{id}")
    public OrderDTO findOrderById(@PathVariable Long id) {
        return orderUseCase.findOrderById(id);
    }

    @GetMapping("/order-status/{status}")
    public List<OrderDTO> findOrderByStatus(@PathVariable OrderStatus status) {
        return orderUseCase.findOrderByStatus(status);
    }

    @PatchMapping("/cancel/{id}")
    public OrderDTO updateOrderPayment(@PathVariable Long id) {
        return orderUseCase.cancelOrder(id);
    }

    @PatchMapping("/payment")
    public OrderDTO updateOrderPayment(@RequestBody OrderPaymentUpdateRequest orderPaymentUpdateRequest) {
        return orderUseCase.updateOrderPayment(orderPaymentUpdateRequest);
    }
}