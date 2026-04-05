package com.tcc.serveme.api.restaurant.entity;

public class RestaurantConfig {
    private Long id;
    private String name;
    private String iconFilename;


    // Usado para reconstrução a partir do banco de dados (construtor completo)
    public RestaurantConfig(Long id, String name, String iconFilename) {
        this.id = id;
        this.name = name;
        this.iconFilename = iconFilename;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIconFilename() {
        return iconFilename;
    }
}