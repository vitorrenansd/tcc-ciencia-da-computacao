package com.tcc.serveme.api.dto.order;

import java.util.List;

public record NewOrderRequest(
    Integer tableNumber,
    String customerName,
    List<OrderItemRequest> items
) {}