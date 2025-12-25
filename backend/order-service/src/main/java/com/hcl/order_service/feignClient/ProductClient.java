package com.hcl.order_service.feignClient;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "product-service")
public interface ProductClient {
    
    @GetMapping("/products/{id}")
    ProductDTO getProductById(@PathVariable Long id);
    
    @PutMapping("/products/{id}/stock")
    void updateStock(@PathVariable Long id, @RequestParam Integer quantity);
}