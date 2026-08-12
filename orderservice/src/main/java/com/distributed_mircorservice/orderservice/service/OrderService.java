package com.distributed_mircorservice.orderservice.service;

public interface OrderService {

    String getOrderDtl(Integer id);

    void invokeProductAPI(Integer id);
}
