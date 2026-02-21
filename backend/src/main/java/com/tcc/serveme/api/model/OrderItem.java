package com.tcc.serveme.api.model;

import java.math.BigDecimal;

public class OrderItem {
    private Long id;
    private Long orderId;
    private Long productId;
    private String productName;
    private BigDecimal productPrice;
    private Integer quantity;
    private String notes;
    private Boolean canceled;


    // Usado na inclusão de itens no banco de dados (não tem id ainda)
    public OrderItem(Long orderId, Long productId, String productName, BigDecimal productPrice, Integer quantity, String notes) {
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
        this.notes = notes;
        this.canceled = Boolean.FALSE;
    }

    // Usado para reconstrução a partir do banco de dados (construtor completo)
    public OrderItem(Long id, Long orderId, Long productId, String productName, BigDecimal productPrice, Integer quantity, String notes, Boolean canceled) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
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

    public String getProductName() {
        return this.productName;
    }

    public BigDecimal getProductPrice() {
        return this.productPrice;
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