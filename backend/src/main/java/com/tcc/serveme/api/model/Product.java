package com.tcc.serveme.api.model;

import java.math.BigDecimal;

public class Product {
    private Long id;
    private ProductCategory category;
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean inactive;


    // Construtor para novos produtos
    public Product(ProductCategory category, String name, String description, BigDecimal price) {
        this.category = category;
        this.name = name;
        this.description = description;
        this.price = price;
        this.inactive = false;
    }

    // Construtor para reconstruir obj a partir do banco de dados
    public Product(Long id, ProductCategory category, String name, String description, BigDecimal price, boolean inactive) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.description = description;
        this.price = price;
        this.inactive = inactive;
    }


    public Long getId() {
        return this.id;
    }

    public ProductCategory getCategory() {
        return this.category;
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