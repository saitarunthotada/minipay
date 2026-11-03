package com.minipay.order.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/*
Interface                  	Responsibility
SpringDataOrderRepository	Spring Data JPA database operations
 */
public interface SpringDataOrderRepository extends JpaRepository<OrderJpaEntity, UUID> {
}
