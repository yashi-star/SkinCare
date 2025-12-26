package com.hcl.payment_service.feignClient;


import com.hcl.payment_service.dto.OrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "order-service")
public interface OrderClient {
    
    @GetMapping("/orders/{id}")
    OrderDTO getOrderById(@PathVariable Long id);
    
    @PatchMapping("/orders/{id}/payment")
    OrderDTO updatePaymentStatus(@PathVariable Long id, @RequestParam String paymentStatus);
}