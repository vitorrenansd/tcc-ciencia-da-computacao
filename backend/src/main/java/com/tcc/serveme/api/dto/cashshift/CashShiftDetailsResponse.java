package com.tcc.serveme.api.dto.cashshift;

import java.time.LocalDateTime;

public record CashShiftDetailsResponse(
        Long id,
        LocalDateTime openedAt,
        LocalDateTime closedAt,
        String status
) {}