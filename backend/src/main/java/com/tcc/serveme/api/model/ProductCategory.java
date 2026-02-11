package com.tcc.serveme.api.model;

public class ProductCategory {
    private Long id;
    private String name;
    private Boolean inactive;


    // Usado na inclusão de categoria nova no banco de dados (não tem id ainda)
    public ProductCategory(String name, Boolean inactive) {
        this.name = name;
        this.inactive = inactive;
    }

    // Usado para reconstrução a partir do banco de dados (construtor completo)
    public ProductCategory(Long id, String name, Boolean inactive) {
        this.id = id;
        this.name = name;
        this.inactive = inactive;
    }

    
    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Boolean isInactive() {
        return this.inactive;
    }
}