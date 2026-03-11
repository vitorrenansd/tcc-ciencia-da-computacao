package com.tcc.serveme.api.service;

import com.tcc.serveme.api.dto.cashshift.CashShiftDetailsResponse;
import com.tcc.serveme.api.mapper.CashShiftMapper;
import com.tcc.serveme.api.entity.CashShift;
import com.tcc.serveme.api.repository.CashShiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe turno aberto.");
        }
        return cashShiftRepo.openShift(LocalDateTime.now());
    }

    // Fechamento do caixa atual
    @Transactional
    public void closeCashShift() {
        CashShift shift = cashShiftRepo.findOpenShift()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhuma sessão aberta no momento."));

        cashShiftRepo.closeShift(shift.getId(), LocalDateTime.now());
    }

    // Retorna os detalhes de um caixa pelo ID
    public CashShiftDetailsResponse getDetailsById(Long id) {
        return cashShiftRepo.findById(id)
                .map(CashShiftMapper::toDetailsResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sessão de caixa não encontrada."));
    }

    // Retorna o caixa aberto neste momento
    public CashShiftDetailsResponse getOpenShift() {
        return cashShiftRepo.findOpenShift()
                .map(CashShiftMapper::toDetailsResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhuma sessão aberta no momento."));
    }
}