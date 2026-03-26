package com.tcc.serveme.api.category.mapper;

import com.tcc.serveme.api.category.dto.NewProductCategoryRequest;
import com.tcc.serveme.api.category.dto.ProductCategoryDetailsResponse;
import com.tcc.serveme.api.category.dto.ProductCategorySummaryResponse;
import com.tcc.serveme.api.category.entity.ProductCategory;

public class ProductCategoryMapper {

    public static ProductCategory toModel(NewProductCategoryRequest dto) {
        return new ProductCategory(
                dto.name(),
                dto.inactive()
        );
    }

    public static ProductCategoryDetailsResponse toDetailsResponse(ProductCategory productCategory) {
        return new ProductCategoryDetailsResponse(
                productCategory.getId(),
                productCategory.getName(),
                productCategory.isActive()
        );
    }

    public static ProductCategorySummaryResponse toSummaryResponse(ProductCategory productCategory) {
        return new ProductCategorySummaryResponse(
                productCategory.getId(),
                productCategory.getName()
        );
    }

}