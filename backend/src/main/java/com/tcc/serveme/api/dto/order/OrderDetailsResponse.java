package com.tcc.serveme.api.dto.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderDetailsResponse(
    Long id,
    Integer tableNumber,
    String customerName,
    BigDecimal totalPrice,
    String status,
    LocalDateTime createdAt,
    List<OrderItemDetailsResponse> items
) {}