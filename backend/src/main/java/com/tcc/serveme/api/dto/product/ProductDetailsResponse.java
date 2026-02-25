package com.tcc.serveme.api.dto.product;

import java.math.BigDecimal;

public record ProductDetailsResponse(
        String name,
        String description,
        BigDecimal price
) {}
