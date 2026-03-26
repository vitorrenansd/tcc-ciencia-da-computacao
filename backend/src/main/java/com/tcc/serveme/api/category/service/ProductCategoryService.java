package com.tcc.serveme.api.category.service;

import com.tcc.serveme.api.category.dto.NewProductCategoryRequest;
import com.tcc.serveme.api.category.dto.ProductCategoryDetailsResponse;
import com.tcc.serveme.api.category.dto.ProductCategorySummaryResponse;
import com.tcc.serveme.api.category.dto.UpdateProductCategoryRequest;
import com.tcc.serveme.api.category.mapper.ProductCategoryMapper;
import com.tcc.serveme.api.category.entity.ProductCategory;
import com.tcc.serveme.api.category.repository.ProductCategoryRepository;
import com.tcc.serveme.api.exception.BadRequestException;
import com.tcc.serveme.api.exception.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            throw new BadRequestException("Nome da categoria é obrigatório");
        }
        ProductCategory productCategory = ProductCategoryMapper.toModel(request);
        productCategoryRepo.save(productCategory);
    }

    // Retorna os detalhes de uma categoria pelo ID
    public ProductCategoryDetailsResponse getById(Long id) {
        ProductCategory category = productCategoryRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada. ID: " + id));
        return ProductCategoryMapper.toDetailsResponse(category);
    }

    // Atualiza os dados de uma categoria existente
    @Transactional
    public void updateProductCategory(Long id, UpdateProductCategoryRequest request) {
        ProductCategory category = productCategoryRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada. ID: " + id));

        if (request.name() == null || request.name().isBlank()) {
            throw new BadRequestException("Nome da categoria é obrigatório");
        }

        ProductCategory updated = new ProductCategory(
                category.getId(),
                request.name(),
                request.inactive()
        );
        productCategoryRepo.update(updated);
    }

    // Inativa uma categoria (delete lógico)
    public void deleteProductCategory(Long id) {
        productCategoryRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada. ID: " + id));
        productCategoryRepo.softDelete(id);
    }

    // Retorna um List com todas as categorias
    public List<ProductCategorySummaryResponse> getAllCategories() {
        return productCategoryRepo.findAll()
                .stream()
                .map(ProductCategoryMapper::toSummaryResponse)
                .toList();
    }

    // Retorna um List com todas as categorias marcadas inactive = FALSE
    public List<ProductCategorySummaryResponse> getAllActiveCategories() {
        return productCategoryRepo.findAllActive()
                .stream()
                .map(ProductCategoryMapper::toSummaryResponse) // Mapeia o retorno do repo para um DTO valido
                .toList();
    }

    // Retorna um List com as categorias da keyword digitada (pesquisa por nome)
    public List<ProductCategoryDetailsResponse> getCategoriesByName(String keyword) {
        return productCategoryRepo.findAllByName(keyword)
                .stream()
                .map(ProductCategoryMapper::toDetailsResponse)
                .toList();
    }
}