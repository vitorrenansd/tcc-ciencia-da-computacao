package com.tcc.serveme.api.product.dto;

import java.math.BigDecimal;

public record ProductSummaryResponse(
        Long id,
        Long categoryId,
        String name,
        BigDecimal price,
        String imageUrl
) {}