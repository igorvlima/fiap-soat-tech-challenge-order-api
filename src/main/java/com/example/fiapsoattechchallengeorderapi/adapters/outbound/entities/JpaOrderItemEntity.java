package com.example.fiapsoattechchallengeorderapi.adapters.outbound.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(name = "order_item")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "order_item_seq_gen", sequenceName = "order_item_id_seq", allocationSize = 1)
public class JpaOrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_item_seq_gen")
    private Long id;
    private Long orderId;
    private Long productId;
    private Integer quantity;
    private LocalDateTime createdAt;
}
