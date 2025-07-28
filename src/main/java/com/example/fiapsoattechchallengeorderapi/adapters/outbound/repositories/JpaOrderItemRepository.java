package com.example.fiapsoattechchallengeorderapi.adapters.outbound.repositories;

import com.example.fiapsoattechchallengeorderapi.adapters.outbound.entities.JpaOrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaOrderItemRepository extends JpaRepository<JpaOrderItemEntity, Long> {
    List<JpaOrderItemEntity> findAllByOrderId(Long orderId);
}
