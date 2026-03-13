package com.tcc.serveme.api.order.dto;

import java.math.BigDecimal;

public record OrderItemDetailsResponse(
    Long id,
    Long productId,
    String productName,
    BigDecimal productPrice,
    Integer quantity,
    String notes,
    Boolean canceled
) {}