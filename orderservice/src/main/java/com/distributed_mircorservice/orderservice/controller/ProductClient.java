package com.distributed_mircorservice.orderservice.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Without Service Discovery
//@FeignClient(name = "product-service",
//url = "${product.service.base-url}")
//public interface ProductClient {
//    @GetMapping("/products/{id}")
//    String  getProductById(@PathVariable Integer id);
//}

// With Service Discovery Enable
@FeignClient(
        name = "product-service"
)
public interface ProductClient {
    @GetMapping("/products/{id}")
    String  getProductById(@PathVariable Integer id);
}
