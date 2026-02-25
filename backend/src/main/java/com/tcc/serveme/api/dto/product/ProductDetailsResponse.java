package com.tcc.serveme.api.dto.product;

import java.math.BigDecimal;

public record ProductDetailsResponse(
        Long id,
        String name,
        String description,
        BigDecimal price
) {}
