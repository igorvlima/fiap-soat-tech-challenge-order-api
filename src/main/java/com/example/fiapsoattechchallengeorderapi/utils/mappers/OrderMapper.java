package com.example.fiapsoattechchallengeorderapi.utils.mappers;

import com.example.fiapsoattechchallengeorderapi.adapters.outbound.entities.JpaOrderEntity;
import com.example.fiapsoattechchallengeorderapi.adapters.outbound.entities.JpaOrderItemEntity;
import com.example.fiapsoattechchallengeorderapi.domain.order.Order;
import com.example.fiapsoattechchallengeorderapi.domain.order.OrderDTO;
import com.example.fiapsoattechchallengeorderapi.domain.order.OrderItem;
import com.example.fiapsoattechchallengeorderapi.domain.order.OrderItemDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public Order DTOToDomain(OrderDTO dto){
        Order order = new Order();
        order.setCustomerId(dto.getCustomerId());
        order.setCustomerEmail(dto.getCustomerEmail());
        order.setTotal(dto.getTotal());
        order.setStatus(dto.getStatus());
        order.setPaymentStatus(dto.getPaymentStatus());
        order.setPaymentType(dto.getPaymentType());
        order.setPaymentQrCode(dto.getPaymentQrCode());
        order.setWaitingTimeInMinutes(dto.getWaitingTimeInMinutes());
        order.setItems(dto.getItems().stream()
                .map(itemDTO -> new OrderItem(itemDTO.getId(), itemDTO.getOrderId(), itemDTO.getProductId(), itemDTO.getQuantity(), itemDTO.getCreatedAt()))
                .collect(Collectors.toList()));
        order.setCreatedAt(dto.getCreatedAt());
        return order;
    }

    public OrderDTO domainToDTO(Order order){
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setCustomerId(order.getCustomerId());
        dto.setCustomerEmail(order.getCustomerEmail());
        dto.setTotal(order.getTotal());
        dto.setStatus(order.getStatus());
        dto.setPaymentStatus(order.getPaymentStatus());
        dto.setPaymentType(order.getPaymentType());
        dto.setPaymentQrCode(order.getPaymentQrCode());
        dto.setWaitingTimeInMinutes(order.getWaitingTimeInMinutes());
        dto.setItems(order.getItems().stream()
                .map(item -> new OrderItemDTO(item.getId(), item.getOrderId(), item.getProductId(), item.getQuantity(), item.getCreatedAt()))
                .collect(Collectors.toList()));
        dto.setCreatedAt(order.getCreatedAt());
        return dto;
    }

    public JpaOrderEntity domainToJpa(Order order) {
        JpaOrderEntity jpaOrderEntity = new JpaOrderEntity();
        jpaOrderEntity.setId(order.getId());
        jpaOrderEntity.setCustomerId(order.getCustomerId());
        jpaOrderEntity.setCustomerEmail(order.getCustomerEmail());
        jpaOrderEntity.setTotal(order.getTotal());
        jpaOrderEntity.setStatus(order.getStatus());
        jpaOrderEntity.setPaymentType(order.getPaymentType());
        jpaOrderEntity.setPaymentStatus(order.getPaymentStatus());
        jpaOrderEntity.setPaymentQrCode(order.getPaymentQrCode());
        jpaOrderEntity.setWaitingTimeInMinutes(order.getWaitingTimeInMinutes());
        jpaOrderEntity.setCreatedAt(order.getCreatedAt());
        return jpaOrderEntity;
    }

    public Order jpaToDomain(JpaOrderEntity jpaOrderEntity, List<JpaOrderItemEntity> jpaOrderItemEntities) {
        Order order = new Order();
        order.setId(jpaOrderEntity.getId());
        order.setCustomerId(jpaOrderEntity.getCustomerId());
        order.setCustomerEmail(jpaOrderEntity.getCustomerEmail());
        order.setTotal(jpaOrderEntity.getTotal());
        order.setStatus(jpaOrderEntity.getStatus());
        order.setPaymentStatus(jpaOrderEntity.getPaymentStatus());
        order.setPaymentType(jpaOrderEntity.getPaymentType());
        order.setPaymentQrCode(jpaOrderEntity.getPaymentQrCode());
        order.setWaitingTimeInMinutes(jpaOrderEntity.getWaitingTimeInMinutes());
        order.setCreatedAt(jpaOrderEntity.getCreatedAt());

        List<JpaOrderItemEntity> filteredItems = jpaOrderItemEntities.stream()
                .filter(item -> item.getOrderId().equals(jpaOrderEntity.getId()))
                .toList();

        order.setItems(filteredItems.stream()
                .map(item -> new OrderItem(item.getId(), item.getOrderId(), item.getProductId(), item.getQuantity(), item.getCreatedAt()))
                .toList());

        return order;
    }

    public List<Order> jpaToDomainList(List<JpaOrderEntity> jpaOrderEntities, List<JpaOrderItemEntity> jpaOrderItemEntities) {
        return jpaOrderEntities.stream()
                .map(jpaOrderEntity -> jpaToDomain(jpaOrderEntity, jpaOrderItemEntities))
                .toList();
    }

    public List<OrderDTO> domainToDTOList(List<Order> orders) {
        return orders.stream()
                .map(this::domainToDTO)
                .collect(Collectors.toList());
    }
}
