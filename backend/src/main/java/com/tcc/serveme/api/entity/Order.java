package com.tcc.serveme.api.entity;

import com.tcc.serveme.api.entity.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Order {
    private Long id;
    private Long cashShiftId;
    private Integer tableNumber;
    private String customerName;
    private BigDecimal totalPrice;
    private OrderStatus status;
    private LocalDateTime createdAt;


    // Construtor para adicao de pedido na DB
    public Order(Long cashShiftId, Integer tableNumber, String customerName, BigDecimal totalPrice) {
        this.cashShiftId = cashShiftId;
        this.tableNumber = tableNumber;
        this.customerName = customerName;
        this.totalPrice = totalPrice;
        this.status = OrderStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }

    // Usado para reconstrução a partir do banco de dados (construtor completo)
    public Order(Long id, Long cashShiftId, Integer tableNumber, String customerName, BigDecimal totalPrice, OrderStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.cashShiftId = cashShiftId;
        this.tableNumber = tableNumber;
        this.customerName = customerName;
        this.totalPrice = totalPrice;
        this.status = status;
        this.createdAt = createdAt;
    }


    public Long getId() {
        return this.id;
    }

    public Long getCashShiftId() {
        return this.cashShiftId;
    }

    public Integer getTableNumber() {
        return this.tableNumber;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public BigDecimal getTotalPrice() {
        return this.totalPrice;
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
    public void serve() {
        this.status = OrderStatus.SERVED;
    }
    public void pay() {
        this.status = OrderStatus.PAID;
    }
    public void cancel() {
        this.status = OrderStatus.CANCELED;
    }
}