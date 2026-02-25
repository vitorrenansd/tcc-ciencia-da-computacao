package com.tcc.serveme.api.controller;

import com.tcc.serveme.api.dto.product.NewProductRequest;
import com.tcc.serveme.api.dto.product.ProductDetailsResponse;
import com.tcc.serveme.api.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
@CrossOrigin("*")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody NewProductRequest request) {
        try {
            productService.createProduct(request);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailsResponse> getById(@PathVariable Long id) {
        ProductDetailsResponse response = productService.findDetailsById(id);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }
}