package com.tcc.serveme.api.restaurant.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateRestaurantConfigRequest(
        @NotBlank
        @Size(max = 80)
        String name
) {}