package com.example.fiapsoattechchallengeorderapi.utils.mapper;

import static org.junit.jupiter.api.Assertions.*;

import com.example.fiapsoattechchallengeorderapi.adapters.outbound.entities.JpaOrderEntity;
import com.example.fiapsoattechchallengeorderapi.adapters.outbound.entities.JpaOrderItemEntity;
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

    @Test
    void shouldMapDomainToJpaEntitySuccessfully() {
        Order order = new Order();
        order.setId(1L);
        order.setCustomerId(2L);
        order.setCustomerEmail("customer@example.com");
        order.setTotal(BigDecimal.valueOf(150.00));
        order.setStatus(OrderStatus.RECEBIDO);
        order.setPaymentType(OrderPaymentType.CREDIT_CARD);
        order.setPaymentStatus(OrderPaymentStatus.APPROVED);
        order.setPaymentQrCode("QR123");
        order.setWaitingTimeInMinutes(30L);
        order.setCreatedAt(LocalDateTime.now());

        OrderMapper mapper = new OrderMapper();
        JpaOrderEntity jpaOrderEntity = mapper.domainToJpa(order);

        assertNotNull(jpaOrderEntity);
        assertEquals(order.getId(), jpaOrderEntity.getId());
        assertEquals(order.getCustomerId(), jpaOrderEntity.getCustomerId());
        assertEquals(order.getCustomerEmail(), jpaOrderEntity.getCustomerEmail());
        assertEquals(order.getTotal(), jpaOrderEntity.getTotal());
        assertEquals(order.getStatus(), jpaOrderEntity.getStatus());
        assertEquals(order.getPaymentType(), jpaOrderEntity.getPaymentType());
        assertEquals(order.getPaymentStatus(), jpaOrderEntity.getPaymentStatus());
        assertEquals(order.getPaymentQrCode(), jpaOrderEntity.getPaymentQrCode());
        assertEquals(order.getWaitingTimeInMinutes(), jpaOrderEntity.getWaitingTimeInMinutes());
        assertEquals(order.getCreatedAt(), jpaOrderEntity.getCreatedAt());
    }

    @Test
    void shouldReturnDomainWithEmptyItemsWhenJpaEntityHasNoMatchingItems() {
        JpaOrderEntity jpaOrderEntity = new JpaOrderEntity();
        jpaOrderEntity.setId(1L);
        jpaOrderEntity.setCustomerId(2L);
        jpaOrderEntity.setCustomerEmail("customer@example.com");
        jpaOrderEntity.setTotal(BigDecimal.valueOf(150.00));
        jpaOrderEntity.setStatus(OrderStatus.RECEBIDO);
        jpaOrderEntity.setPaymentType(OrderPaymentType.CREDIT_CARD);
        jpaOrderEntity.setPaymentStatus(OrderPaymentStatus.APPROVED);
        jpaOrderEntity.setPaymentQrCode("QR123");
        jpaOrderEntity.setWaitingTimeInMinutes(30L);
        jpaOrderEntity.setCreatedAt(LocalDateTime.now());

        JpaOrderItemEntity itemEntity = new JpaOrderItemEntity();
        itemEntity.setId(1L);
        itemEntity.setOrderId(2L);
        itemEntity.setProductId(10L);
        itemEntity.setQuantity(2);
        itemEntity.setCreatedAt(LocalDateTime.now());

        OrderMapper mapper = new OrderMapper();
        Order order = mapper.jpaToDomain(jpaOrderEntity, List.of(itemEntity));

        assertNotNull(order);
        assertEquals(jpaOrderEntity.getId(), order.getId());
        assertTrue(order.getItems().isEmpty());
    }

    @Test
    void shouldMapJpaEntityListToDomainListSuccessfully() {
        JpaOrderEntity jpaOrderEntity1 = new JpaOrderEntity();
        jpaOrderEntity1.setId(1L);
        jpaOrderEntity1.setCustomerId(2L);
        jpaOrderEntity1.setCustomerEmail("customer1@example.com");
        jpaOrderEntity1.setTotal(BigDecimal.valueOf(100.00));
        jpaOrderEntity1.setStatus(OrderStatus.RECEBIDO);
        jpaOrderEntity1.setCreatedAt(LocalDateTime.now());

        JpaOrderEntity jpaOrderEntity2 = new JpaOrderEntity();
        jpaOrderEntity2.setId(2L);
        jpaOrderEntity2.setCustomerId(3L);
        jpaOrderEntity2.setCustomerEmail("customer2@example.com");
        jpaOrderEntity2.setTotal(BigDecimal.valueOf(200.00));
        jpaOrderEntity2.setStatus(OrderStatus.RECEBIDO);
        jpaOrderEntity2.setCreatedAt(LocalDateTime.now());

        JpaOrderItemEntity itemEntity1 = new JpaOrderItemEntity();
        itemEntity1.setId(1L);
        itemEntity1.setOrderId(1L);
        itemEntity1.setProductId(10L);
        itemEntity1.setQuantity(2);
        itemEntity1.setCreatedAt(LocalDateTime.now());

        JpaOrderItemEntity itemEntity2 = new JpaOrderItemEntity();
        itemEntity2.setId(2L);
        itemEntity2.setOrderId(2L);
        itemEntity2.setProductId(20L);
        itemEntity2.setQuantity(1);
        itemEntity2.setCreatedAt(LocalDateTime.now());

        OrderMapper mapper = new OrderMapper();
        List<Order> orders = mapper.jpaToDomainList(List.of(jpaOrderEntity1, jpaOrderEntity2), List.of(itemEntity1, itemEntity2));

        assertNotNull(orders);
        assertEquals(2, orders.size());
        assertEquals(jpaOrderEntity1.getId(), orders.get(0).getId());
        assertEquals(jpaOrderEntity2.getId(), orders.get(1).getId());
        assertEquals(1, orders.get(0).getItems().size());
        assertEquals(1, orders.get(1).getItems().size());
    }

    @Test
    void shouldReturnEmptyDomainListWhenJpaEntityListIsEmpty() {
        OrderMapper mapper = new OrderMapper();
        List<Order> orders = mapper.jpaToDomainList(Collections.emptyList(), Collections.emptyList());

        assertNotNull(orders);
        assertTrue(orders.isEmpty());
    }

    @Test
    void shouldReturnDomainListWithEmptyItemsWhenJpaEntitiesHaveNoMatchingItems() {
        JpaOrderEntity jpaOrderEntity1 = new JpaOrderEntity();
        jpaOrderEntity1.setId(1L);
        jpaOrderEntity1.setCustomerId(2L);
        jpaOrderEntity1.setCustomerEmail("customer1@example.com");
        jpaOrderEntity1.setTotal(BigDecimal.valueOf(100.00));
        jpaOrderEntity1.setStatus(OrderStatus.RECEBIDO);
        jpaOrderEntity1.setCreatedAt(LocalDateTime.now());

        JpaOrderEntity jpaOrderEntity2 = new JpaOrderEntity();
        jpaOrderEntity2.setId(2L);
        jpaOrderEntity2.setCustomerId(3L);
        jpaOrderEntity2.setCustomerEmail("customer2@example.com");
        jpaOrderEntity2.setTotal(BigDecimal.valueOf(200.00));
        jpaOrderEntity2.setStatus(OrderStatus.RECEBIDO);
        jpaOrderEntity2.setCreatedAt(LocalDateTime.now());

        JpaOrderItemEntity itemEntity1 = new JpaOrderItemEntity();
        itemEntity1.setId(1L);
        itemEntity1.setOrderId(3L); // No matching orderId
        itemEntity1.setProductId(10L);
        itemEntity1.setQuantity(2);
        itemEntity1.setCreatedAt(LocalDateTime.now());

        OrderMapper mapper = new OrderMapper();
        List<Order> orders = mapper.jpaToDomainList(List.of(jpaOrderEntity1, jpaOrderEntity2), List.of(itemEntity1));

        assertNotNull(orders);
        assertEquals(2, orders.size());
        assertTrue(orders.get(0).getItems().isEmpty());
        assertTrue(orders.get(1).getItems().isEmpty());
    }

    @Test
    void shouldMapDomainListToDTOListSuccessfully() {
        Order order1 = new Order();
        order1.setId(1L);
        order1.setCustomerId(2L);
        order1.setCustomerEmail("customer1@example.com");
        order1.setTotal(BigDecimal.valueOf(100.00));
        order1.setStatus(OrderStatus.RECEBIDO);
        order1.setItems(List.of(new OrderItem(1L, 1L, 10L, 2, LocalDateTime.now())));
        order1.setCreatedAt(LocalDateTime.now());

        Order order2 = new Order();
        order2.setId(2L);
        order2.setCustomerId(3L);
        order2.setCustomerEmail("customer2@example.com");
        order2.setTotal(BigDecimal.valueOf(200.00));
        order2.setStatus(OrderStatus.RECEBIDO);
        order2.setItems(List.of(new OrderItem(2L, 2L, 20L, 1, LocalDateTime.now())));
        order2.setCreatedAt(LocalDateTime.now());

        OrderMapper mapper = new OrderMapper();
        List<OrderDTO> dtos = mapper.domainToDTOList(List.of(order1, order2));

        assertNotNull(dtos);
        assertEquals(2, dtos.size());
        assertEquals(order1.getId(), dtos.get(0).getId());
        assertEquals(order2.getId(), dtos.get(1).getId());
        assertEquals(order1.getItems().size(), dtos.get(0).getItems().size());
        assertEquals(order2.getItems().size(), dtos.get(1).getItems().size());
    }

    @Test
    void shouldReturnEmptyDTOListWhenDomainListIsEmpty() {
        OrderMapper mapper = new OrderMapper();
        List<OrderDTO> dtos = mapper.domainToDTOList(Collections.emptyList());

        assertNotNull(dtos);
        assertTrue(dtos.isEmpty());
    }
}