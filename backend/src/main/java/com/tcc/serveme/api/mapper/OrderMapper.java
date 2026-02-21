package com.tcc.serveme.api.mapper;

import com.tcc.serveme.api.dto.order.*;
import com.tcc.serveme.api.model.Order;
import com.tcc.serveme.api.model.OrderItem;

import java.util.List;

public class OrderMapper {

    public static PendingOrdersResponse toResponse(Order order) {
        return new PendingOrdersResponse(
                order.getId(),
                order.getTableNumber(),
                order.getCustomerName(),
                order.getTotalPrice(),
                order.getCreatedAt(),
                order.getStatus().name()
        );
    }

    public static OrderDetailsResponse toResponse(Order order, List<OrderItemDetailsResponse> items) {
        return new OrderDetailsResponse(
                order.getId(),
                order.getTableNumber(),
                order.getCustomerName(),
                order.getTotalPrice(),
                order.getStatus().name(),
                order.getCreatedAt(),
                items
        );
    }
}