package com.hcl.cart_service.service;


import com.hcl.cart_service.feignClient.ProductClient;
import com.hcl.cart_service.dto.*;
import com.hcl.cart_service.entity.Cart;
import com.hcl.cart_service.entity.CartItem;
import com.hcl.cart_service.exception.ResourceNotFoundException;
import com.hcl.cart_service.repository.CartItemRepository;
import com.hcl.cart_service.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CartServiceImpl implements CartService {
    
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductClient productClient;
    
    @Override
    public CartResponseDTO addToCart(AddToCartDTO addToCartDTO) {
        log.info("Adding product {} to cart for user {}", addToCartDTO.getProductId(), addToCartDTO.getUserId());
        
        // Get product details from Product Service
        ProductDTO product = productClient.getProductById(addToCartDTO.getProductId());
        
        // Check stock availability
        if (product.getStock() < addToCartDTO.getQuantity()) {
            throw new IllegalArgumentException("Insufficient stock. Available: " + product.getStock());
        }
        
        // Get or create cart
        Cart cart = cartRepository.findByUserId(addToCartDTO.getUserId())
            .orElseGet(() -> {
                Cart newCart = new Cart();
                newCart.setUserId(addToCartDTO.getUserId());
                return cartRepository.save(newCart);
            });
        
        // Check if item already exists in cart
        CartItem cartItem = cartItemRepository
            .findByCartIdAndProductId(cart.getId(), addToCartDTO.getProductId())
            .orElse(null);
        
        if (cartItem != null) {
            // Update quantity if item exists
            int newQuantity = cartItem.getQuantity() + addToCartDTO.getQuantity();
            if (product.getStock() < newQuantity) {
                throw new IllegalArgumentException("Insufficient stock. Available: " + product.getStock());
            }
            cartItem.setQuantity(newQuantity);
            cartItem.calculateSubtotal();
        } else {
            // Create new cart item
            cartItem = new CartItem();
            cartItem.setProductId(product.getId());
            cartItem.setProductName(product.getName());
            cartItem.setPrice(product.getPrice());
            cartItem.setQuantity(addToCartDTO.getQuantity());
            cartItem.setImageUrl(product.getImageUrl());
            cart.addItem(cartItem);
        }
        
        cartItemRepository.save(cartItem);
        cart.calculateTotal();
        Cart savedCart = cartRepository.save(cart);
        
        return mapToDTO(savedCart);
    }
    
    @Override
    @Transactional(readOnly = true)
    public CartResponseDTO getCartByUserId(Long userId) {
        log.info("Fetching cart for user {}", userId);
        
        Cart cart = cartRepository.findByUserId(userId)
            .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user: " + userId));
        
        return mapToDTO(cart);
    }
    
    @Override
    public CartResponseDTO updateCartItem(Long userId, Long cartItemId, UpdateCartItemDTO updateDTO) {
        log.info("Updating cart item {} for user {}", cartItemId, userId);
        
        Cart cart = cartRepository.findByUserId(userId)
            .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user: " + userId));
        
        CartItem cartItem = cartItemRepository.findById(cartItemId)
            .orElseThrow(() -> new ResourceNotFoundException("Cart item not found: " + cartItemId));
        
        if (!cartItem.getCart().getId().equals(cart.getId())) {
            throw new IllegalArgumentException("Cart item does not belong to this user's cart");
        }
        
        // Verify stock
        ProductDTO product = productClient.getProductById(cartItem.getProductId());
        if (product.getStock() < updateDTO.getQuantity()) {
            throw new IllegalArgumentException("Insufficient stock. Available: " + product.getStock());
        }
        
        cartItem.setQuantity(updateDTO.getQuantity());
        cartItem.calculateSubtotal();
        cartItemRepository.save(cartItem);
        
        cart.calculateTotal();
        Cart savedCart = cartRepository.save(cart);
        
        return mapToDTO(savedCart);
    }
    
    @Override
    public CartResponseDTO removeCartItem(Long userId, Long cartItemId) {
        log.info("Removing cart item {} for user {}", cartItemId, userId);
        
        Cart cart = cartRepository.findByUserId(userId)
            .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user: " + userId));
        
        CartItem cartItem = cartItemRepository.findById(cartItemId)
            .orElseThrow(() -> new ResourceNotFoundException("Cart item not found: " + cartItemId));
        
        if (!cartItem.getCart().getId().equals(cart.getId())) {
            throw new IllegalArgumentException("Cart item does not belong to this user's cart");
        }
        
        cart.removeItem(cartItem);
        cartItemRepository.delete(cartItem);
        
        cart.calculateTotal();
        Cart savedCart = cartRepository.save(cart);
        
        return mapToDTO(savedCart);
    }
    
    @Override
    public void clearCart(Long userId) {
        log.info("Clearing cart for user {}", userId);
        
        Cart cart = cartRepository.findByUserId(userId)
            .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user: " + userId));
        
        cart.getItems().clear();
        cart.calculateTotal();
        cartRepository.save(cart);
    }
    
    private CartResponseDTO mapToDTO(Cart cart) {
        CartResponseDTO dto = new CartResponseDTO();
        dto.setId(cart.getId());
        dto.setUserId(cart.getUserId());
        dto.setTotalAmount(cart.getTotalAmount());
        dto.setTotalItems(cart.getItems().size());
        dto.setCreatedAt(cart.getCreatedAt());
        dto.setUpdatedAt(cart.getUpdatedAt());
        
        dto.setItems(cart.getItems().stream()
            .map(this::mapItemToDTO)
            .collect(Collectors.toList()));
        
        return dto;
    }
    
    private CartItemDTO mapItemToDTO(CartItem item) {
        CartItemDTO dto = new CartItemDTO();
        dto.setId(item.getId());
        dto.setProductId(item.getProductId());
        dto.setProductName(item.getProductName());
        dto.setPrice(item.getPrice());
        dto.setQuantity(item.getQuantity());
        dto.setSubtotal(item.getSubtotal());
        dto.setImageUrl(item.getImageUrl());
        dto.setAddedAt(item.getAddedAt());
        return dto;
    }
}

