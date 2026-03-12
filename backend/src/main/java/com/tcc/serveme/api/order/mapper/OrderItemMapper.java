package com.tcc.serveme.api.order.mapper;

import com.tcc.serveme.api.order.dto.OrderItemDetailsResponse;
import com.tcc.serveme.api.order.entity.OrderItem;

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