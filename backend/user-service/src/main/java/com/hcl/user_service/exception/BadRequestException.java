package com.hcl.user_service.exception;


// ============================================
// FILE 4: BadRequestException.java
// ============================================

public class BadRequestException extends RuntimeException {
    
    public BadRequestException(String message) {
        super(message);
    }
}
