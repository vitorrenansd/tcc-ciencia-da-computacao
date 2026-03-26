package com.tcc.serveme.api.category.dto;

import jakarta.validation.constraints.*;

public record UpdateProductCategoryRequest(
        @NotBlank(message = "Nome é obrigatório")
        String name,

        @NotNull(message = "Campo active é obrigatório")
        Boolean active
) {}