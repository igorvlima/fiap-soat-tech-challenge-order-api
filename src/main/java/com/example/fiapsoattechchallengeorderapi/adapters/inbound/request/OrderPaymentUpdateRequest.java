package com.example.fiapsoattechchallengeorderapi.adapters.inbound.request;

import lombok.Data;

@Data
public class OrderPaymentUpdateRequest {
    Long orderId;
    String paymentQrCode;
    String paymentStatus;
}
