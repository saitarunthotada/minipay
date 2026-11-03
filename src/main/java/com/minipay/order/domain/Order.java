package com.minipay.order.domain;

import lombok.Getter;
import org.aspectj.weaver.ast.Or;

import java.time.Instant;
import java.util.UUID;

@Getter
public class Order {

    private final UUID id;
    private final String description;
    private final long amountInPaise;
    private final String currency;
    private OrderStatus status;
    private final Instant createdAt;
    private Instant updatedAt;

    public Order(String description, long amountInPaise) {

        if(description == null || description.isBlank() || description.length() > 255){
            throw new IllegalArgumentException(
                    "Description must contain 1-255 characters"
            );
        }

        if(amountInPaise < 100){
            throw new IllegalArgumentException(
                    "Amount must be at least 100 paise"
            );
        }

        Instant now = Instant.now();

        this.id = UUID.randomUUID();
        this.description = description;
        this.amountInPaise = amountInPaise;
        this.currency = "INR";
        this.status = OrderStatus.CREATED;
        this.createdAt = now;
        this.updatedAt = now;
    }

    private Order(
            UUID id,
            String description,
            long amountInPaise,
            String currency,
            OrderStatus status,
            Instant createdAt,
            Instant updatedAt
    ){
        this.id = id;
        this.description = description;
        this.amountInPaise = amountInPaise;
        this.currency = currency;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Order rehydrate(
            UUID id,
            String description,
            long amountInPaise,
            String currency,
            OrderStatus status,
            Instant createdAt,
            Instant updatedAt
    ){
        if (id == null){
            throw new IllegalArgumentException("order Id cannot be null");
        }
        if(description == null || description.isBlank() || description.length() > 255 ){
            throw new IllegalArgumentException("invalid order description");
        }
        if (amountInPaise < 100){
            throw new IllegalArgumentException("Invalid order amount");
        }
        if(!currency.equals("INR")){
            throw new IllegalArgumentException("Unsupported currency");
        }if(status == null || createdAt == null || updatedAt == null){
            throw new IllegalArgumentException("status and timestamps cannot be null");
        }
        if(updatedAt.isBefore(createdAt)){
            throw new IllegalArgumentException(
                    "Updated timestamp cannot precede creation timestamp"
            );
        }
        return new Order(
                id, description, amountInPaise, currency, status, createdAt, updatedAt
        );
    }

    public void markPaymentPending(){
        if (status!=OrderStatus.CREATED){
            throw new IllegalStateException(
                    "Only newly created orders can become payment pending"
            );
        }
        this.status = OrderStatus.PAYMENT_PENDING;
        this.updatedAt = Instant.now();
    }
}


