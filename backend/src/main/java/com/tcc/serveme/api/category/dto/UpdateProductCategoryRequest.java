package com.tcc.serveme.api.category.dto;

public record UpdateProductCategoryRequest(
        String name,
        Boolean active
) {}