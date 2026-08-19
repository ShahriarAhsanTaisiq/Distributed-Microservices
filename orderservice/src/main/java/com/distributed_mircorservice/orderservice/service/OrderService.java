package com.distributed_mircorservice.orderservice.service;

public interface OrderService {

    String getUsingRestTemplate(Integer id);

    String getUsingRestClient(Integer id);

    String getUsingFeign(Integer id);

    String invokeWithRateLimiter(Integer id);

    String invokeWithBulkhead(Integer id);

    String invokeWithRetry(Integer id);

    String invokeWithCustomRetry(Integer id);

    String invokeWithCircuitBreaker(Integer id);

    String invokeUsingHttpURLConnection(Integer id);
}