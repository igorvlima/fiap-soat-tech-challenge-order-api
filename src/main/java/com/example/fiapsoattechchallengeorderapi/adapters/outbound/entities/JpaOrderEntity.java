package com.example.fiapsoattechchallengeorderapi.adapters.outbound.entities;

import com.example.fiapsoattechchallengeorderapi.domain.order.OrderPaymentStatus;
import com.example.fiapsoattechchallengeorderapi.domain.order.OrderPaymentType;
import com.example.fiapsoattechchallengeorderapi.domain.order.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name = "\"order\"")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "order_seq_gen", sequenceName = "order_id_seq", allocationSize = 1)
public class JpaOrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq_gen")
    private Long id;
    private Long customerId;
    private String customerEmail;
    private BigDecimal total;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @Enumerated(EnumType.STRING)
    private OrderPaymentStatus paymentStatus;
    @Enumerated(EnumType.STRING)
    private OrderPaymentType paymentType;
    @Column(name = "payment_qr_code")
    private String paymentQrCode;
    private Long waitingTimeInMinutes;
    private LocalDateTime createdAt;
}