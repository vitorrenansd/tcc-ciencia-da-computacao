package com.tcc.serveme.api.order.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderDetailsResponse(
    Long id,
    Long cashShiftId,
    Integer tableNumber,
    String customerName,
    BigDecimal totalPrice,
    String status,
    LocalDateTime createdAt,
    List<OrderItemDetailsResponse> items
) {}