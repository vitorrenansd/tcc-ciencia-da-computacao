package com.tcc.serveme.api.dto.order;

import java.time.LocalDateTime;

public record PendingOrdersResponse(
    Long id,
    Integer tableNumber,
    String customerName,
    LocalDateTime createdAt,
    String status
) {}