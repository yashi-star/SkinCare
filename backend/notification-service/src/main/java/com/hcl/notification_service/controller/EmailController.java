package com.hcl.notification_service.controller;

import com.hcl.notification_service.dto.EmailRequest;
import com.hcl.notification_service.dto.EmailResponse;
import com.hcl.notification_service.service.EmailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
public class EmailController {
    
    private final EmailService emailService;
    
    /**
     * Send generic email
     * POST /notifications/send
     */
    @PostMapping("/send")
    public ResponseEntity<EmailResponse> sendEmail(@Valid @RequestBody EmailRequest request) {
        log.info("Sending email to: {}", request.getTo());
        EmailResponse response = emailService.sendEmail(request);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Send order confirmation
     * POST /notifications/order-confirmation/{orderId}
     */
    @PostMapping("/order-confirmation/{orderId}")
    public ResponseEntity<EmailResponse> sendOrderConfirmation(@PathVariable Long orderId) {
        log.info("Sending order confirmation for order: {}", orderId);
        EmailResponse response = emailService.sendOrderConfirmation(orderId);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Send payment confirmation
     * POST /notifications/payment-confirmation/{orderId}
     */
    @PostMapping("/payment-confirmation/{orderId}")
    public ResponseEntity<EmailResponse> sendPaymentConfirmation(
            @PathVariable Long orderId,
            @RequestParam String transactionId) {
        log.info("Sending payment confirmation for order: {}", orderId);
        EmailResponse response = emailService.sendPaymentConfirmation(orderId, transactionId);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Send order shipped notification
     * POST /notifications/order-shipped/{orderId}
     */
    @PostMapping("/order-shipped/{orderId}")
    public ResponseEntity<EmailResponse> sendOrderShipped(
            @PathVariable Long orderId,
            @RequestParam String trackingNumber) {
        log.info("Sending order shipped notification for order: {}", orderId);
        EmailResponse response = emailService.sendOrderShipped(orderId, trackingNumber);
        return ResponseEntity.ok(response);
    }
}

