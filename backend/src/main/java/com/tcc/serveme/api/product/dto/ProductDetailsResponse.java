package com.tcc.serveme.api.product.dto;

import java.math.BigDecimal;

public record ProductDetailsResponse(
        Long id,
        Long categoryId,
        String categoryName,
        String name,
        String description,
        BigDecimal price,
        Boolean active,
        Boolean available
) {}