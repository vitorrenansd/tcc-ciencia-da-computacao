package com.tcc.serveme.api.mapper;

import com.tcc.serveme.api.dto.order.NewOrderRequest;
import com.tcc.serveme.api.dto.order.OrderItemRequest;
import com.tcc.serveme.api.dto.order.PendingOrdersResponse;
import com.tcc.serveme.api.model.Order;
import com.tcc.serveme.api.model.OrderItem;

public class OrderMapper {
    public static Order toModel(NewOrderRequest dto) {
        return new Order(
            dto.tableNumber(),
            dto.customerName()
        );
    }

    public static OrderItem toModel(OrderItemRequest dto, Long orderId) {
        return new OrderItem(
            orderId,
            dto.productId(),
            dto.quantity(),
            dto.notes()
        );
    }

    public static PendingOrdersResponse toResponse(Order order) {
        return new PendingOrdersResponse(
                order.getId(),
                order.getTableNumber(),
                order.getCustomerName(),
                order.getCreatedAt(),
                order.getStatus().name()
        );
    }
}