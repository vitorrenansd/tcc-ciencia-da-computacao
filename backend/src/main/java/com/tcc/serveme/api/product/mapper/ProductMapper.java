package com.tcc.serveme.api.product.mapper;

import com.tcc.serveme.api.product.dto.NewProductRequest;
import com.tcc.serveme.api.product.dto.ProductDetailsResponse;
import com.tcc.serveme.api.product.dto.ProductSummaryResponse;
import com.tcc.serveme.api.product.entity.Product;

public class ProductMapper {
    public static Product toModel(NewProductRequest dto) {
        return new Product(
                dto.categoryId(),
                dto.name(),
                dto.description(),
                dto.price(),
                dto.inactive()
        );
    }

    public static ProductDetailsResponse toDetailsResponse(Product product, String categoryName) {
        return new ProductDetailsResponse(
                product.getId(),
                product.getCategoryId(),
                categoryName,
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.isInactive()
        );
    }


    public static ProductSummaryResponse toSummaryResponse(Product product) {
        return new ProductSummaryResponse(
                product.getId(),
                product.getName(),
                product.getPrice()
        );
    }

}