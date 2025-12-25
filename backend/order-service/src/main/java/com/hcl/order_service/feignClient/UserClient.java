package com.hcl.order_service.feignClient;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "user-service")
public interface UserClient {
    
    @GetMapping("/users/{id}")
    UserDTO getUserById(@PathVariable Long id);
}
