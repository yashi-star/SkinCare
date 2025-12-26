package com.hcl.payment_service.dto;

import com.hcl.payment_service.entity.PaymentMethod;
import com.hcl.payment_service.entity.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDTO {
    private Long id;
    private String transactionId;
    private Long orderId;
    private Long userId;
    private BigDecimal amount;
    private PaymentMethod paymentMethod;
    private PaymentStatus status;
    private String message;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
