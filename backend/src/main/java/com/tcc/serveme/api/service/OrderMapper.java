package com.tcc.serveme.api.service;

import com.tcc.serveme.api.dto.*;
import com.tcc.serveme.api.model.*;

import java.util.List;

public class OrderMapper {

    public static Order toEntity(OrderRequest dto, List<OrderItem> items) {
        return new Order(
            dto.tableNumber(),
            dto.waiter(),
            items
        );
    }

    public static OrderItem toEntity(OrderItemRequest dto, Product product) {
        return new OrderItem(
            product,
            dto.quantity(),
            dto.notes()
        );
    }
}