package com.tcc.serveme.api.entity;

import com.tcc.serveme.api.entity.enums.CashShiftStatus;

import java.time.LocalDateTime;

public class CashShift {
    private Long id;
    private LocalDateTime openedAt;
    private LocalDateTime closedAt;
    private CashShiftStatus status;


    // Usado para reconstrução a partir do banco de dados (construtor completo)
    public CashShift(Long id, LocalDateTime openedAt, LocalDateTime closedAt, CashShiftStatus status) {
        this.id = id;
        this.openedAt = openedAt;
        this.closedAt = closedAt;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getOpenedAt() {
        return openedAt;
    }

    public LocalDateTime getClosedAt() {
        return closedAt;
    }

    public CashShiftStatus getStatus() {
        return status;
    }


    public void open() {
        this.status = CashShiftStatus.OPEN;
    }

    public void close() {
        this.status = CashShiftStatus.CLOSED;
    }
}