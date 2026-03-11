package com.tcc.serveme.api.repository;

import com.tcc.serveme.api.entity.ProductCategory;

import java.util.List;
import java.util.Optional;

public interface ProductCategoryRepository {
    Optional<ProductCategory> findById(Long id);
    List<ProductCategory> findAll();
    int save(ProductCategory productCategory);
    int update(ProductCategory productCategory);
    int softDelete(Long id);

    List<ProductCategory> findAllActive();
    List<ProductCategory> findByNameActive(String keyword);
}