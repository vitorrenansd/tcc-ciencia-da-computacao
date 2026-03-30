package com.tcc.serveme.api.product.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.*;

public record NewProductRequest(
    @NotNull(message = "Categoria é obrigatória")
    Long categoryId,

    @NotBlank(message = "Nome é obrigatório")
    String name,

    String description,

    @NotNull(message = "Preço é obrigatório")
    @Positive(message = "Preço deve ser maior que zero")
    BigDecimal price,

    String imageUrl, // sem anotação Jakarta, é opcional

    Boolean active,

    Boolean available
) {}