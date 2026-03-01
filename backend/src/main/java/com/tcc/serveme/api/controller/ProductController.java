package com.tcc.serveme.api.controller;

import com.tcc.serveme.api.dto.product.NewProductRequest;
import com.tcc.serveme.api.dto.product.ProductDetailsResponse;
import com.tcc.serveme.api.dto.product.ProductSummaryResponse;
import com.tcc.serveme.api.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            productService.createProduct(request);
            return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<ProductSummaryResponse>> getAllActive(@RequestParam(required = false) Long categoryId) {
        if (categoryId != null) {
            // EXEMPLO: /api/product?categoryId=1
            return ResponseEntity.ok(productService.getActiveProductsByCategory(categoryId));
        }
        // EXEMPLO: /api/product
        return ResponseEntity.ok(productService.getAllActiveProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailsResponse> getById(@PathVariable Long id) {
        ProductDetailsResponse response = productService.getDetailsById(id);
        return ResponseEntity.ok(response);
    }
}