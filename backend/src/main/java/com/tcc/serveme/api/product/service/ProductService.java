package com.tcc.serveme.api.product.service;

import com.tcc.serveme.api.exception.BadRequestException;
import com.tcc.serveme.api.exception.NotFoundException;
import com.tcc.serveme.api.product.dto.NewProductRequest;
import com.tcc.serveme.api.product.dto.ProductDetailsResponse;
import com.tcc.serveme.api.product.dto.ProductSummaryResponse;
import com.tcc.serveme.api.product.dto.UpdateProductRequest;
import com.tcc.serveme.api.product.mapper.ProductMapper;
import com.tcc.serveme.api.product.entity.Product;
import com.tcc.serveme.api.category.entity.ProductCategory;
import com.tcc.serveme.api.category.repository.ProductCategoryRepository;
import com.tcc.serveme.api.product.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepo;
    private final ProductCategoryRepository productCategoryRepo;

    @Value("${serve-me.images.base-url}")
    private String imageBaseUrl;

    @Autowired
    public ProductService(ProductRepository productRepo, ProductCategoryRepository productCategoryRepo) {
        this.productRepo = productRepo;
        this.productCategoryRepo = productCategoryRepo;
    }


    // Cria produto novo no banco
    @Transactional
    public void createProduct(NewProductRequest request) {
        productCategoryRepo.findById(request.categoryId())
                .orElseThrow(() -> new BadRequestException("Categoria não encontrada. ID:" + request.categoryId()));

        Product product = ProductMapper.toModel(request);
        productRepo.save(product);
    }

    // Atualiza os dados de um produto existente
    @Transactional
    public void updateProduct(Long id, UpdateProductRequest request) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado. ID: " + id));

        productCategoryRepo.findById(request.categoryId())
                .orElseThrow(() -> new BadRequestException("Categoria não encontrada. ID: " + request.categoryId()));

        Product updated = new Product(
                product.getId(),
                request.categoryId(),
                request.name(),
                request.description(),
                request.price(),
                request.imageUrl(),
                request.active(),
                request.available()
        );
        productRepo.update(updated);
    }

    // Inativa um produto (delete lógico)
    public void deleteProduct(Long id) {
        productRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado. ID: " + id));
        productRepo.softDelete(id);
    }

    // Retorna um List com todos os produtos (tem LIMIT no repo)
    public List<ProductSummaryResponse> getAllProducts() {
        return productRepo.findAll()
                .stream()
                .map(p -> ProductMapper.toSummaryResponse(p, imageBaseUrl))
                .toList();
    }

    // Retorna os detalhes de um produto pelo ID do banco
    public ProductDetailsResponse getDetailsById(Long id) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado. ID: " + id));
        ProductCategory category = productCategoryRepo.findById(product.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada. ID: " + id));

        return ProductMapper.toDetailsResponse(product, category.getName(), imageBaseUrl);
    }

    // Retorna um List com os produtos da keyword digitada (pesquisa por nome)
    public List<ProductSummaryResponse> getProductsByName(String keyword) {
        return productRepo.findAllByName(keyword)
                .stream()
                .map(p -> ProductMapper.toSummaryResponse(p, imageBaseUrl))
                .toList();
    }

    // Retorna um List com os produtos da categoria (ativos ou não)
    public List<ProductSummaryResponse> getProductsByCategory(Long categoryId) {
        return productRepo.findAllByCategory(categoryId)
                .stream()
                .map(p -> ProductMapper.toSummaryResponse(p, imageBaseUrl))
                .toList();
    }

    // Retorna um List com os produtos ativos da categoria
    public List<ProductSummaryResponse> getAvailableProductsByCategory(Long categoryId) {
        return productRepo.findAllAvailableByCategory(categoryId)
                .stream()
                .map(p -> ProductMapper.toSummaryResponse(p, imageBaseUrl))  // Mapeia o retorno do repo para um DTO valido
                .toList();
    }
}