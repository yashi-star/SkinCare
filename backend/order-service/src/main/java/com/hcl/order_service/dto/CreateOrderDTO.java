package com.hcl.order_service.dto;

// ============================================
// DTOs - Data Transfer Objects
// ============================================

// FILE 1: CreateOrderDTO.java
// Path: dto/CreateOrderDTO.java

import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.List;

@Data
public class CreateOrderDTO {
    @NotNull(message = "User ID is required")
    private Long userId;
    
    @NotEmpty(message = "Order items cannot be empty")
    private List<OrderItemDTO> orderItems;
    
    @NotBlank(message = "Payment method is required")
    private String paymentMethod;
}