package com.tcc.serveme.api.order.controller;

import com.tcc.serveme.api.order.dto.NewOrderRequest;
import com.tcc.serveme.api.order.dto.OrderDetailsResponse;
import com.tcc.serveme.api.order.dto.OrdersByStatusResponse;
import com.tcc.serveme.api.order.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    @PostMapping
    public ResponseEntity<?> create(@RequestBody NewOrderRequest request) {
        Long id = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<OrdersByStatusResponse>> getAllPending() {
        return ResponseEntity.ok(orderService.getPendingOrders());
    }

    @GetMapping("/in-progress")
    public ResponseEntity<List<OrdersByStatusResponse>> getAllInProgress() {
        return ResponseEntity.ok(orderService.getOrdersInProgress());
    }

    @GetMapping("/served")
    public ResponseEntity<List<OrdersByStatusResponse>> getAllServed() {
        return ResponseEntity.ok(orderService.getServedOrders());
    }

    @GetMapping("/paid")
    public ResponseEntity<List<OrdersByStatusResponse>> getAllPaid() {
        return ResponseEntity.ok(orderService.getPaidOrders());
    }

    @GetMapping("/canceled")
    public ResponseEntity<List<OrdersByStatusResponse>> getAllCanceled() {
        return ResponseEntity.ok(orderService.getCanceledOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailsResponse> getById(@PathVariable Long id) {
        OrderDetailsResponse response = orderService.findDetailsById(id);
        return ResponseEntity.ok(response);
    }
}