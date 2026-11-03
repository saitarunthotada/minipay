package com.minipay.order.adapter.in.web;

import com.minipay.order.domain.Order;
import com.minipay.order.domain.OrderStatus;

import java.time.Instant;
import java.util.UUID;

public record OrderResponse (
        UUID id,
        String description,
        long amountInPaise,
        String currency,
        OrderStatus status,
        Instant createdAt,
        Instant updatedAt
)
{
    public static OrderResponse from(Order order){
        return new OrderResponse(
                order.getId(),
                order.getDescription(),
                order.getAmountInPaise(),
                order.getCurrency(),
                order.getStatus(),
                order.getCreatedAt(),
                order.getUpdatedAt()
        );
    }
}
