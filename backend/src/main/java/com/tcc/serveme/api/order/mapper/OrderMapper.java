package com.tcc.serveme.api.order.mapper;

import com.tcc.serveme.api.order.dto.OrderDetailsResponse;
import com.tcc.serveme.api.order.dto.OrderItemDetailsResponse;
import com.tcc.serveme.api.order.dto.OrdersByStatusResponse;
import com.tcc.serveme.api.order.entity.Order;

import java.util.List;

public class OrderMapper {

    public static OrdersByStatusResponse toOrdersByStatus(Order order) {
        return new OrdersByStatusResponse(
                order.getId(),
                order.getTableNumber(),
                order.getCustomerName(),
                order.getTotalPrice(),
                order.getCreatedAt()
        );
    }

    public static OrderDetailsResponse toDetailsResponse(Order order, List<OrderItemDetailsResponse> items) {
        return new OrderDetailsResponse(
                order.getId(),
                order.getCashShiftId(),
                order.getTableNumber(),
                order.getCustomerName(),
                order.getTotalPrice(),
                order.getStatus().name(),
                order.getCreatedAt(),
                items
        );
    }
}