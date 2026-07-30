package com.distributed_mircorservice.orderservice.controller;

import com.distributed_mircorservice.orderservice.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getOrder(@PathVariable Integer id) {

        String response = orderService.getOrderDtl(id);
        return ResponseEntity.ok("Order call successful. " + response);
    }
}
