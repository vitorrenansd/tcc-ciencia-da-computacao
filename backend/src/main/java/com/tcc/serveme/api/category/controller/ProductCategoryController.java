package com.tcc.serveme.api.category.controller;

import com.tcc.serveme.api.category.dto.NewProductCategoryRequest;
import com.tcc.serveme.api.category.dto.ProductCategoryResponse;
import com.tcc.serveme.api.category.dto.UpdateProductCategoryRequest;
import com.tcc.serveme.api.category.service.ProductCategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-category")
@CrossOrigin("*")
public class ProductCategoryController {
    private final ProductCategoryService productCategoryService;

    @Autowired
    public ProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }


    @PostMapping
    public ResponseEntity<?> create(@RequestBody NewProductCategoryRequest request) {
        productCategoryService.createProductCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<ProductCategoryResponse>> getProductCategories(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false, defaultValue = "false") boolean activeOnly) {

        if (keyword != null && !keyword.isBlank()) {
            // EXEMPLO: api/product-category?keyword=BEBIDAS
            return ResponseEntity.ok(productCategoryService.getCategoriesByName(keyword));
        }
        if (activeOnly) {
            // EXEMPLO: api/product-category?activeOnly=true
            return ResponseEntity.ok(productCategoryService.getAllActiveCategories());
        }
        // EXEMPLO: api/product-category
        return ResponseEntity.ok(productCategoryService.getAllCategories());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable Long id,
            @RequestBody UpdateProductCategoryRequest request) {

        productCategoryService.updateProductCategory(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productCategoryService.deleteProductCategory(id);
        return ResponseEntity.noContent().build();
    }
}