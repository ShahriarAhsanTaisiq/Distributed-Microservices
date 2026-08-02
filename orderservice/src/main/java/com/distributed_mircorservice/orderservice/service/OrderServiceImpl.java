package com.distributed_mircorservice.orderservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderServiceImpl implements OrderService {

    private final RestTemplate restTemplate;
    private final RestClient restClient;

    @Value("${product.service.base-url}")
    private String baseUrl;

    public OrderServiceImpl(RestTemplate restTemplate, RestClient restClient) {
        this.restTemplate = restTemplate;
        this.restClient = restClient;
    }

    // RestTemplate Synchronous Communication
    @Override
    public String getOrderDtl(Integer id) {

        // invoke product API through RestTemple Approach
//        String response = restTemplate.getForObject(baseUrl+ "/products/{id}", String.class, id); // RestTemplate

        // RestClient request
        String response =  restClient
                .get()
                        .uri(baseUrl+ "/products/{id}", id)
                                .retrieve()
                                        .body(String.class);
        System.out.println("Response from Product Service call from Order Service: " + response);
        return response;
    }
}
