package com.minipay.order.application;

import com.minipay.order.domain.Order;
import com.minipay.order.port.in.GetOrderUseCase;
import com.minipay.order.port.out.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetOrderService implements GetOrderUseCase {

    private final OrderRepository orderRepository;

    @Override
    public Order getOrder(UUID orderId) {
         return orderRepository.findById(orderId)
                 .orElseThrow(() ->
                         new IllegalArgumentException(
                                 "Order not found: " + orderId
                         ));
    }
}
