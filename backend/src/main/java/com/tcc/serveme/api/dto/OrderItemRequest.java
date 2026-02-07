package com.tcc.serveme.api.dto;

public record OrderItemRequest(
    Long productId,
    Integer quantity,
    String notes
) {}