package com.tcc.serveme.api.product.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.*;

public record UpdateProductRequest(
        @NotNull(message = "Categoria é obrigatória")
        Long categoryId,

        @NotBlank(message = "Nome é obrigatório")
        String name,

        String description,

        @NotNull(message = "Preço é obrigatório")
        @Positive(message = "Preço deve ser maior que zero")
        BigDecimal price,

        @NotNull(message = "Campo active é obrigatório")
        Boolean active,

        @NotNull(message = "Campo available é obrigatório")
        Boolean available
) {}