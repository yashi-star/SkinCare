package com.hcl.notification_service.feignClient;

import com.hcl.notification_service.dto.OrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "order-service")
public interface OrderClient {
    
    @GetMapping("/orders/{id}")
    OrderDTO getOrderById(@PathVariable Long id);
}