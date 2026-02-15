package com.tcc.serveme.api.controller;

import com.tcc.serveme.api.dto.OrderRequest;
import com.tcc.serveme.api.dto.OrderResponse;
import com.tcc.serveme.api.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@CrossOrigin("*")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public void send(@RequestBody OrderRequest request) {
        orderService.createOrder(request);
    }

    @GetMapping("/pending")
    public List<OrderResponse> getAllPendingOrders() {
        return orderService.getPendingOrders();
    }
}