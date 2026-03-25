package com.tcc.serveme.api.order.dto;

import com.tcc.serveme.api.order.entity.OrderStatus;

public record UpdateOrderStatusRequest(
        OrderStatus status
) {}