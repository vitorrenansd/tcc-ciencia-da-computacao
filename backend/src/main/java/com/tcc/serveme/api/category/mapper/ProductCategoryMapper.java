package com.tcc.serveme.api.category.mapper;

import com.tcc.serveme.api.category.dto.NewProductCategoryRequest;
import com.tcc.serveme.api.category.dto.ProductCategoryResponse;
import com.tcc.serveme.api.category.entity.ProductCategory;

public class ProductCategoryMapper {

    public static ProductCategory toModel(NewProductCategoryRequest dto) {
        return new ProductCategory(
                dto.name(),
                dto.inactive()
        );
    }

    public static ProductCategoryResponse toResponse(ProductCategory productCategory) {
        return new ProductCategoryResponse(
                productCategory.getId(),
                productCategory.getName()
        );
    }
}