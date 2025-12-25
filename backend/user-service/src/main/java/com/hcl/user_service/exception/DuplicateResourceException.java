package com.hcl.user_service.exception;


// ============================================
// FILE 2: DuplicateResourceException.java
// ============================================

public class DuplicateResourceException extends RuntimeException {
    
    public DuplicateResourceException(String message) {
        super(message);
    }
}
