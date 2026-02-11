package com.tcc.serveme.api.model;

import java.math.BigDecimal;

public class Product {
    private Long id;
    private Long categoryId;
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean inactive;


    // Usado na inclusão de produto novo no banco de dados (não tem id ainda)
    public Product(Long categoryId, String name, String description, BigDecimal price, Boolean inactive) {
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.inactive = inactive;
    }

    // Usado para reconstrução a partir do banco de dados (construtor completo)
    public Product(Long id, Long categoryId, String name, String description, BigDecimal price, boolean inactive) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.inactive = inactive;
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

    public Boolean isInactive() {
        return this.inactive;
    }
}