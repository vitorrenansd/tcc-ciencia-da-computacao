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
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepo;
    private final ProductCategoryRepository productCategoryRepo;

    @Value("${serve-me.images.path}")
    private String uploadDir;
    @Value("${serve-me.images.base-url}")
    private String baseUrl;

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
                .orElseThrow(NotFoundException::new);

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

    public void uploadImage(Long id, MultipartFile file) {
        // Verifica se produto existe
        Product product = productRepo.findById(id)
                .orElseThrow(NotFoundException::new);

        // Valida se é imagem
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new BadRequestException("Formato de arquivo inválido.");
        }

        // Gera nome único com UUID mantendo a extensão original
        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        String filename = UUID.randomUUID().toString() + "." + extension;

        // Salva o arquivo no diretório configurado
        try {
            Path destination = Paths.get(uploadDir).resolve("products/").resolve(filename);
            Files.createDirectories(destination.getParent());
            file.transferTo(destination.toFile());
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar imagem.", e);
        }

        // Se já havia imagem anterior, deleta do disco
        if (product.getImageFilename() != null) {
            try {
                Path old = Paths.get(uploadDir).resolve("products/").resolve(product.getImageFilename());
                Files.deleteIfExists(old);
            } catch (IOException ignored) {}
        }

        // Atualiza o filename no banco
        productRepo.updateImageFilename(id, filename);
    }

    // Inativa um produto (delete lógico)
    public void deleteProduct(Long id) {
        productRepo.findById(id)
                .orElseThrow(NotFoundException::new);
        productRepo.softDelete(id);
    }

    public void deleteImage(Long id) {
        Product product = productRepo.findById(id)
                .orElseThrow(NotFoundException::new);

        if (product.getImageFilename() == null) {
            throw new BadRequestException("Produto não possui imagem.");
        }

        // Deleta do disco
        try {
            Path path = Paths.get(uploadDir).resolve("products/").resolve(product.getImageFilename());
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao deletar imagem.", e);
        }

        // Remove o filename do banco
        productRepo.updateImageFilename(id, null);
    }

    // Retorna um List com todos os produtos (tem LIMIT no repo)
    public List<ProductSummaryResponse> getAllProducts() {
        return productRepo.findAll()
                .stream()
                .map(p -> ProductMapper.toSummaryResponse(p, baseUrl))
                .toList();
    }

    // Retorna os detalhes de um produto pelo ID do banco
    public ProductDetailsResponse getDetailsById(Long id) {
        Product product = productRepo.findById(id)
                .orElseThrow(NotFoundException::new);
        ProductCategory category = productCategoryRepo.findById(product.getCategoryId())
                .orElseThrow(NotFoundException::new);

        return ProductMapper.toDetailsResponse(product, category.getName(), baseUrl);
    }

    // Retorna um List com os produtos da keyword digitada (pesquisa por nome)
    public List<ProductSummaryResponse> getProductsByName(String keyword) {
        return productRepo.findAllByName(keyword)
                .stream()
                .map(p -> ProductMapper.toSummaryResponse(p, baseUrl))
                .toList();
    }

    // Retorna um List com os produtos da categoria (ativos ou não)
    public List<ProductSummaryResponse> getProductsByCategory(Long categoryId) {
        return productRepo.findAllByCategory(categoryId)
                .stream()
                .map(p -> ProductMapper.toSummaryResponse(p, baseUrl))
                .toList();
    }

    // Retorna um List com os produtos ativos da categoria
    public List<ProductSummaryResponse> getAvailableProductsByCategory(Long categoryId) {
        return productRepo.findAllAvailableByCategory(categoryId)
                .stream()
                .map(p -> ProductMapper.toSummaryResponse(p, baseUrl))  // Mapeia o retorno do repo para um DTO valido
                .toList();
    }
}