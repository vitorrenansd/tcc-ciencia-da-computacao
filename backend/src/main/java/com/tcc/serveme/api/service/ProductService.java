package com.tcc.serveme.api.service;

import com.tcc.serveme.api.dto.product.NewProductRequest;
import com.tcc.serveme.api.dto.product.ProductDetailsResponse;
import com.tcc.serveme.api.dto.product.ProductSummaryResponse;
import com.tcc.serveme.api.mapper.ProductMapper;
import com.tcc.serveme.api.model.Product;
import com.tcc.serveme.api.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepo;

    @Autowired
    public ProductService(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }


    // Cria produto novo no banco
    @Transactional
    public void createProduct(NewProductRequest request) {
        Product product = ProductMapper.toModel(request);
        productRepo.save(product);
    }

    // Retorna os detalhes de um produto pelo ID do banco
    public ProductDetailsResponse getDetailsById(Long id) {
        return productRepo.findByIdActive(id)
                .map(ProductMapper::toDetailsResponse)  // Mapeia o retorno do repo para um DTO valido
                .orElse(null); // Retorna null caso não encontre o ID, trata na camada acima, evita exception
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