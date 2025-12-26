package com.hcl.payment_service.controller;

import com.hcl.payment_service.dto.PaymentResponseDTO;
import com.hcl.payment_service.dto.ProcessPaymentDTO;
import com.hcl.payment_service.dto.RefundRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
public class PaymentController {
    
    private final PaymentService paymentService;
    
    /**
     * Process a new payment
     * POST /payments/process
     */
    @PostMapping("/process")
    public ResponseEntity<PaymentResponseDTO> processPayment(
            @Valid @RequestBody ProcessPaymentDTO processPaymentDTO) {
        log.info("REST request to process payment for order: {}", processPaymentDTO.getOrderId());
        PaymentResponseDTO payment = paymentService.processPayment(processPaymentDTO);
        return new ResponseEntity<>(payment, HttpStatus.CREATED);
    }
    
    /**
     * Get payment by ID
     * GET /payments/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponseDTO> getPaymentById(@PathVariable Long id) {
        log.info("REST request to get payment: {}", id);
        PaymentResponseDTO payment = paymentService.getPaymentById(id);
        return ResponseEntity.ok(payment);
    }
    
    /**
     * Get payment by transaction ID
     * GET /payments/transaction/{transactionId}
     */
    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity<PaymentResponseDTO> getPaymentByTransactionId(
            @PathVariable String transactionId) {
        log.info("REST request to get payment by transaction ID: {}", transactionId);
        PaymentResponseDTO payment = paymentService.getPaymentByTransactionId(transactionId);
        return ResponseEntity.ok(payment);
    }
    
    /**
     * Get payment by order ID
     * GET /payments/order/{orderId}
     */
    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentResponseDTO> getPaymentByOrderId(@PathVariable Long orderId) {
        log.info("REST request to get payment for order: {}", orderId);
        PaymentResponseDTO payment = paymentService.getPaymentByOrderId(orderId);
        return ResponseEntity.ok(payment);
    }
    
    /**
     * Get all payments for a user
     * GET /payments/user/{userId}
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PaymentResponseDTO>> getPaymentsByUserId(@PathVariable Long userId) {
        log.info("REST request to get payments for user: {}", userId);
        List<PaymentResponseDTO> payments = paymentService.getPaymentsByUserId(userId);
        return ResponseEntity.ok(payments);
    }
    
    /**
     * Get all payments
     * GET /payments
     */
    @GetMapping
    public ResponseEntity<List<PaymentResponseDTO>> getAllPayments() {
        log.info("REST request to get all payments");
        List<PaymentResponseDTO> payments = paymentService.getAllPayments();
        return ResponseEntity.ok(payments);
    }
    
    /**
     * Process refund
     * POST /payments/refund
     */
    @PostMapping("/refund")
    public ResponseEntity<PaymentResponseDTO> refundPayment(
            @Valid @RequestBody RefundRequestDTO refundRequestDTO) {
        log.info("REST request to refund payment: {}", refundRequestDTO.getPaymentId());
        PaymentResponseDTO payment = paymentService.refundPayment(refundRequestDTO);
        return ResponseEntity.ok(payment);
    }
    
    /**
     * Check payment status
     * GET /payments/status/{transactionId}
     */
    @GetMapping("/status/{transactionId}")
    public ResponseEntity<PaymentResponseDTO> checkPaymentStatus(
            @PathVariable String transactionId) {
        log.info("REST request to check payment status: {}", transactionId);
        PaymentResponseDTO payment = paymentService.checkPaymentStatus(transactionId);
        return ResponseEntity.ok(payment);
    }
}
