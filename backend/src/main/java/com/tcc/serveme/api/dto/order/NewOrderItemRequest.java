package com.tcc.serveme.api.dto.order;

public record NewOrderItemRequest(
    Long productId,
    Integer quantity,
    String notes
) {}