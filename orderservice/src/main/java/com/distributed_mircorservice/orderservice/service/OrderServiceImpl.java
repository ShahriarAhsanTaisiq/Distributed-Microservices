package com.distributed_mircorservice.orderservice.service;

import com.distributed_mircorservice.orderservice.controller.ProductClient;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.Retry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class OrderServiceImpl implements OrderService {

    private final RestTemplate restTemplate;
    private final RestClient restClient;
    private final ProductClient productClient;
    private final Retry customRetry;

    @Value("${product.service.base-url}")
    private String baseUrl;


    public OrderServiceImpl(
            RestTemplate restTemplate,
            RestClient restClient,
            ProductClient productClient,
            Retry customRetry) {

        this.restTemplate = restTemplate;
        this.restClient = restClient;
        this.productClient = productClient;
        this.customRetry = customRetry;
    }


    // ============================================================
    // 1. RestTemplate
    // ============================================================

    @Override
    public String getUsingRestTemplate(Integer id) {

        String response = restTemplate.getForObject(
                baseUrl + "/products/{id}",
                String.class,
                id
        );

        System.out.println(
                "Response from Product Service using RestTemplate: "
                        + response
        );

        return response;
    }


    // ============================================================
    // 2. RestClient
    // ============================================================

    @Override
    public String getUsingRestClient(Integer id) {

        String response = restClient
                .get()
                .uri(baseUrl + "/products/{id}", id)
                .retrieve()
                .body(String.class);

        System.out.println(
                "Response from Product Service using RestClient: "
                        + response
        );

        return response;
    }


    // ============================================================
    // 3. Feign / ProductClient
    // ============================================================

    @Override
    public String getUsingFeign(Integer id) {

        String response = productClient.getProductById(id);

        System.out.println(
                "Response from Product Service using ProductClient: "
                        + response
        );

        return response;
    }


    // ============================================================
    // 4. RateLimiter
    // ============================================================

    @Override
    @RateLimiter(
            name = "productRateLimiter",
            fallbackMethod = "rateLimiterFallback"
    )
    public String invokeWithRateLimiter(Integer id) {

        System.out.println(
                "[" + LocalDateTime.now() +
                        "] Calling Product Service using RateLimiter"
        );

        String response = productClient.getProductById(id);

        return "Product API response: " + response;
    }


    public String rateLimiterFallback(
            Integer id,
            Throwable ex) {

        System.out.println(
                "========== RATE LIMITER FALLBACK =========="
        );

        System.out.println("Product ID: " + id);

        System.out.println(
                "Exception: " + ex.getClass().getName()
        );

        return "Rate limit exceeded for product id: " + id;
    }


    // ============================================================
    // 5. Bulkhead - Semaphore
    // ============================================================

    @Override
    @Bulkhead(
            name = "productService",
            type = Bulkhead.Type.SEMAPHORE,
            fallbackMethod = "bulkheadFallback"
    )
    public String invokeWithBulkhead(Integer id) {

        System.out.println(
                "[" + LocalDateTime.now() +
                        "] Calling Product Service using Bulkhead"
        );

        String response = productClient.getProductById(id);

        return "Product API response: " + response;
    }


    public String bulkheadFallback(
            Integer id,
            Throwable ex) {

        System.out.println(
                "========== BULKHEAD FALLBACK =========="
        );

        System.out.println("Product ID: " + id);

        System.out.println(
                "Exception: " + ex.getClass().getName()
        );

        System.out.println(
                "Message: " + ex.getMessage()
        );

        return "Bulkhead limit exceeded for product id: " + id;
    }


    // ============================================================
    // 6. Annotation Based Retry
    // ============================================================

    @Override
    //need two different Retry types in the same class.
    @io.github.resilience4j.retry.annotation.Retry(
            name = "productService",
            fallbackMethod = "retryFallback"
    )
    public String invokeWithRetry(Integer id) {

        System.out.println(
                "[" + LocalDateTime.now() +
                        "] Calling Product Service using Retry"
        );

        String response = productClient.getProductById(id);

        return "Product API response: " + response;
    }


    public String retryFallback(
            Integer id,
            Throwable ex) {

        System.out.println(
                "========== RETRY FALLBACK =========="
        );

        System.out.println("Product ID: " + id);

        System.out.println(
                "Exception: " + ex.getClass().getName()
        );

        System.out.println(
                "Message: " + ex.getMessage()
        );

        return "Product Service unavailable after retry attempts.";
    }


    // ============================================================
    // 7. Custom / Programmatic Retry
    // ============================================================

    @Override
    public String invokeWithCustomRetry(Integer id) {

        try {

            return customRetry.executeSupplier(() -> {

                System.out.println(
                        "[" + LocalDateTime.now() +
                                "] Invoking Product API using Custom Retry"
                );

                return productClient.getProductById(id);

            });

        } catch (Exception e) {

            System.out.println(
                    "[" + LocalDateTime.now() +
                            "] Product API failed. Custom Retry Fallback"
            );

            System.out.println(
                    "Exception: " + e.getClass().getName()
            );

            return "Custom Retry fallback. Product Service unavailable.";
        }
    }

    // ============================================================
    // 8. Circuit Breaker
    // ============================================================

    @Override
    @CircuitBreaker(
            name = "productService",
            fallbackMethod = "circuitBreakerFallback"
    )
    public String invokeWithCircuitBreaker(Integer id) {

        System.out.println(
                "[" + LocalDateTime.now() +
                        "] Calling Product Service using Circuit Breaker"
        );

        String response = productClient.getProductById(id);

        return "Product API response: " + response;
    }


    public String circuitBreakerFallback(
            Integer id,
            Throwable ex) {

        System.out.println(
                "========== CIRCUIT BREAKER FALLBACK =========="
        );

        System.out.println("Product ID: " + id);

        System.out.println(
                "Exception: " + ex.getClass().getName()
        );

        System.out.println(
                "Message: " + ex.getMessage()
        );

        return "Circuit Breaker fallback. Product Service is currently unavailable.";
    }


    // ============================================================
    // 9. Java HttpURLConnection
    // ============================================================

    @Override
    public String invokeUsingHttpURLConnection(Integer id) {

        HttpURLConnection connection = null;

        try {

            String url =
                    "http://localhost:8084/products/" + id;

            URL obj = new URL(url);

            connection =
                    (HttpURLConnection) obj.openConnection();

            // HTTP method
            connection.setRequestMethod("GET");

            // HTTP headers
            connection.setRequestProperty(
                    "Accept",
                    "application/json"
            );

            // Connection timeout
            connection.setConnectTimeout(1000);

            // Read timeout
            connection.setReadTimeout(10000);

            // Execute request and read response
            BufferedReader br =
                    new BufferedReader(
                            new InputStreamReader(
                                    connection.getInputStream()
                            )
                    );

            StringBuilder response =
                    new StringBuilder();

            String responseLine;

            while ((responseLine = br.readLine()) != null) {

                response.append(responseLine);
            }

            br.close();

            System.out.println(
                    "Response from Product Service using HttpURLConnection: "
                            + response
            );

            return response.toString();

        } catch (Exception e) {

            e.printStackTrace();

            return "HttpURLConnection failed: "
                    + e.getMessage();

        } finally {

            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
