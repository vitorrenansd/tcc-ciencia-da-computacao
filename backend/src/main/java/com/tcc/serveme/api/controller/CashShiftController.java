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
}