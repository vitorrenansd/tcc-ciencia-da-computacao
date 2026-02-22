package com.tcc.serveme.api.mapper;

import com.tcc.serveme.api.dto.category.NewProductCategoryRequest;
import com.tcc.serveme.api.dto.category.ProductCategoryResponse;
import com.tcc.serveme.api.model.ProductCategory;

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