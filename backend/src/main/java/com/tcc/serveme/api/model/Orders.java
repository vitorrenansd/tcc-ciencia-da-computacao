package com.tcc.serveme.api.model;

import com.tcc.serveme.api.model.enums.OrderStatus;

import java.util.List;
import java.time.LocalDateTime;

public class Orders {
    private Long id;
    private Integer tableNumber;
    private String customerName;
    private OrderStatus status;
    private LocalDateTime createdAt;


    // Usado na inclusão de pedidos no banco de dados
    public Orders(Integer tableNumber, String customerName, List<OrderItem> items) {
        this.tableNumber = tableNumber;
        this.customerName = customerName;
        this.status = OrderStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }

    // Construtor para reconstruir obj a partir do banco de dados
    public Orders(Long id, Integer tableNumber, String customerName, List<OrderItem> items, OrderStatus status, LocalDateTime createdAt) {
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