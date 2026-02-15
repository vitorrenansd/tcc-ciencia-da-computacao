package com.tcc.serveme.api.dto.product;

import java.math.BigDecimal;

public record NewProductRequest(
    Long categoryId,
    String name,
    String description,
    BigDecimal price,
    Boolean inactive
) {}