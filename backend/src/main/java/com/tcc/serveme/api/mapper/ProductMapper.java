package com.tcc.serveme.api.mapper;

import com.tcc.serveme.api.dto.product.NewProductRequest;
import com.tcc.serveme.api.dto.product.ProductDetailsResponse;
import com.tcc.serveme.api.dto.product.ProductSummaryResponse;
import com.tcc.serveme.api.model.Product;

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

    public static ProductDetailsResponse toDetailsResponse(Product product) {
        return new ProductDetailsResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice()
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