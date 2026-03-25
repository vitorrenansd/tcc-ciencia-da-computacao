package com.tcc.serveme.api.product.entity;

import java.math.BigDecimal;

public class Product {
    private Long id;
    private Long categoryId;
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean active;
    private Boolean available;


    // Usado na inclusão de produto novo no banco de dados (não tem id ainda)
    public Product(Long categoryId, String name, String description, BigDecimal price, Boolean active, Boolean available) {
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.active = active;
        this.available = available;
    }

    // Usado para reconstrução a partir do banco de dados (construtor completo)
    public Product(Long id, Long categoryId, String name, String description, BigDecimal price, Boolean active, Boolean available) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.active = active;
        this.available = available;
    }


    public Long getId() {
        return this.id;
    }

    public Long getCategoryId() {
        return this.categoryId;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public Boolean isActive() {
        return this.active;
    }

    public Boolean isAvailable() {
        return this.available;
    }


    public void markAsInactive() {
        this.active = Boolean.FALSE;
    }
}