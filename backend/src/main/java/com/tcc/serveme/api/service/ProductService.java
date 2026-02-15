package com.tcc.serveme.api.service;

import com.tcc.serveme.api.dto.product.NewProductRequest;
import com.tcc.serveme.api.mapper.ProductMapper;
import com.tcc.serveme.api.model.Product;
import com.tcc.serveme.api.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {
    private final ProductRepository productRepo;

    @Autowired
    public ProductService(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    @Transactional
    public void createProduct(NewProductRequest request) {
        Product product = ProductMapper.toModel(request);
        productRepo.save(product);
    }
}