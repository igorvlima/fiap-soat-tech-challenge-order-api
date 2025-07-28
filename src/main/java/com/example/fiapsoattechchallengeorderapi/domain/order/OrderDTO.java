package com.example.fiapsoattechchallengeorderapi.domain.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO {
    private Long id;
    private Long customerId;
    private String customerEmail;
    private BigDecimal total;
    private OrderStatus status;
    private OrderPaymentStatus paymentStatus;
    private OrderPaymentType paymentType;
    private String paymentQrCode;
    private Long waitingTimeInMinutes;
    private List<OrderItemDTO> items;
    private LocalDateTime createdAt;

    public OrderDTO() {
    }
    public OrderDTO(Long id, Long customerId, String customerEmail, BigDecimal total, OrderStatus status, OrderPaymentStatus orderPaymentStatus,
                    String paymentQrCode, OrderPaymentType orderPaymentType, Long waitingTimeInMinutes, List<OrderItemDTO> items, LocalDateTime createdAt) {
        this.id = id;
        this.customerId = customerId;
        this.customerEmail = customerEmail;
        this.total = total;
        this.status = status;
        this.paymentStatus = orderPaymentStatus;
        this.paymentQrCode = paymentQrCode;
        this.paymentType = orderPaymentType;
        this.waitingTimeInMinutes = waitingTimeInMinutes;
        this.items = items;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public OrderPaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(OrderPaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public OrderPaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(OrderPaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentQrCode() {
        return paymentQrCode;
    }

    public void setPaymentQrCode(String paymentQrCode) {
        this.paymentQrCode = paymentQrCode;
    }

    public Long getWaitingTimeInMinutes() {
        return waitingTimeInMinutes;
    }

    public void setWaitingTimeInMinutes(Long waitingTime) {
        this.waitingTimeInMinutes = waitingTime;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
