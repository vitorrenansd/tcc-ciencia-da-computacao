package com.tcc.serveme.api.dto;

import java.time.LocalDateTime;

public record OrderResponse(
    Long id,
    Integer tableNumber,
    String customerName,
    LocalDateTime createdAt,
    String status
) {}