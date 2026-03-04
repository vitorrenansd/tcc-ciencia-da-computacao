package com.tcc.serveme.api.service;

import com.tcc.serveme.api.dto.cashshift.CashShiftDetailsResponse;
import com.tcc.serveme.api.mapper.CashShiftMapper;
import com.tcc.serveme.api.model.CashShift;
import com.tcc.serveme.api.model.enums.CashShiftStatus;
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


    @Transactional
    public Long createCashSession() {
        if (cashShiftRepo.findOpenShift().isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe turno aberto.");
        }
        return cashShiftRepo.openShift(LocalDateTime.now());
    }

    @Transactional
    public void closeSession(Long id) {
        CashShift session = cashShiftRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sessão de caixa não encontrada. ID: " + id));

        if (session.getStatus() == CashShiftStatus.CLOSED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sessão já está fechada.");
        }

        cashShiftRepo.closeShift(id, LocalDateTime.now());
    }

    public CashShiftDetailsResponse getDetailsById(Long id) {
        return cashShiftRepo.findById(id)
                .map(CashShiftMapper::toDetailsResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sessão de caixa não encontrada. ID: " + id));
    }

    public CashShiftDetailsResponse getOpenSession() {
        return cashShiftRepo.findOpenShift()
                .map(CashShiftMapper::toDetailsResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhuma sessão aberta no momento."));
    }
}