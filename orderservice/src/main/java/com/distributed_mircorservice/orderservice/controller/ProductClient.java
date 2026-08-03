package com.distributed_mircorservice.orderservice.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service",
url = "${product.service.base-url}")
public interface ProductClient {
    @GetMapping("/products/{id}")
    public String  getProductById(@PathVariable Integer id);
}
