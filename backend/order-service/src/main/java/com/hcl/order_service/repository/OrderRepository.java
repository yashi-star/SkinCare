package com.hcl.order_service.repository;

// ============================================
// FILE: OrderRepository.java
// Path: src/main/java/com/hcl/orderservice/repository/OrderRepository.java
// ============================================


import com.hcl.order_service.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    // Find all orders for a specific user
    List<Order> findByUserId(Long userId);
    
    // Find orders by status
    List<Order> findByStatus(String status);
    
    // Find orders by payment status
    List<Order> findByPaymentStatus(String paymentStatus);
    
    // Find orders created after a specific date
    List<Order> findByCreatedAtAfter(LocalDateTime date);
    
    // Find orders between date range
    List<Order> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    // Find pending orders for a user
    List<Order> findByUserIdAndStatus(Long userId, String status);
    
    // Count orders by status
    @Query("SELECT COUNT(o) FROM Order o WHERE o.status = :status")
    Long countByStatus(String status);
    
    // Get total revenue
    @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.paymentStatus = 'PAID'")
    Double getTotalRevenue();
}