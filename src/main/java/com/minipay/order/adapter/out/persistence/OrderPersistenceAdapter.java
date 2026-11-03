package com.minipay.order.adapter.out.persistence;

import com.minipay.order.domain.Order;
import com.minipay.order.port.out.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OrderPersistenceAdapter implements OrderRepository {

    private final SpringDataOrderRepository springDataOrderRepository;

    @Override
    public Order save(Order order) {
        OrderJpaEntity entity = toEntity(order);
        OrderJpaEntity savedEntity = springDataOrderRepository.save(entity);
        return toDomain(savedEntity);
    }

    @Override
    public Optional<Order> findById(UUID id) {
        return springDataOrderRepository.findById(id)
                .map(this::toDomain);
    }

    private OrderJpaEntity toEntity(Order order){
        OrderJpaEntity entity = new OrderJpaEntity();
        entity.setId(order.getId());
        entity.setDescription(order.getDescription());
        entity.setAmountInPaise(order.getAmountInPaise());
        entity.setCurrency(order.getCurrency());
        entity.setStatus(order.getStatus());
        entity.setCreatedAt(order.getCreatedAt());
        entity.setUpdatedAt(order.getUpdatedAt());
        return entity;
    }

    private Order toDomain(OrderJpaEntity orderJpaEntity){
        return Order.rehydrate(
                orderJpaEntity.getId(),
                orderJpaEntity.getDescription(),
                orderJpaEntity.getAmountInPaise(),
                orderJpaEntity.getCurrency(),
                orderJpaEntity.getStatus(),
                orderJpaEntity.getCreatedAt(),
                orderJpaEntity.getUpdatedAt()
        );
    }
}
