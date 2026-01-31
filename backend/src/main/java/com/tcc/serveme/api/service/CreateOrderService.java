package com.tcc.serveme.api.service;

import com.tcc.serveme.api.dto.OrderRequest;

import org.springframework.stereotype.Service;

@Service
public class CreateOrderService {
    public void create(OrderRequest request) {
        System.out.println(request); // Placeholder

        // Parte de salvamento em banco aqui
        
    }
}