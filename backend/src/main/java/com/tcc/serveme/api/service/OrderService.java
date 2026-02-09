package com.tcc.serveme.api.service;

import com.tcc.serveme.api.dto.OrderRequest;
import com.tcc.serveme.api.model.Orders;
import com.tcc.serveme.api.model.OrderItem;
import com.tcc.serveme.api.repository.OrdersRepository;
import com.tcc.serveme.api.repository.OrderItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
    private final OrdersRepository ordersRepo;
    private final OrderItemRepository orderItemRepo;

    @Autowired
    public OrderService(OrdersRepository ordersRepo, OrderItemRepository orderItemRepo) {
        this.ordersRepo = ordersRepo;
        this.orderItemRepo = orderItemRepo;
    }


    @Transactional
    public void createOrder(OrderRequest request) {

    }
}