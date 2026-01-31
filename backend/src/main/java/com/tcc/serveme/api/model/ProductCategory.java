package com.tcc.serveme.api.model;

public class ProductCategory {
    private Long id;
    private String name;
    private Boolean inactive;


    // Construtor para novas categorias
    public ProductCategory(String name) {
        this.name = name;
        this.inactive = false;
    }

    // Construtor para reconstruir obj a partir do banco de dados
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