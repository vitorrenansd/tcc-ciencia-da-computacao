package com.tcc.serveme.api.category.dto;

import jakarta.validation.constraints.*;

public record NewProductCategoryRequest(
        @NotBlank(message = "Nome é obrigatório")
        String name,

        Boolean active
) {}