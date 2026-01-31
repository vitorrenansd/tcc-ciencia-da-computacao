package com.tcc.serveme.api.model;

import com.tcc.serveme.api.model.enums.OrderStatus;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private Long id;
    private Integer tableNumber;
    private String waiter;
    private List<OrderItem> items = new ArrayList<>();
    private OrderStatus status;


    // Construtor para novos itens
    public Order(Integer tableNumber, String waiter, List<OrderItem> items) {
        this.tableNumber = tableNumber;
        this.waiter = waiter;
        this.items = items;
        this.status = OrderStatus.PENDING;
    }

    // Construtor para reconstruir obj a partir do banco de dados
    public Order(Long id, Integer tableNumber, String waiter, List<OrderItem> items, OrderStatus status) {
        this.id = id;
        this.tableNumber = tableNumber;
        this.waiter = waiter;
        this.items = items;
        this.status = status;
    }


    public Long getId() {
        return this.id;
    }

    public Integer getTableNumber() {
        return this.tableNumber;
    }

    public String getWaiter() {
        return this.waiter;
    }

    public List<OrderItem> getItems() {
        return List.copyOf(this.items);
    }

    public OrderStatus getStatus() {
        return this.status;
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