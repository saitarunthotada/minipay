package com.minipay.order.port.out;

import com.minipay.order.domain.Order;

import java.util.Optional;
import java.util.UUID;

/*
Interface	Responsibility
OrderRepository	Domain-facing persistence contract
 */

public interface OrderRepository {
    Order save(Order order);
    Optional<Order> findById(UUID id);
}