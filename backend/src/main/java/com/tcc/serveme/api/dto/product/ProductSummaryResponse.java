package com.tcc.serveme.api.dto.product;

import java.math.BigDecimal;

public record ProductSummaryResponse(
        Long id,
        String name,
        BigDecimal price
) {}