package com.tcc.serveme.api.dto.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PendingOrdersResponse(
    Long id,
    Integer tableNumber,
    String customerName,
    BigDecimal totalPrice,
    LocalDateTime createdAt,
    String status
) {}