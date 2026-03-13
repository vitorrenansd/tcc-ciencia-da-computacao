package com.tcc.serveme.api.cashshift.dto;

import java.time.LocalDateTime;

public record CashShiftDetailsResponse(
        Long id,
        LocalDateTime openedAt,
        LocalDateTime closedAt,
        String status
) {}