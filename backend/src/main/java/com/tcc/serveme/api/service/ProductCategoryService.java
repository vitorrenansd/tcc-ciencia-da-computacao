package com.tcc.serveme.api.service;

import com.tcc.serveme.api.model.ProductCategory;
import com.tcc.serveme.api.repository.ProductCategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductCategoryService {
    private final ProductCategoryRepository productCategoryRepo;

    @Autowired
    public ProductCategoryService(ProductCategoryRepository productCategoryRepo) {
        this.productCategoryRepo = productCategoryRepo;
    }
}