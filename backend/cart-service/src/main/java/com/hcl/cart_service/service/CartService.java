package com.hcl.cart_service.service;

import com.hcl.cart_service.dto.AddToCartDTO;
import com.hcl.cart_service.dto.CartResponseDTO;
import com.hcl.cart_service.dto.UpdateCartItemDTO;

public interface CartService {
    
    CartResponseDTO addToCart(AddToCartDTO addToCartDTO);
    
    CartResponseDTO getCartByUserId(Long userId);
    
    CartResponseDTO updateCartItem(Long userId, Long cartItemId, UpdateCartItemDTO updateDTO);
    
    CartResponseDTO removeCartItem(Long userId, Long cartItemId);
    
    void clearCart(Long userId);
}

