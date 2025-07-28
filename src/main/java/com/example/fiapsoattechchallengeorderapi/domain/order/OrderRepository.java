package com.example.fiapsoattechchallengeorderapi.domain.order;

import java.util.List;

public interface OrderRepository {
    Order save(Order order);
    Order findById(Long id);
    List<Order> findOrderByStatus(OrderStatus orderStatus);
}
