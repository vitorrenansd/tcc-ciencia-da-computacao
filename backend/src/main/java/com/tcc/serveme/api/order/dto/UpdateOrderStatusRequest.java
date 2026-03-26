package com.tcc.serveme.api.order.dto;

import com.tcc.serveme.api.order.entity.OrderStatus;

import jakarta.validation.constraints.*;

public record UpdateOrderStatusRequest(
        @NotNull(message = "Status é obrigatório")
        OrderStatus status
) {}