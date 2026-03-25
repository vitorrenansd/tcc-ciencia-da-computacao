package com.tcc.serveme.api.order.controller;

import com.tcc.serveme.api.order.dto.NewOrderRequest;
import com.tcc.serveme.api.order.dto.OrderDetailsResponse;
import com.tcc.serveme.api.order.dto.OrdersByStatusResponse;
import com.tcc.serveme.api.order.dto.UpdateOrderStatusRequest;
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

    @GetMapping
    public ResponseEntity<List<OrdersByStatusResponse>> getByStatus(
            @RequestParam(required = false) String status) {

        if (status != null && !status.isBlank()) {
            return ResponseEntity.ok(orderService.getOrdersByStatus(status));
        }
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailsResponse> getById(@PathVariable Long id) {
        OrderDetailsResponse response = orderService.findDetailsById(id);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(
            @PathVariable Long id,
            @RequestBody UpdateOrderStatusRequest request) {

        orderService.updateOrderStatus(id, request);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/items/{itemId}/cancel")
    public ResponseEntity<Void> cancelItem(
            @PathVariable Long id,
            @PathVariable Long itemId) {

        orderService.cancelOrderItem(id, itemId);
        return ResponseEntity.noContent().build();
    }
}