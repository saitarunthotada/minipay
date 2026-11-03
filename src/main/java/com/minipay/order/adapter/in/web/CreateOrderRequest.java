package com.minipay.order.adapter.in.web;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateOrderRequest (

        @NotBlank(message = "description is required")
        @Size(max = 255, message = "Description cannot exceed 25 characters")
        String description,

        @Min(value = 100, message = "Minimum order value is 100 paise")
        long amountInPaise
){
}