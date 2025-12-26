package com.hcl.payment_service.service;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PaymentServiceImpl implements PaymentService {
    
    private final PaymentRepository paymentRepository;
    private final OrderClient orderClient;
    private final Random random = new Random();
    
    @Override
    public PaymentResponseDTO processPayment(ProcessPaymentDTO processPaymentDTO) {
        log.info("Processing payment for order: {}", processPaymentDTO.getOrderId());
        
        // Verify order exists and amount matches
        OrderDTO order = orderClient.getOrderById(processPaymentDTO.getOrderId());
        
        if (!order.getTotalAmount().equals(processPaymentDTO.getAmount())) {
            throw new PaymentException("Payment amount does not match order amount");
        }
        
        // Check if payment already exists for this order
        paymentRepository.findByOrderId(processPaymentDTO.getOrderId())
            .ifPresent(existingPayment -> {
                if (existingPayment.getStatus() == PaymentStatus.SUCCESS) {
                    throw new PaymentException("Payment already completed for this order");
                }
            });
        
        // Create payment record
        Payment payment = new Payment();
        payment.setOrderId(processPaymentDTO.getOrderId());
        payment.setUserId(processPaymentDTO.getUserId());
        payment.setAmount(processPaymentDTO.getAmount());
        payment.setPaymentMethod(processPaymentDTO.getPaymentMethod());
        payment.setStatus(PaymentStatus.PROCESSING);
        
        Payment savedPayment = paymentRepository.save(payment);
        
        try {
            // Simulate payment gateway processing
            simulatePaymentGateway(processPaymentDTO, savedPayment);
            
            if (savedPayment.getStatus() == PaymentStatus.SUCCESS) {
                // Update order payment status
                orderClient.updatePaymentStatus(order.getId(), "PAID");
                log.info("Payment successful for order: {}", order.getId());
            } else {
                // Update order payment status
                orderClient.updatePaymentStatus(order.getId(), "FAILED");
                log.error("Payment failed for order: {}", order.getId());
            }
            
        } catch (Exception e) {
            savedPayment.setStatus(PaymentStatus.FAILED);
            savedPayment.setFailureReason("Payment processing error: " + e.getMessage());
            log.error("Payment processing error for order: {}", order.getId(), e);
        }
        
        Payment finalPayment = paymentRepository.save(savedPayment);
        return mapToDTO(finalPayment);
    }
    
    /**
     * Mock Payment Gateway - Replace with real gateway integration
     * (Razorpay, Stripe, PayPal, etc.)
     */
    private void simulatePaymentGateway(ProcessPaymentDTO paymentDTO, Payment payment) {
        log.info("Simulating payment gateway for transaction: {}", payment.getTransactionId());
        
        // Simulate processing delay
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // COD always succeeds
        if (paymentDTO.getPaymentMethod() == PaymentMethod.COD) {
            payment.setStatus(PaymentStatus.SUCCESS);
            payment.setPaymentGatewayResponse("COD order placed successfully");
            return;
        }
        
        // Simulate 90% success rate for other payment methods
        boolean success = random.nextInt(100) < 90;
        
        if (success) {
            payment.setStatus(PaymentStatus.SUCCESS);
            payment.setPaymentGatewayResponse("Payment processed successfully");
        } else {
            payment.setStatus(PaymentStatus.FAILED);
            payment.setFailureReason("Insufficient funds");
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public PaymentResponseDTO getPaymentById(Long id) {
        log.info("Fetching payment by ID: {}", id);
        Payment payment = paymentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Payment not found with ID: " + id));
        return mapToDTO(payment);
    }
    
    @Override
    @Transactional(readOnly = true)
    public PaymentResponseDTO getPaymentByTransactionId(String transactionId) {
        log.info("Fetching payment by transaction ID: {}", transactionId);
        Payment payment = paymentRepository.findByTransactionId(transactionId)
            .orElseThrow(() -> new ResourceNotFoundException("Payment not found with transaction ID: " + transactionId));
        return mapToDTO(payment);
    }
    
    @Override
    @Transactional(readOnly = true)
    public PaymentResponseDTO getPaymentByOrderId(Long orderId) {
        log.info("Fetching payment by order ID: {}", orderId);
        Payment payment = paymentRepository.findByOrderId(orderId)
            .orElseThrow(() -> new ResourceNotFoundException("Payment not found for order ID: " + orderId));
        return mapToDTO(payment);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<PaymentResponseDTO> getPaymentsByUserId(Long userId) {
        log.info("Fetching payments for user: {}", userId);
        List<Payment> payments = paymentRepository.findByUserId(userId);
        return payments.stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<PaymentResponseDTO> getAllPayments() {
        log.info("Fetching all payments");
        List<Payment> payments = paymentRepository.findAll();
        return payments.stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList());
    }
    
    @Override
    public PaymentResponseDTO refundPayment(RefundRequestDTO refundRequestDTO) {
        log.info("Processing refund for payment: {}", refundRequestDTO.getPaymentId());
        
        Payment payment = paymentRepository.findById(refundRequestDTO.getPaymentId())
            .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));
        
        if (payment.getStatus() != PaymentStatus.SUCCESS) {
            throw new PaymentException("Only successful payments can be refunded");
        }
        
        if (payment.getStatus() == PaymentStatus.REFUNDED) {
            throw new PaymentException("Payment already refunded");
        }
        
        // Simulate refund processing
        payment.setStatus(PaymentStatus.REFUNDED);
        payment.setFailureReason(refundRequestDTO.getReason());
        
        // Update order status
        orderClient.updatePaymentStatus(payment.getOrderId(), "REFUNDED");
        
        Payment refundedPayment = paymentRepository.save(payment);
        log.info("Refund successful for payment: {}", payment.getId());
        
        return mapToDTO(refundedPayment);
    }
    
    @Override
    @Transactional(readOnly = true)
    public PaymentResponseDTO checkPaymentStatus(String transactionId) {
        log.info("Checking payment status for transaction: {}", transactionId);
        
        Payment payment = paymentRepository.findByTransactionId(transactionId)
            .orElseThrow(() -> new ResourceNotFoundException("Payment not found with transaction ID: " + transactionId));
        
        return mapToDTO(payment);
    }
    
    private PaymentResponseDTO mapToDTO(Payment payment) {
        PaymentResponseDTO dto = new PaymentResponseDTO();
        dto.setId(payment.getId());
        dto.setTransactionId(payment.getTransactionId());
        dto.setOrderId(payment.getOrderId());
        dto.setUserId(payment.getUserId());
        dto.setAmount(payment.getAmount());
        dto.setPaymentMethod(payment.getPaymentMethod());
        dto.setStatus(payment.getStatus());
        dto.setCreatedAt(payment.getCreatedAt());
        dto.setUpdatedAt(payment.getUpdatedAt());
        
        // Set appropriate message based on status
        String message = switch (payment.getStatus()) {
            case SUCCESS -> "Payment completed successfully";
            case FAILED -> "Payment failed: " + (payment.getFailureReason() != null ? payment.getFailureReason() : "Unknown error");
            case REFUNDED -> "Payment refunded";
            case PROCESSING -> "Payment is being processed";
            case PENDING -> "Payment is pending";
            case CANCELLED -> "Payment cancelled";
        };
        dto.setMessage(message);
        
        return dto;
    }
}
