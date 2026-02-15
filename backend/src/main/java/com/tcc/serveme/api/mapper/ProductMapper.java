package com.tcc.serveme.api.mapper;

import com.tcc.serveme.api.dto.product.NewProductRequest;
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

}