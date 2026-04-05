package com.tcc.serveme.api.product.dto;

import java.math.BigDecimal;

public record ProductSummaryResponse(
        Long id,
        Long categoryId,
        String name,
        String description,
        BigDecimal price,
        String imageUrl,
        Boolean active,
        Boolean available
) {}