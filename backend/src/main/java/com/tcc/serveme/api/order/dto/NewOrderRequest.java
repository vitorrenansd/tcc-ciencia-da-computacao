package com.tcc.serveme.api.order.dto;

import java.util.List;

public record NewOrderRequest(
    Integer tableNumber,
    String customerName,
    List<NewOrderItemRequest> items
) {}