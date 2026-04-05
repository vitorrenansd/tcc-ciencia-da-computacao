package com.tcc.serveme.api.cashshift.controller;

import com.tcc.serveme.api.cashshift.dto.CashShiftDetailsResponse;
import com.tcc.serveme.api.cashshift.service.CashShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cash-shift")
@CrossOrigin("*")
public class CashShiftController {
    private final CashShiftService cashShiftService;

    @Autowired
    public CashShiftController(CashShiftService cashShiftService) {
        this.cashShiftService = cashShiftService;
    }


    @PostMapping("/open")
    public ResponseEntity<?> open() {
        Long id = cashShiftService.createCashShift();
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @PutMapping("/close")
    public ResponseEntity<Void> close() {
        cashShiftService.closeCashShift();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CashShiftDetailsResponse> getById(@PathVariable Long id) {
        CashShiftDetailsResponse response = cashShiftService.getDetailsById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/active")
    public ResponseEntity<CashShiftDetailsResponse> getOpenShift() {
        CashShiftDetailsResponse response = cashShiftService.getOpenShift();
        return ResponseEntity.ok(response);
    }
}