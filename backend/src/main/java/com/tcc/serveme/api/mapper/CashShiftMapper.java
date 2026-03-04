package com.tcc.serveme.api.mapper;

import com.tcc.serveme.api.dto.cashshift.CashShiftDetailsResponse;
import com.tcc.serveme.api.model.CashShift;

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