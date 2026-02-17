package com.tcc.serveme.api.controller;

import com.tcc.serveme.api.dto.category.NewProductCategoryRequest;
import com.tcc.serveme.api.service.ProductCategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product-category")
@CrossOrigin("*")
public class ProductCategoryController {
    private final ProductCategoryService productCategoryService;

    @Autowired
    public ProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @PostMapping("/new")
    public void newProductCategory(@RequestBody NewProductCategoryRequest request) {
        productCategoryService.createProductCategory(request);
    }
}