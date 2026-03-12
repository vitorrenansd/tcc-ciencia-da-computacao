package com.tcc.serveme.api.order.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrdersByStatusResponse(
    Long id,
    Integer tableNumber,
    String customerName,
    BigDecimal totalPrice,
    LocalDateTime createdAt
) {}