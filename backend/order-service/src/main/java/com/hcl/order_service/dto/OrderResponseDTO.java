package com.hcl.order_service.dto;


import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponseDTO {
    private Long id;
    private Long userId;
    private String userEmail;
    private String userName;
    private List<OrderItemResponseDTO> orderItems;
    private BigDecimal totalAmount;
    private String status;
    private String shippingAddress;
    private String paymentMethod;
    private String paymentStatus;
    private LocalDateTime createdAt;
}