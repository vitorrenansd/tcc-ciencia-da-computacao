package com.tcc.serveme.api.order.dto;

public record NewOrderItemRequest(
    Long productId,
    Integer quantity,
    String notes
) {}