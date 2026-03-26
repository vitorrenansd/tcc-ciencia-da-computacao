package com.tcc.serveme.api.order.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

public record NewOrderRequest(
        @NotNull(message = "Número da mesa é obrigatório")
        Integer tableNumber,

        @NotBlank(message = "Nome do cliente é obrigatório")
        String customerName,

        @NotEmpty(message = "Pedido deve conter ao menos um item")
        @Valid
        List<NewOrderItemRequest> items
) {}