package com.example.fiapsoattechchallengeorderapi.adapters.outbound.repositories;

import com.example.fiapsoattechchallengeorderapi.adapters.outbound.entities.JpaOrderEntity;
import com.example.fiapsoattechchallengeorderapi.domain.order.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaOrderRepository extends JpaRepository<JpaOrderEntity, Long> {

    @Query("SELECT o FROM JpaOrderEntity o WHERE o.status = :status")
    List<JpaOrderEntity> findByStatus(@Param("status") OrderStatus status);
}
