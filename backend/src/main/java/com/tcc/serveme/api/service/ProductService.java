package com.tcc.serveme.api.service;

import com.tcc.serveme.api.dto.product.NewProductRequest;
import com.tcc.serveme.api.dto.product.ProductDetailsResponse;
import com.tcc.serveme.api.dto.product.ProductSummaryResponse;
import com.tcc.serveme.api.mapper.ProductMapper;
import com.tcc.serveme.api.entity.Product;
import com.tcc.serveme.api.entity.ProductCategory;
import com.tcc.serveme.api.repository.ProductCategoryRepository;
import com.tcc.serveme.api.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepo;
    private final ProductCategoryRepository productCategoryRepo;

    @Autowired
    public ProductService(ProductRepository productRepo, ProductCategoryRepository productCategoryRepo) {
        this.productRepo = productRepo;
        this.productCategoryRepo = productCategoryRepo;
    }


    // Cria produto novo no banco
    @Transactional
    public void createProduct(NewProductRequest request) {
        productCategoryRepo.findById(request.categoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria não encontrada. ID:" + request.categoryId()));

        Product product = ProductMapper.toModel(request);
        productRepo.save(product);
    }

    // Retorna os detalhes de um produto pelo ID do banco
    public ProductDetailsResponse getDetailsById(Long id) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
        ProductCategory category = productCategoryRepo.findById(product.getCategoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada"));

        return ProductMapper.toDetailsResponse(product, category.getName());
    }

    // Retorna um List com todos os produtos ativos
    public List<ProductSummaryResponse> getAllActiveProducts() {
        return productRepo.findAllActive()
                .stream()
                .map(ProductMapper::toSummaryResponse)  // Mapeia o retorno do repo para um DTO valido
                .toList();
    }

    // Retorna um List com os produtos ativos da categoria
    public List<ProductSummaryResponse> getActiveProductsByCategory(Long categoryId) {
        return productRepo.findAllActiveByCategory(categoryId)
                .stream()
                .map(ProductMapper::toSummaryResponse)  // Mapeia o retorno do repo para um DTO valido
                .toList();
    }
}