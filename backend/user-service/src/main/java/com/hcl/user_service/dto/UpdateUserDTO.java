package com.hcl.user_service.dto;


// ============================================
// FILE 4: UpdateUserDTO.java
// Path: dto/UpdateUserDTO.java
// ============================================

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDTO {
    
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
