package com.tcc.serveme.api.product.repository;

import com.tcc.serveme.api.product.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Optional<Product> findById(Long id);
    List<Product> findAll();
    int save(Product product);
    int update(Product product);
    void updateImageFilename(Long id, String filename);
    int softDelete(Long id);

    Optional<Product> findByIdActive(Long id);
    List<Product> findAllByName(String keyword);
    List<Product> findAllByCategory(Long categoryId);
    List<Product> findAllAvailableByCategory(Long categoryId);
}