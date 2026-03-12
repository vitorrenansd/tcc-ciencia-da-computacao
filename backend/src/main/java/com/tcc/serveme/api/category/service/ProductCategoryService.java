package com.tcc.serveme.api.category.service;

import com.tcc.serveme.api.category.dto.NewProductCategoryRequest;
import com.tcc.serveme.api.category.dto.ProductCategoryResponse;
import com.tcc.serveme.api.category.mapper.ProductCategoryMapper;
import com.tcc.serveme.api.category.entity.ProductCategory;
import com.tcc.serveme.api.category.repository.ProductCategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProductCategoryService {
    private final ProductCategoryRepository productCategoryRepo;

    @Autowired
    public ProductCategoryService(ProductCategoryRepository productCategoryRepo) {
        this.productCategoryRepo = productCategoryRepo;
    }


    // Cria categoria de produto nova no banco
    @Transactional
    public void createProductCategory(NewProductCategoryRequest request) {
        if (request.name() == null || request.name().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome da categoria é obrigatório");
        }
        ProductCategory productCategory = ProductCategoryMapper.toModel(request);
        productCategoryRepo.save(productCategory);
    }

    // Retorna um List com todas as categorias marcadas inactive = FALSE
    public List<ProductCategoryResponse> getActiveCategories() {
        return productCategoryRepo.findAllActive()
                .stream()
                .map(ProductCategoryMapper::toResponse) // Mapeia o retorno do repo para um DTO valido
                .toList();
    }
}