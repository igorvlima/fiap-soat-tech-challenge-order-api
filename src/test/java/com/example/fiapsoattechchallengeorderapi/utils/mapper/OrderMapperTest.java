package com.example.fiapsoattechchallengeorderapi.utils.mapper;

import static org.junit.jupiter.api.Assertions.*;

import com.example.fiapsoattechchallengeorderapi.domain.order.*;
import com.example.fiapsoattechchallengeorderapi.utils.mappers.OrderMapper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

class OrderMapperTest {

    @Test
    void shouldMapDTOToDomainSuccessfully() {
        OrderDTO dto = new OrderDTO();
        dto.setCustomerId(1L);
        dto.setCustomerEmail("customer@example.com");
        dto.setTotal(BigDecimal.valueOf(100.00));
        dto.setStatus(OrderStatus.RECEBIDO);
        dto.setItems(List.of(new OrderItemDTO(1L, 1L, 1L, 2, LocalDateTime.now())));
        dto.setCreatedAt(LocalDateTime.now());

        OrderMapper mapper = new OrderMapper();
        Order order = mapper.DTOToDomain(dto);

        assertNotNull(order);
        assertEquals(dto.getCustomerId(), order.getCustomerId());
        assertEquals(dto.getCustomerEmail(), order.getCustomerEmail());
        assertEquals(dto.getTotal(), order.getTotal());
        assertEquals(dto.getStatus(), order.getStatus());
        assertEquals(dto.getItems().size(), order.getItems().size());
    }

    @Test
    void shouldReturnEmptyDomainWhenDTOHasNoItems() {
        OrderDTO dto = new OrderDTO();
        dto.setCustomerId(1L);
        dto.setCustomerEmail("customer@example.com");
        dto.setTotal(BigDecimal.valueOf(100.00));
        dto.setStatus(OrderStatus.RECEBIDO);
        dto.setItems(Collections.emptyList());
        dto.setCreatedAt(LocalDateTime.now());

        OrderMapper mapper = new OrderMapper();
        Order order = mapper.DTOToDomain(dto);

        assertNotNull(order);
        assertTrue(order.getItems().isEmpty());
    }

    @Test
    void shouldMapDomainToDTOSuccessfully() {
        Order order = new Order();
        order.setCustomerId(1L);
        order.setCustomerEmail("customer@example.com");
        order.setTotal(BigDecimal.valueOf(100.00));
        order.setStatus(OrderStatus.RECEBIDO);
        order.setItems(List.of(new OrderItem(1L, 1L, 1L, 2, LocalDateTime.now())));
        order.setCreatedAt(LocalDateTime.now());

        OrderMapper mapper = new OrderMapper();
        OrderDTO dto = mapper.domainToDTO(order);

        assertNotNull(dto);
        assertEquals(order.getCustomerId(), dto.getCustomerId());
        assertEquals(order.getCustomerEmail(), dto.getCustomerEmail());
        assertEquals(order.getTotal(), dto.getTotal());
        assertEquals(order.getStatus(), dto.getStatus());
        assertEquals(order.getItems().size(), dto.getItems().size());
    }

    @Test
    void shouldReturnEmptyDTOWhenDomainHasNoItems() {
        Order order = new Order();
        order.setCustomerId(1L);
        order.setCustomerEmail("customer@example.com");
        order.setTotal(BigDecimal.valueOf(100.00));
        order.setStatus(OrderStatus.RECEBIDO);
        order.setItems(Collections.emptyList());
        order.setCreatedAt(LocalDateTime.now());

        OrderMapper mapper = new OrderMapper();
        OrderDTO dto = mapper.domainToDTO(order);

        assertNotNull(dto);
        assertTrue(dto.getItems().isEmpty());
    }
}