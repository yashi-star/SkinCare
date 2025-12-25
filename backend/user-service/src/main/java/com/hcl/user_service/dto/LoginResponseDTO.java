package com.hcl.user_service.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {
    
    private String message;
    private UserDTO user;
    // In future, add JWT token here
    // private String token;
}
