package com.tcc.serveme.api.model;

public class OrderItem {
    private Long id;
    private Product product;
    private Integer quantity;
    private String notes;
    private Boolean canceled;


    // Construtor para novos itens
    public OrderItem(Product product, Integer quantity, String notes) {
        this.product = product;
        this.quantity = quantity;
        this.notes = notes;
        this.canceled = false;
    }

    // Construtor para reconstruir obj a partir do banco de dados
    public OrderItem(Long id, Product product, Integer quantity, String notes, Boolean canceled) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.notes = notes;
        this.canceled = canceled;
    }


    public Long getId() {
        return this.id;
    }

    public Product getProduct() {
        return this.product;
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