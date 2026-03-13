package com.tcc.serveme.api.cashshift.mapper;

import com.tcc.serveme.api.cashshift.dto.CashShiftDetailsResponse;
import com.tcc.serveme.api.cashshift.entity.CashShift;

public class CashShiftMapper {

    public static CashShiftDetailsResponse toDetailsResponse(CashShift session) {
        return new CashShiftDetailsResponse(
                session.getId(),
                session.getOpenedAt(),
                session.getClosedAt(),
                session.getStatus().name()
        );
    }
}