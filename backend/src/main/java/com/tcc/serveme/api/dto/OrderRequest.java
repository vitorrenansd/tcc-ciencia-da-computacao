package com.tcc.serveme.api.dto;

import java.util.List;

public record OrderRequest(
    Integer tableNumber,
    String waiter,
    List<OrderItemRequest> items
) {}