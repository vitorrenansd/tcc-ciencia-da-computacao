package com.tcc.serveme.api.cashshift.repository;

import com.tcc.serveme.api.cashshift.entity.CashShift;

import java.time.LocalDateTime;
import java.util.Optional;

public interface CashShiftRepository {
    Optional<CashShift> findById(Long id);
    Long openShift(LocalDateTime currentDateTime);
    void closeShift(Long id, LocalDateTime currentDateTime);

    Optional<CashShift> findOpenShift();
}