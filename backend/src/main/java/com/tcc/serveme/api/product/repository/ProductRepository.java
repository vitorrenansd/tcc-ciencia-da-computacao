package com.tcc.serveme.api.product.repository;

import com.tcc.serveme.api.product.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Optional<Product> findById(Long id);
    List<Product> findAll();
    int save(Product product);
    int update(Product product);
    int softDelete(Long id);

    Optional<Product> findByIdActive(Long id);
    List<Product> findAllActive();
    List<Product> findByName(String keyword);
    List<Product> findAllActiveByCategory(Long categoryId);
}