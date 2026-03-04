package com.tcc.serveme.api.controller;

import com.tcc.serveme.api.dto.cashshift.CashShiftDetailsResponse;
import com.tcc.serveme.api.service.CashShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cash-session")
public class CashShiftController {
    private final CashShiftService cashShiftService;

    @Autowired
    public CashShiftController(CashShiftService cashShiftService) {
        this.cashShiftService = cashShiftService;
    }


    @PostMapping
    public ResponseEntity<?> open() {
        Long id = cashShiftService.createCashSession();
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CashShiftDetailsResponse> getById(@PathVariable Long id) {
        CashShiftDetailsResponse response = cashShiftService.getDetailsById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/close")
    public ResponseEntity<Void> close(@PathVariable Long id) {
        cashShiftService.closeSession(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/open")
    public ResponseEntity<CashShiftDetailsResponse> getOpenSession() {
        CashShiftDetailsResponse response = cashShiftService.getOpenSession();
        return ResponseEntity.ok(response);
    }
}