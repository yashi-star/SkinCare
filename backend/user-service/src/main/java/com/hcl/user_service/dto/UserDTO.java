package com.hcl.user_service.dto;


// ============================================
// FILE 3: UserDTO.java
// Path: dto/UserDTO.java
// ============================================

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    
    private Long id;
    private String email;
    // NOTE: Never send password back to client!
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private String skinType;
    private String skinConcerns;
    private Boolean active;
}
