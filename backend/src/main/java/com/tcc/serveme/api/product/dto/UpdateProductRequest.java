package com.tcc.serveme.api.product.dto;

import java.math.BigDecimal;

public record UpdateProductRequest(
        Long categoryId,
        String name,
        String description,
        BigDecimal price,
        Boolean active,
        Boolean available
) {}