package com.tcc.serveme.api.product.controller;

import com.tcc.serveme.api.product.dto.NewProductRequest;
import com.tcc.serveme.api.product.dto.ProductDetailsResponse;
import com.tcc.serveme.api.product.dto.ProductSummaryResponse;
import com.tcc.serveme.api.product.dto.UpdateProductRequest;
import com.tcc.serveme.api.product.service.ProductService;

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
    public ResponseEntity<List<ProductSummaryResponse>> getProducts(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false, defaultValue = "false") boolean availableOnly) {

        if (keyword != null && !keyword.isBlank()) {
            // EXEMPLO: /api/product?keyword=pastel
            return ResponseEntity.ok(productService.getProductsByName(keyword));
        }
        if (categoryId != null) {
            if (availableOnly) {
                // EXEMPLO: /api/product?categoryId=1&availableOnly=true
                return ResponseEntity.ok(productService.getAvailableProductsByCategory(categoryId));
            }
            // EXEMPLO: /api/product?categoryId=1
            return ResponseEntity.ok(productService.getProductsByCategory(categoryId));
        }
        // EXEMPLO: /api/product
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailsResponse> getById(@PathVariable Long id) {
        ProductDetailsResponse response = productService.getDetailsById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable Long id,
            @RequestBody UpdateProductRequest request) {

        productService.updateProduct(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}