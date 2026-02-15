package com.tcc.serveme.api.dto.order;

public record OrderItemRequest(
    Long productId,
    Integer quantity,
    String notes
) {}