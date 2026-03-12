package com.tcc.serveme.api.product.dto;

import java.math.BigDecimal;

public record NewProductRequest(
    Long categoryId,
    String name,
    String description,
    BigDecimal price,
    Boolean inactive
) {}