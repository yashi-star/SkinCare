package com.hcl.order_service.dto;


import lombok.Data;
import java.math.BigDecimal;

@Data
public class OrderItemResponseDTO {
    private Long id;
    private Long productId;
    private String productName;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal subtotal;
}