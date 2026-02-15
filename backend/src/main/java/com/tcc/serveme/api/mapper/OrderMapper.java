package com.tcc.serveme.api.mapper;

import com.tcc.serveme.api.dto.OrderRequest;
import com.tcc.serveme.api.dto.OrderItemRequest;
import com.tcc.serveme.api.dto.OrderResponse;
import com.tcc.serveme.api.model.Orders;
import com.tcc.serveme.api.model.OrderItem;

public class OrderMapper {
    public static Orders toModel(OrderRequest dto) {
        return new Orders(
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

    public static OrderResponse toResponse(Orders order) {
        return new OrderResponse(
                order.getId(),
                order.getTableNumber(),
                order.getCustomerName(),
                order.getCreatedAt(),
                order.getStatus().name()
        );
    }
}