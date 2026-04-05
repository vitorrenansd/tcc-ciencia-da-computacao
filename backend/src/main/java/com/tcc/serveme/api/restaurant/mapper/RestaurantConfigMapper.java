package com.tcc.serveme.api.restaurant.mapper;

import com.tcc.serveme.api.restaurant.dto.RestaurantConfigResponse;
import com.tcc.serveme.api.restaurant.entity.RestaurantConfig;

public class RestaurantConfigMapper {

    public static RestaurantConfigResponse toResponse(RestaurantConfig config, String baseUrl) {
        String iconUrl = config.getIconFilename() != null
                ? baseUrl + "config/" + config.getIconFilename()
                : null;
        return new RestaurantConfigResponse(
                config.getName(),
                iconUrl
        );
    }
}