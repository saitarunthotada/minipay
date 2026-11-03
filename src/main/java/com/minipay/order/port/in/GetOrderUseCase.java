package com.minipay.order.port.in;

import com.minipay.order.domain.Order;

import java.util.UUID;

public interface GetOrderUseCase {
    Order getOrder(UUID orderId);
}
