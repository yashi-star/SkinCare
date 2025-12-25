package com.hcl.user_service.exception;


// ============================================
// FILE 3: InvalidCredentialsException.java
// ============================================


public class InvalidCredentialsException extends RuntimeException {
    
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
