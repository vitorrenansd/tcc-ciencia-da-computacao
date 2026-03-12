package com.tcc.serveme.api.product.dto;

import java.math.BigDecimal;

public record ProductSummaryResponse(
        Long id,
        String name,
        BigDecimal price
) {}