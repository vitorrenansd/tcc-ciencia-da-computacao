package com.tcc.serveme.api.cashshift.service;

import com.tcc.serveme.api.cashshift.dto.CashShiftDetailsResponse;
import com.tcc.serveme.api.cashshift.mapper.CashShiftMapper;
import com.tcc.serveme.api.cashshift.entity.CashShift;
import com.tcc.serveme.api.cashshift.repository.CashShiftRepository;
import com.tcc.serveme.api.exception.ConflictException;
import com.tcc.serveme.api.exception.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CashShiftService {
    private final CashShiftRepository cashShiftRepo;

    @Autowired
    public CashShiftService(CashShiftRepository cashShiftRepo) {
        this.cashShiftRepo = cashShiftRepo;
    }


    // Abertura de caixa novo
    @Transactional
    public Long createCashShift() {
        if (cashShiftRepo.findOpenShift().isPresent()) {
            throw new ConflictException("Já existe turno aberto.");
        }
        return cashShiftRepo.openShift(LocalDateTime.now());
    }

    // Fechamento do caixa atual
    @Transactional
    public void closeCashShift() {
        CashShift shift = cashShiftRepo.findOpenShift()
                .orElseThrow(() -> new NotFoundException("Nenhum turno aberto no momento."));

        cashShiftRepo.closeShift(shift.getId(), LocalDateTime.now());
    }

    // Retorna os detalhes de um caixa pelo ID
    public CashShiftDetailsResponse getDetailsById(Long id) {
        return cashShiftRepo.findById(id)
                .map(CashShiftMapper::toDetailsResponse)
                .orElseThrow(() -> new NotFoundException("Turno de caixa não encontrado. ID: " + id));
    }

    // Retorna o caixa aberto neste momento
    public CashShiftDetailsResponse getOpenShift() {
        return cashShiftRepo.findOpenShift()
                .map(CashShiftMapper::toDetailsResponse)
                .orElseThrow(() -> new NotFoundException("Nenhum turno aberto no momento."));
    }
}