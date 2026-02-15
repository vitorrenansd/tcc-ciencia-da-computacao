package com.tcc.serveme.api.service;

import com.tcc.serveme.api.dto.order.NewOrderRequest;
import com.tcc.serveme.api.dto.order.OrderItemRequest;
import com.tcc.serveme.api.dto.order.PendingOrdersResponse;
import com.tcc.serveme.api.mapper.OrderMapper;
import com.tcc.serveme.api.model.Orders;
import com.tcc.serveme.api.model.OrderItem;
import com.tcc.serveme.api.repository.OrdersRepository;
import com.tcc.serveme.api.repository.OrderItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        Orders order = OrderMapper.toModel(request);
    public void createOrder(NewOrderRequest request) {
        Long orderId = ordersRepo.save(order);

        for (OrderItemRequest itemRequest : request.items()) {
            OrderItem item = OrderMapper.toModel(itemRequest, orderId);
            orderItemRepo.save(item);
        }
    }

    public List<PendingOrdersResponse> getPendingOrders() {
        return ordersRepo.findAllPendingOrders()
                .stream()
                .map(OrderMapper::toResponse)
                .toList();
    }
}