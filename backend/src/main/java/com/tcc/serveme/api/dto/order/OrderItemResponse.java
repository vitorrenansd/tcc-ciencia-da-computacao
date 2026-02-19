package com.tcc.serveme.api.dto.order;

import java.math.BigDecimal;

public record OrderItemResponse(
    Long id,
    Long productId,
    String productName,
    BigDecimal price,
    Integer quantity,
    String notes,
    Boolean canceled
) {}