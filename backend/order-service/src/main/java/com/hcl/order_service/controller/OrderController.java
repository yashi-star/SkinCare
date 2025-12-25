package com.hcl.order_service.controller;


import com.hcl.order_service.dto.*;
import com.hcl.order_service.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
public class OrderController {
    
    private final OrderService orderService;
    
    /**
     * Create a new order
     * POST /orders
     */
    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@Valid @RequestBody CreateOrderDTO createOrderDTO) {
        log.info("REST request to create order for user: {}", createOrderDTO.getUserId());
        OrderResponseDTO order = orderService.createOrder(createOrderDTO);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }
    
    /**
     * Get order by ID
     * GET /orders/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable Long id) {
        log.info("REST request to get order: {}", id);
        OrderResponseDTO order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }
    
    /**
     * Get all orders
     * GET /orders
     */
    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        log.info("REST request to get all orders");
        List<OrderResponseDTO> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }
    
    /**
     * Get orders by user ID
     * GET /orders/user/{userId}
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByUserId(@PathVariable Long userId) {
        log.info("REST request to get orders for user: {}", userId);
        List<OrderResponseDTO> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }
    
    /**
     * Update order status
     * PATCH /orders/{id}/status?status=CONFIRMED
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderResponseDTO> updateOrderStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        log.info("REST request to update order {} status to: {}", id, status);
        OrderResponseDTO order = orderService.updateOrderStatus(id, status);
        return ResponseEntity.ok(order);
    }
    
    /**
     * Update payment status
     * PATCH /orders/{id}/payment?paymentStatus=PAID
     */
    @PatchMapping("/{id}/payment")
    public ResponseEntity<OrderResponseDTO> updatePaymentStatus(
            @PathVariable Long id,
            @RequestParam String paymentStatus) {
        log.info("REST request to update order {} payment to: {}", id, paymentStatus);
        OrderResponseDTO order = orderService.updatePaymentStatus(id, paymentStatus);
        return ResponseEntity.ok(order);
    }
    
    /**
     * Cancel order
     * DELETE /orders/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> cancelOrder(@PathVariable Long id) {
        log.info("REST request to cancel order: {}", id);
        orderService.cancelOrder(id);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Order cancelled successfully");
        response.put("orderId", id.toString());
        
        return ResponseEntity.ok(response);
    }
}
