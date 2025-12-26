package com.hcl.notification_service.service;

import com.hcl.notification_service.client.OrderClient;
import com.hcl.notification_service.client.UserClient;
import com.hcl.notification_service.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {
    
    private final JavaMailSender mailSender;
    private final OrderClient orderClient;
    private final UserClient userClient;
    
    /**
     * Send generic email
     */
    public EmailResponse sendEmail(EmailRequest request) {
        try {
            log.info("Sending email to: {}", request.getTo());
            
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(request.getTo());
            message.setSubject(request.getSubject());
            message.setText(request.getMessage());
            message.setFrom("noreply@skincare.com");
            
            mailSender.send(message);
            
            log.info("Email sent successfully to: {}", request.getTo());
            return new EmailResponse(true, "Email sent successfully", LocalDateTime.now());
            
        } catch (Exception e) {
            log.error("Failed to send email to: {}", request.getTo(), e);
            return new EmailResponse(false, "Failed to send email: " + e.getMessage(), LocalDateTime.now());
        }
    }
    
    /**
     * Send order confirmation email
     */
    public EmailResponse sendOrderConfirmation(Long orderId) {
        try {
            log.info("Sending order confirmation for order: {}", orderId);
            
            // Get order details
            OrderDTO order = orderClient.getOrderById(orderId);
            
            // Get user details
            UserDTO user = userClient.getUserById(order.getUserId());
            
            // Build email
            String subject = "Order Confirmation - Order #" + orderId;
            String message = String.format(
                "Dear %s %s,\n\n" +
                "Thank you for your order!\n\n" +
                "Order ID: %d\n" +
                "Total Amount: $%.2f\n" +
                "Status: %s\n" +
                "Payment Status: %s\n\n" +
                "We will notify you once your order is shipped.\n\n" +
                "Thank you for shopping with us!\n\n" +
                "Best regards,\n" +
                "Skincare Team",
                user.getFirstName(), user.getLastName(),
                order.getId(), order.getTotalAmount(),
                order.getOrderStatus(), order.getPaymentStatus()
            );
            
            EmailRequest emailRequest = new EmailRequest(user.getEmail(), subject, message);
            return sendEmail(emailRequest);
            
        } catch (Exception e) {
            log.error("Failed to send order confirmation for order: {}", orderId, e);
            return new EmailResponse(false, "Failed to send order confirmation: " + e.getMessage(), LocalDateTime.now());
        }
    }
    
    /**
     * Send payment confirmation email
     */
    public EmailResponse sendPaymentConfirmation(Long orderId, String transactionId) {
        try {
            log.info("Sending payment confirmation for order: {}", orderId);
            
            // Get order details
            OrderDTO order = orderClient.getOrderById(orderId);
            
            // Get user details
            UserDTO user = userClient.getUserById(order.getUserId());
            
            // Build email
            String subject = "Payment Confirmation - Order #" + orderId;
            String message = String.format(
                "Dear %s %s,\n\n" +
                "Your payment has been received successfully!\n\n" +
                "Order ID: %d\n" +
                "Transaction ID: %s\n" +
                "Amount Paid: $%.2f\n" +
                "Payment Status: %s\n\n" +
                "Your order is now being processed.\n\n" +
                "Thank you!\n\n" +
                "Best regards,\n" +
                "Skincare Team",
                user.getFirstName(), user.getLastName(),
                order.getId(), transactionId, order.getTotalAmount(),
                order.getPaymentStatus()
            );
            
            EmailRequest emailRequest = new EmailRequest(user.getEmail(), subject, message);
            return sendEmail(emailRequest);
            
        } catch (Exception e) {
            log.error("Failed to send payment confirmation for order: {}", orderId, e);
            return new EmailResponse(false, "Failed to send payment confirmation: " + e.getMessage(), LocalDateTime.now());
        }
    }
    
    /**
     * Send order shipped notification
     */
    public EmailResponse sendOrderShipped(Long orderId, String trackingNumber) {
        try {
            log.info("Sending order shipped notification for order: {}", orderId);
            
            // Get order details
            OrderDTO order = orderClient.getOrderById(orderId);
            
            // Get user details
            UserDTO user = userClient.getUserById(order.getUserId());
            
            // Build email
            String subject = "Order Shipped - Order #" + orderId;
            String message = String.format(
                "Dear %s %s,\n\n" +
                "Great news! Your order has been shipped!\n\n" +
                "Order ID: %d\n" +
                "Tracking Number: %s\n" +
                "Total Amount: $%.2f\n\n" +
                "You can track your order using the tracking number above.\n\n" +
                "Estimated delivery: 3-5 business days\n\n" +
                "Thank you for shopping with us!\n\n" +
                "Best regards,\n" +
                "Skincare Team",
                user.getFirstName(), user.getLastName(),
                order.getId(), trackingNumber, order.getTotalAmount()
            );
            
            EmailRequest emailRequest = new EmailRequest(user.getEmail(), subject, message);
            return sendEmail(emailRequest);
            
        } catch (Exception e) {
            log.error("Failed to send order shipped notification for order: {}", orderId, e);
            return new EmailResponse(false, "Failed to send shipping notification: " + e.getMessage(), LocalDateTime.now());
        }
    }
}
