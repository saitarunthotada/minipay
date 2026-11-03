package com.minipay.order.port.in;

import com.minipay.order.domain.Order;

public interface CreateOrderUseCase {
    Order createOrder(String description, long amountInPaise);
}
