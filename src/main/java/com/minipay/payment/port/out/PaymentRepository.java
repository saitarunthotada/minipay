package com.minipay.payment.port.out;

import com.minipay.payment.domain.Payment;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository {
    Payment save(Payment payment);
    Optional<Payment> findById(UUID paymentId);
    Optional<Payment> findLatestByOrderId(UUID orderId);
}
