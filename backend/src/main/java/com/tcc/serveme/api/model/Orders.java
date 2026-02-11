package com.tcc.serveme.api.model;

import com.tcc.serveme.api.model.enums.OrderStatus;

import java.time.LocalDateTime;

public class Orders {
    private Long id;
    private Integer tableNumber;
    private String customerName;
    private OrderStatus status;
    private LocalDateTime createdAt;


    // Construtor com apenas o necessário pelo input do front (dto)
    public Orders(Integer tableNumber, String customerName) {
        this.tableNumber = tableNumber;
        this.customerName = customerName;
    }
    }

    // Usado para reconstrução a partir do banco de dados (construtor completo)
    public Orders(Long id, Integer tableNumber, String customerName, OrderStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.tableNumber = tableNumber;
        this.customerName = customerName;
        this.status = status;
        this.createdAt = createdAt;
    }


    public Long getId() {
        return this.id;
    }

    public Integer getTableNumber() {
        return this.tableNumber;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public OrderStatus getStatus() {
        return this.status;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }


    public void start() {
        this.status = OrderStatus.IN_PROGRESS;
    }
    public void complete() {
        this.status = OrderStatus.COMPLETED;
    }
    public void cancel() {
        this.status = OrderStatus.CANCELED;
    }
}