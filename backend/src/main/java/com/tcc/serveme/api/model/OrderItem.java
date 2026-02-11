package com.tcc.serveme.api.model;

public class OrderItem {
    private Long id;
    private Long orderId;
    private Long productId;
    private Integer quantity;
    private String notes;
    private Boolean canceled;


    // Construtor com apenas o necessário pelo input do front (dto)
    public OrderItem(Long productId, Integer quantity, String notes) {
        this.productId = productId;
        this.quantity = quantity;
        this.notes = notes;
    }

    // Usado na inclusão de itens no banco de dados (não tem id ainda)
    public OrderItem(Long orderId, Long productId, Integer quantity, String notes, Boolean canceled) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.notes = notes;
        this.canceled = canceled;
    }

    // Usado para reconstrução a partir do banco de dados (construtor completo)
    public OrderItem(Long id, Long orderId, Long productId, Integer quantity, String notes, Boolean canceled) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.notes = notes;
        this.canceled = canceled;
    }


    public Long getId() {
        return this.id;
    }

    public Long getOrderId() {
        return this.orderId;
    }
    
    public Long getProductId() {
        return this.productId;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public String getNotes() {
        return this.notes;
    }

    public Boolean isCanceled() {
        return this.canceled;
    }

    
    public void markAsCanceled() {
        this.canceled = true;
    }
}