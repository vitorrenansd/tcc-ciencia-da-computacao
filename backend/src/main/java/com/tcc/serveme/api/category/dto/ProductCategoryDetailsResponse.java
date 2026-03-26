package com.tcc.serveme.api.category.dto;

public record ProductCategoryDetailsResponse(
    Long id,
    String name,
    Boolean active
) {}