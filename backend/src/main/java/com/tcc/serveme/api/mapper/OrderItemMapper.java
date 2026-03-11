package com.tcc.serveme.api.mapper;

import com.tcc.serveme.api.dto.order.*;
import com.tcc.serveme.api.entity.OrderItem;

public class OrderItemMapper {

    public static OrderItemDetailsResponse toDetailsResponse(OrderItem orderItem) {
        return new OrderItemDetailsResponse(
                orderItem.getId(),
                orderItem.getProductId(),
                orderItem.getProductName(),
                orderItem.getProductPrice(),
                orderItem.getQuantity(),
                orderItem.getNotes(),
                orderItem.isCanceled()
        );
    }
}