package com.tcc.serveme.api.category.entity;

public class ProductCategory {
    private Long id;
    private String name;
    private Boolean active;


    // Usado na inclusão de categoria nova no banco de dados (não tem id ainda)
    public ProductCategory(String name, Boolean active) {
        this.name = name;
        this.active = active;
    }

    // Usado para reconstrução a partir do banco de dados (construtor completo)
    public ProductCategory(Long id, String name, Boolean active) {
        this.id = id;
        this.name = name;
        this.active = active;
    }

    
    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Boolean isActive() {
        return this.active;
    }


    public void markAsInactive() {
        this.active = Boolean.FALSE;
    }
}