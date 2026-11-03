package com.minipay.order.adapter.in.web;


import com.minipay.order.domain.Order;
import com.minipay.order.port.in.CreateOrderUseCase;
import com.minipay.order.port.in.GetOrderUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final CreateOrderUseCase createOrderUseCase;
    private final GetOrderUseCase getOrderUseCase;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            @Valid @RequestBody CreateOrderRequest request){
        Order order = createOrderUseCase.createOrder(
                request.description(),
                request.amountInPaise()
        );
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(OrderResponse.from(order));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrder(
            @PathVariable UUID id){
        Order order = getOrderUseCase.getOrder(id);
        return ResponseEntity.ok(OrderResponse.from(order));
    }
}
