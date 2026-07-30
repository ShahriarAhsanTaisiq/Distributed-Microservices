package com.distributed_mircorservice.orderservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderServiceImpl implements OrderService {

    private final RestTemplate restTemplate;

    @Value("${product.service.base-url}")
    private String baseUrl;

    public OrderServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // RestTemplate Synchronous Communication
    @Override
    public String getOrderDtl(Integer id) {

        // invoke product API
        String response = restTemplate.getForObject(baseUrl+ "/products/{id}", String.class, id); // RestTemplate
        System.out.println("Response from Product Service call from Order Service: " + response);
        return response;
    }
}
