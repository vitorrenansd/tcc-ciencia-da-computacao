package com.tcc.serveme.api.category.dto;

public record NewProductCategoryRequest(
    String name,
    Boolean inactive
) {}