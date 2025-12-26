package com.hcl.payment_service.dto;

import com.hcl.payment_service.entity.PaymentMethod;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessPaymentDTO {
    
    @NotNull(message = "Order ID is required")
    private Long orderId;
    
    @NotNull(message = "User ID is required")
    private Long userId;
    
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;
    
    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;
    
    // Payment details (masked in production)
    private String cardNumber;
    private String cardHolderName;
    private String expiryDate;
    private String cvv;
    
    // UPI details
    private String upiId;
    
    // Net Banking
    private String bankCode;
}
