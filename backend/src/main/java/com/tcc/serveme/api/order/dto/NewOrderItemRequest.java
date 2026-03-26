package com.tcc.serveme.api.order.dto;

import jakarta.validation.constraints.*;

public record NewOrderItemRequest(
        @NotNull(message = "Produto é obrigatório")
        Long productId,

        @NotNull(message = "Quantidade é obrigatória")
        @Positive(message = "Quantidade deve ser maior que zero")
        Integer quantity,

        String notes
) {}