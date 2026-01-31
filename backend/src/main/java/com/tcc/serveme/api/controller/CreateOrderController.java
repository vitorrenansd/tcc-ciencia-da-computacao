package com.tcc.serveme.api.controller;

import com.tcc.serveme.api.dto.OrderRequest;
import com.tcc.serveme.api.service.CreateOrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@CrossOrigin("*")
public class CreateOrderController {
    private final CreateOrderService createOrderService;

    @Autowired
    public CreateOrderController(CreateOrderService orderService) {
        this.createOrderService = orderService;
    }

    @PostMapping("/create")
    public void send(@RequestBody OrderRequest request) { // change this method from void to OrderRequest when the API is done
        createOrderService.create(request);
    }
}