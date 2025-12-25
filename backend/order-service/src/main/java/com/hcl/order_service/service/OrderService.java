package com.hcl.order_service.service;

// ============================================
// FILE: OrderService.java
// Path: src/main/java/com/hcl/orderservice/service/OrderService.java
// ============================================

import com.hcl.order_service.feignClient.*;
import com.hcl.order_service.dto.*;
import com.hcl.order_service.entity.Order;
import com.hcl.order_service.entity.OrderItem;
import com.hcl.order_service.exception.*;
import com.hcl.order_service.repository.OrderRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final ProductClient productClient;
    private final UserClient userClient;
    
    @Transactional
    public OrderResponseDTO createOrder(CreateOrderDTO createOrderDTO) {
        log.info("Creating order for user ID: {}", createOrderDTO.getUserId());
        
        // 1. Verify user exists
        UserDTO user;
        try {
            user = userClient.getUserById(createOrderDTO.getUserId());
            log.info("User verified: {} {}", user.getFirstName(), user.getLastName());
        } catch (FeignException e) {
            log.error("Failed to fetch user: {}", e.getMessage());
            throw new ServiceUnavailableException("User service unavailable or user not found");
        }
        
        // 2. Create order
        Order order = new Order();
        order.setUserId(createOrderDTO.getUserId());
        order.setPaymentMethod(createOrderDTO.getPaymentMethod());
        order.setPaymentStatus("PENDING");
        order.setStatus("PENDING");
        
        // Build shipping address
        String shippingAddress = String.format("%s, %s, %s %s, %s",
            user.getAddress() != null ? user.getAddress() : "",
            user.getCity() != null ? user.getCity() : "",
            user.getState() != null ? user.getState() : "",
            user.getZipCode() != null ? user.getZipCode() : "",
            user.getCountry() != null ? user.getCountry() : ""
        );
        order.setShippingAddress(shippingAddress);
        
        // 3. Process order items
        BigDecimal totalAmount = BigDecimal.ZERO;
        
        for (OrderItemDTO itemDTO : createOrderDTO.getOrderItems()) {
            // Get product details
            ProductDTO product;
            try {
                product = productClient.getProductById(itemDTO.getProductId());
                log.info("Product fetched: {} - Price: {}", product.getName(), product.getPrice());
            } catch (FeignException e) {
                log.error("Failed to fetch product {}: {}", itemDTO.getProductId(), e.getMessage());
                throw new ServiceUnavailableException("Product service unavailable or product not found");
            }
            
            // Validate product
            if (!product.getActive()) {
                throw new BadRequestException("Product " + product.getName() + " is not available");
            }
            
            if (product.getStock() < itemDTO.getQuantity()) {
                throw new BadRequestException(
                    "Insufficient stock for " + product.getName() +
                    ". Available: " + product.getStock() + ", Requested: " + itemDTO.getQuantity()
                );
            }
            
            // Create order item
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProductId(product.getId());
            orderItem.setProductName(product.getName());
            orderItem.setQuantity(itemDTO.getQuantity());
            orderItem.setPrice(product.getPrice());
            
            BigDecimal subtotal = product.getPrice().multiply(new BigDecimal(itemDTO.getQuantity()));
            orderItem.setSubtotal(subtotal);
            
            order.getOrderItems().add(orderItem);
            totalAmount = totalAmount.add(subtotal);
        }
        
        order.setTotalAmount(totalAmount);
        
        // 4. Save order
        Order savedOrder = orderRepository.save(order);
        log.info("Order created with ID: {}", savedOrder.getId());
        
        // 5. Update stock
        for (OrderItem item : savedOrder.getOrderItems()) {
            try {
                productClient.updateStock(item.getProductId(), item.getQuantity());
                log.info("Stock updated for product ID: {}", item.getProductId());
            } catch (FeignException e) {
                log.error("Failed to update stock: {}", e.getMessage());
            }
        }
        
        return mapToResponseDTO(savedOrder, user);
    }
    
    public OrderResponseDTO getOrderById(Long id) {
        log.info("Fetching order: {}", id);
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + id));
        
        UserDTO user;
        try {
            user = userClient.getUserById(order.getUserId());
        } catch (FeignException e) {
            user = null;
        }
        
        return mapToResponseDTO(order, user);
    }
    
    public List<OrderResponseDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(order -> {
                    try {
                        UserDTO user = userClient.getUserById(order.getUserId());
                        return mapToResponseDTO(order, user);
                    } catch (FeignException e) {
                        return mapToResponseDTO(order, null);
                    }
                })
                .collect(Collectors.toList());
    }
    
    public List<OrderResponseDTO> getOrdersByUserId(Long userId) {
        try {
            userClient.getUserById(userId);
        } catch (FeignException e) {
            throw new ResourceNotFoundException("User not found: " + userId);
        }
        
        UserDTO user;
        try {
            user = userClient.getUserById(userId);
        } catch (FeignException e) {
            user = null;
        }
        
        UserDTO finalUser = user;
        return orderRepository.findByUserId(userId).stream()
                .map(order -> mapToResponseDTO(order, finalUser))
                .collect(Collectors.toList());
    }
    
    @Transactional
    public OrderResponseDTO updateOrderStatus(Long id, String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + id));
        order.setStatus(status);
        Order updated = orderRepository.save(order);
        
        UserDTO user;
        try {
            user = userClient.getUserById(order.getUserId());
        } catch (FeignException e) {
            user = null;
        }
        
        return mapToResponseDTO(updated, user);
    }
    
    @Transactional
    public OrderResponseDTO updatePaymentStatus(Long id, String paymentStatus) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + id));
        
        order.setPaymentStatus(paymentStatus);
        if ("PAID".equals(paymentStatus) && "PENDING".equals(order.getStatus())) {
            order.setStatus("CONFIRMED");
        }
        
        Order updated = orderRepository.save(order);
        
        UserDTO user;
        try {
            user = userClient.getUserById(order.getUserId());
        } catch (FeignException e) {
            user = null;
        }
        
        return mapToResponseDTO(updated, user);
    }
    
    @Transactional
    public void cancelOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + id));
        
        if (!"PENDING".equals(order.getStatus()) && !"CONFIRMED".equals(order.getStatus())) {
            throw new BadRequestException("Cannot cancel order in status: " + order.getStatus());
        }
        
        order.setStatus("CANCELLED");
        orderRepository.save(order);
    }
    
    // Mapping methods
    private OrderResponseDTO mapToResponseDTO(Order order, UserDTO user) {
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setId(order.getId());
        dto.setUserId(order.getUserId());
        
        if (user != null) {
            dto.setUserEmail(user.getEmail());
            dto.setUserName(user.getFirstName() + " " + user.getLastName());
        }
        
        dto.setTotalAmount(order.getTotalAmount());
        dto.setStatus(order.getStatus());
        dto.setShippingAddress(order.getShippingAddress());
        dto.setPaymentMethod(order.getPaymentMethod());
        dto.setPaymentStatus(order.getPaymentStatus());
        dto.setCreatedAt(order.getCreatedAt());
        
        List<OrderItemResponseDTO> items = order.getOrderItems().stream()
                .map(this::mapItemToDTO)
                .collect(Collectors.toList());
        dto.setOrderItems(items);
        
        return dto;
    }
    
    private OrderItemResponseDTO mapItemToDTO(OrderItem item) {
        OrderItemResponseDTO dto = new OrderItemResponseDTO();
        dto.setId(item.getId());
        dto.setProductId(item.getProductId());
        dto.setProductName(item.getProductName());
        dto.setQuantity(item.getQuantity());
        dto.setPrice(item.getPrice());
        dto.setSubtotal(item.getSubtotal());
        return dto;
    }
}