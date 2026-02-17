package com.tcc.serveme.api.dto.category;

public record NewProductCategoryRequest(
    String name,
    Boolean inactive
) {}