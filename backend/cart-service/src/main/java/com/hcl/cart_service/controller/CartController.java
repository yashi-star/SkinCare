package com.hcl.cart_service.controller;

import com.hcl.cart_service.dto.AddToCartDTO;
import com.hcl.cart_service.dto.CartResponseDTO;
import com.hcl.cart_service.dto.UpdateCartItemDTO;
import com.hcl.cart_service.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
public class CartController {
    
    private final CartService cartService;
    
    /**
     * Add item to cart
     * POST /carts/add
     */
    @PostMapping("/add")
    public ResponseEntity<CartResponseDTO> addToCart(@Valid @RequestBody AddToCartDTO addToCartDTO) {
        log.info("REST request to add product {} to cart for user {}", 
            addToCartDTO.getProductId(), addToCartDTO.getUserId());
        CartResponseDTO cart = cartService.addToCart(addToCartDTO);
        return new ResponseEntity<>(cart, HttpStatus.CREATED);
    }
    
    /**
     * Get cart by user ID
     * GET /carts/user/{userId}
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<CartResponseDTO> getCartByUserId(@PathVariable Long userId) {
        log.info("REST request to get cart for user: {}", userId);
        CartResponseDTO cart = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(cart);
    }
    
    /**
     * Update cart item quantity
     * PUT /carts/user/{userId}/items/{cartItemId}
     */
    @PutMapping("/user/{userId}/items/{cartItemId}")
    public ResponseEntity<CartResponseDTO> updateCartItem(
            @PathVariable Long userId,
            @PathVariable Long cartItemId,
            @Valid @RequestBody UpdateCartItemDTO updateDTO) {
        log.info("REST request to update cart item {} for user {}", cartItemId, userId);
        CartResponseDTO cart = cartService.updateCartItem(userId, cartItemId, updateDTO);
        return ResponseEntity.ok(cart);
    }
    
    /**
     * Remove cart item
     * DELETE /carts/user/{userId}/items/{cartItemId}
     */
    @DeleteMapping("/user/{userId}/items/{cartItemId}")
    public ResponseEntity<CartResponseDTO> removeCartItem(
            @PathVariable Long userId,
            @PathVariable Long cartItemId) {
        log.info("REST request to remove cart item {} for user {}", cartItemId, userId);
        CartResponseDTO cart = cartService.removeCartItem(userId, cartItemId);
        return ResponseEntity.ok(cart);
    }
    
    /**
     * Clear entire cart
     * DELETE /carts/user/{userId}
     */
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Map<String, String>> clearCart(@PathVariable Long userId) {
        log.info("REST request to clear cart for user {}", userId);
        cartService.clearCart(userId);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Cart cleared successfully");
        response.put("userId", userId.toString());
        
        return ResponseEntity.ok(response);
    }
}
