package com.tcc.serveme.api.restaurant.repository;

import com.tcc.serveme.api.restaurant.entity.RestaurantConfig;

import java.util.Optional;

public interface RestaurantConfigRepository {
    int update(RestaurantConfig restaurantConfig);
    void updateIconFilename(Long id, String iconUrl);

    Optional<RestaurantConfig> find(Long id);
}