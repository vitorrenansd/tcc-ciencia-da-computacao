package com.tcc.serveme.api.restaurant.controller;

import com.tcc.serveme.api.restaurant.dto.RestaurantConfigResponse;
import com.tcc.serveme.api.restaurant.dto.UpdateRestaurantConfigRequest;
import com.tcc.serveme.api.restaurant.service.RestaurantConfigService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/config")
@CrossOrigin("*")
public class RestaurantConfigController {
    private final RestaurantConfigService restaurantConfigService;

    @Autowired
    public RestaurantConfigController(RestaurantConfigService restaurantConfigService) {
        this.restaurantConfigService = restaurantConfigService;
    }


    @GetMapping
    public ResponseEntity<RestaurantConfigResponse> getConfig() {
        return ResponseEntity.ok(restaurantConfigService.getConfig());
    }

    @PutMapping
    public ResponseEntity<Void> updateConfig(
            @Valid @RequestBody UpdateRestaurantConfigRequest request) {

        restaurantConfigService.updateConfig(request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/icon")
    public ResponseEntity<Void> uploadIcon(
            @RequestParam("file") MultipartFile file) {

        restaurantConfigService.updateIcon(file);
        return ResponseEntity.noContent().build();
    }
}