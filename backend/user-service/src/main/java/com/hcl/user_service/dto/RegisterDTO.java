package com.hcl.user_service.dto;

// ============================================
// FILE 1: RegisterDTO.java
// Path: dto/RegisterDTO.java
// ============================================

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {
    
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
    
    @NotBlank(message = "First name is required")
    private String firstName;
    
    @NotBlank(message = "Last name is required")
    private String lastName;
    
    private String phone;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private String skinType;
    private String skinConcerns;
}
