package com.tcc.serveme.api.category.repository;

import com.tcc.serveme.api.category.entity.ProductCategory;

import java.util.List;
import java.util.Optional;

public interface ProductCategoryRepository {
    Optional<ProductCategory> findById(Long id);
    List<ProductCategory> findAll();
    int save(ProductCategory productCategory);
    int update(ProductCategory productCategory);
    int softDelete(Long id);

    List<ProductCategory> findAllActive();
    List<ProductCategory> findAllByName(String keyword);
}