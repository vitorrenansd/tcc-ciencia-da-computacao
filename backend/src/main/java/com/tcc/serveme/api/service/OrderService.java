package com.tcc.serveme.api.service;

import com.tcc.serveme.api.dto.order.*;
import com.tcc.serveme.api.mapper.OrderMapper;
import com.tcc.serveme.api.model.Order;
import com.tcc.serveme.api.model.OrderItem;
import com.tcc.serveme.api.repository.OrderRepository;
import com.tcc.serveme.api.repository.OrderItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepo;
    private final OrderItemRepository orderItemRepo;

    @Autowired
    public OrderService(OrderRepository orderRepo, OrderItemRepository orderItemRepo) {
        this.orderRepo = orderRepo;
        this.orderItemRepo = orderItemRepo;
    }

    @Transactional
    public void createOrder(NewOrderRequest request) {
        Order order = OrderMapper.toModel(request);
        Long orderId = orderRepo.save(order);

        for (OrderItemRequest itemRequest : request.items()) {
            OrderItem item = OrderMapper.toModel(itemRequest, orderId);
            orderItemRepo.save(item);
        }
    }

    public List<PendingOrdersResponse> getPendingOrders() {
        return orderRepo.findAllPendingOrders()
                .stream()
                .map(OrderMapper::toResponse)
                .toList();
    }
}