package com.tcc.serveme.api.order.entity;

public enum OrderStatus {
    PENDING,
    IN_PROGRESS,
    SERVED,
    PAID,
    CANCELED;

    public boolean isTerminal() {
        return this == PAID || this == CANCELED;
    }
}