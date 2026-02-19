package com.tcc.serveme.api.dto.order;

import java.time.LocalDateTime;
import java.util.List;

public record OrderDetailsResponse(
    Long id,
    Integer tableNumber,
    String customerName,
    String status,
    LocalDateTime createdAt,
    List<OrderItemResponse> items
) {}