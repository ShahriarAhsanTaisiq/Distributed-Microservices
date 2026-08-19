package com.distributed_mircorservice.orderservice.controller;

import com.distributed_mircorservice.orderservice.model.Order;
import com.distributed_mircorservice.orderservice.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // ============================================================
    // 1. RestTemplate
    // ============================================================

    @GetMapping("/rest-template/{id}")
    public ResponseEntity<String> getUsingRestTemplate(
            @PathVariable Integer id) {

        String response = orderService.getUsingRestTemplate(id);

        return ResponseEntity.ok(
                "RestTemplate call successful. " + response
        );
    }


    // ============================================================
    // 2. RestClient
    // ============================================================

    @GetMapping("/rest-client/{id}")
    public ResponseEntity<String> getUsingRestClient(
            @PathVariable Integer id) {

        String response = orderService.getUsingRestClient(id);

        return ResponseEntity.ok(
                "RestClient call successful. " + response
        );
    }


    // ============================================================
    // 3. ProductClient / Feign
    // ============================================================

    @GetMapping("/feign/{id}")
    public ResponseEntity<String> getUsingFeign(
            @PathVariable Integer id) {

        String response = orderService.getUsingFeign(id);

        return ResponseEntity.ok(
                "Feign call successful. " + response
        );
    }


    // ============================================================
    // 4. RateLimiter
    // ============================================================

    @GetMapping("/rate-limiter/{id}")
    public ResponseEntity<String> rateLimiter(
            @PathVariable Integer id) {

        String response = orderService.invokeWithRateLimiter(id);

        return ResponseEntity.ok(response);
    }


    // ============================================================
    // 5. Bulkhead
    // ============================================================

    @GetMapping("/bulkhead/{id}")
    public ResponseEntity<String> bulkhead(
            @PathVariable Integer id) {

        String response = orderService.invokeWithBulkhead(id);

        return ResponseEntity.ok(response);
    }


    // ============================================================
    // 6. Annotation based Retry
    // ============================================================

    @GetMapping("/retry/{id}")
    public ResponseEntity<String> retry(
            @PathVariable Integer id) {

        String response = orderService.invokeWithRetry(id);

        return ResponseEntity.ok(response);
    }


    // ============================================================
    // 7. Custom / Programmatic Retry
    // ============================================================

    @GetMapping("/custom-retry/{id}")
    public ResponseEntity<String> customRetry(
            @PathVariable Integer id) {

        String response = orderService.invokeWithCustomRetry(id);

        return ResponseEntity.ok(response);
    }

    // ============================================================
    // 8. Circuit Breaker
    // ============================================================
    @GetMapping("/circuit-breaker/{id}")
    public ResponseEntity<String> invokeWithCircuitBreaker(
            @PathVariable Integer id) {

        String response = orderService.invokeWithCircuitBreaker(id);

        return ResponseEntity.ok(response);
    }


    // ============================================================
    // 9. Java HttpURLConnection
    // ============================================================

    @GetMapping("/http-url-connection/{id}")
    public ResponseEntity<String> httpUrlConnection(
            @PathVariable Integer id) {

        String response = orderService.invokeUsingHttpURLConnection(id);

        return ResponseEntity.ok(response);
    }


    @GetMapping("/gateway/{id}")
    public ResponseEntity<Order> getOrderForGateway(
            @PathVariable Integer id) {

        Order order = new Order(
                id,
                10,
                2
        );

        return ResponseEntity.ok(order);
    }
}