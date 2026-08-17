package com.distributed_mircorservice.orderservice.service;

import com.distributed_mircorservice.orderservice.controller.ProductClient;
import com.netflix.discovery.DiscoveryClient;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.stylesheets.LinkStyle;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final RestTemplate restTemplate;
    private final RestClient restClient;
    private final ProductClient productClient;
    private final Retry customRetry;

    @Value("${product.service.base-url}")
    private String baseUrl;

    public OrderServiceImpl(RestTemplate restTemplate, RestClient restClient, ProductClient productClient, Retry customRetry) {
        this.restTemplate = restTemplate;
        this.restClient = restClient;
        this.productClient = productClient;
        this.customRetry = customRetry;
    }

    // RestTemplate Synchronous Communication
    @Override
    public String getOrderDtl(Integer id) {

//         invoke product API through RestTemple Approach

        String response = restTemplate.getForObject(baseUrl+ "/products/{id}", String.class, id); // RestTemplate

//        // RestClient request
//        String response =  restClient
//                .get()
//                        .uri(baseUrl+ "/products/{id}", id)
//                                .retrieve()
//                                        .body(String.class);
        System.out.println("Response from Product Service call from Order Service: " + response);
        return response;
    }
//
//    @Override
//    // This is for RateLimiter Approach
//    @RateLimiter(name = "productRateLimiter", fallbackMethod = "rateLimiterFallBack")
//    public void invokeProductAPI(Integer id) {
//        String response = productClient.getProductById(id);
//        System.out.println("Response from Product Service call from Order Service: " + response);
//    }

//    public void rateLimiterFallBack(Integer id, Throwable ex) {
//        System.out.println(
//                "Rate limit exceeded for product id: " + id
//        );
//        System.out.println(
//                "Exception: " + ex.getMessage()
//        );
//    }

//    @Override
//    // This is for Bulkhead Semaphore Approach
//    @Bulkhead(name = "productService", type = Bulkhead.Type.SEMAPHORE, fallbackMethod = "bulkHeadFallBack")
//    public void invokeProductAPI(Integer id) {
//        String response = productClient.getProductById(id);
//        System.out.println("Response from Product Service call from Order Service: " + response);
//    }
//
//
//
//    public void bulkHeadFallBack(Integer id, Throwable ex) {
//
//        System.out.println("========== BULKHEAD FALLBACK ==========");
//        System.out.println("Product ID: " + id);
//        System.out.println("Exception: " + ex.getClass().getName());
//        System.out.println("Message: " + ex.getMessage());
//    }


//    @Override
//    // This is for Retry Approach
//    @Retry(name = "productService", fallbackMethod = "retryFallBack")
//    public void invokeProductAPI(Integer id) {
//        String response = "";
//        try {
//            response  = productClient.getProductById(id); // For test this: do not start product service and then hit the API.
//        } catch (Exception e){
//            System.out.println("[" + LocalDateTime.now() + "] It is not able to invoke Product API");
//            throw e;
//        }
//        System.out.println("Response from Product Service call from Order Service: " + response);
//
//
//    }

    public void retryFallBack(Integer id, Throwable ex) {

        System.out.println("========== Retry FALLBACK ==========");
        System.out.println("Product ID: " + id);
        System.out.println("Exception: " + ex.getClass().getName());
        System.out.println("Message: " + ex.getMessage());
    }


    @Override
    // This is for Custom Retry Approach
    public void invokeProductAPI(Integer id) {
        try {
            customRetry.executeSupplier( () -> {
                System.out.println("Invoking Product API call from Order Service at: " + LocalDateTime.now());
                return productClient.getProductById(id);
            });
        } catch (Exception e){
            System.out.println("[" + LocalDateTime.now() + "] It is not able to invoke Product API. This is Fallback");
//            throw e;
        }


    }
}
