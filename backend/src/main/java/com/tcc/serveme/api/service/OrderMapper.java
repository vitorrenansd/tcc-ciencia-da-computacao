package com.tcc.serveme.api.service;

import com.tcc.serveme.api.dto.OrderRequest;
import com.tcc.serveme.api.dto.OrderItemRequest;
import com.tcc.serveme.api.model.Orders;
import com.tcc.serveme.api.model.OrderItem;

import java.util.List;

public class OrderMapper {
    public static Orders toModel(OrderRequest dto, List<OrderItem> items) {
        return new Orders(
            dto.tableNumber(),
            dto.customerName(),
            items
        );
    }

    public static OrderItem toModel(OrderItemRequest dto) {
        return new OrderItem(
            dto.productId(),
            dto.quantity(),
            dto.notes()
        );
    }
}