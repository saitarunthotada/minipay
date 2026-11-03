package com.minipay.payment.domain;

import lombok.Getter;
import org.springframework.transaction.annotation.SpringTransactionAnnotationParser;

import java.time.Instant;
import java.util.UUID;

@Getter
public class Payment {
    private final UUID id;
    private final UUID orderId;
    private final long amountInPaise;
    private final String currency;

    private PaymentStatus status;

    private final Instant createdAt;
    private Instant updatedAt;

    public Payment(UUID orderId, long amountInPaise){
        if(orderId == null){
            throw new IllegalArgumentException(
                    "orderId is required"
            );
        }
        if(amountInPaise < 100){
           throw new IllegalArgumentException("amount must be at least 100 paise");
        }

        Instant now = Instant.now();

        this.id = UUID.randomUUID();
        this.orderId = orderId;
        this.amountInPaise = amountInPaise;
        this.currency = "INR";
        this.status = PaymentStatus.CREATED;
        this.createdAt = now;
        this.updatedAt = now;
    }

    private Payment(
            UUID id,
            UUID orderId,
            long amountInPaise,
            String currency,
            PaymentStatus status,
            Instant createdAt,
            Instant updatedAt
    ){
        this.id = id;
        this.orderId = orderId;
        this.amountInPaise = amountInPaise;
        this.currency = currency;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Payment rehydrate(
            UUID id,
            UUID orderId,
            long amountInPaise,
            String currency,
            PaymentStatus status,
            Instant createdAt,
            Instant updatedAt
    ){
        if(id == null || orderId == null || amountInPaise < 100 ||
        !currency.equals("INR") || status == null || createdAt == null
                || updatedAt.isBefore(createdAt)){
            throw new IllegalArgumentException(
                    "Invalid persisted payment state"
            );
        }
        return new Payment(id, orderId, amountInPaise, currency, status, createdAt, updatedAt);
    }

    public void markPending(){
        transitionTo(PaymentStatus.PENDING);
    }
    public void markSucceeded(){
        transitionTo(PaymentStatus.SUCCEEDED);
    }
    public void markFailed(){
        transitionTo(PaymentStatus.FAILED);
    }
    public void markCancelled(){
        transitionTo(PaymentStatus.CANCELLED);
    }

    private void transitionTo(PaymentStatus status){
        if(status!=PaymentStatus.CREATED
                && status!=PaymentStatus.PENDING){
            throw new IllegalStateException("cannot change payment from " + status);
        }
        this.status = status;
        this.updatedAt = Instant.now();
    }
}
