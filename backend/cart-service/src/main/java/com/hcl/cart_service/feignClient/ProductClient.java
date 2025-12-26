package com.hcl.cart_service.feignClient;


import com.hcl.cart_service.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductClient {
    
    @GetMapping("/products/{id}")
    ProductDTO getProductById(@PathVariable Long id);
}
