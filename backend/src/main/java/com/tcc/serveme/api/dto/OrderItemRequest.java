package com.tcc.serveme.api.dto;

public record OrderItemRequest(
    Integer productId,
    Integer quantity,
    String notes
) {}