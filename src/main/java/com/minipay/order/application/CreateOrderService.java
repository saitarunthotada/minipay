package com.minipay.order.application;

import com.minipay.order.domain.Order;
import com.minipay.order.port.in.CreateOrderUseCase;
import com.minipay.order.port.out.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Transactional
public class CreateOrderService implements CreateOrderUseCase {

    private final OrderRepository orderRepository;

    @Override
    public Order createOrder(String description, long amountInPaise) {
        Order order = new Order(description, amountInPaise);
        return orderRepository.save(order);
    }
}
