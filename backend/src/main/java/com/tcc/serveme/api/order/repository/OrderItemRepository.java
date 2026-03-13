package com.tcc.serveme.api.order.repository;

import com.tcc.serveme.api.order.entity.OrderItem;

import java.util.List;

public interface OrderItemRepository {
    int save(OrderItem orderItem);

    List<OrderItem> findByOrderId(Long orderId);
    List<OrderItem> findActiveOrderItemsByTableNumber(Integer tableNumber);
    int cancel(Long id);
}