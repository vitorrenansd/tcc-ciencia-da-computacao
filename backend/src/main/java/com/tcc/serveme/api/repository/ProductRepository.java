package com.tcc.serveme.api.repository;

import com.tcc.serveme.api.model.Product;
import com.tcc.serveme.api.repository.mapper.ProductRowMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class ProductRepository {
    private final JdbcTemplate jdbc;
    private static final ProductRowMapper ROW_MAPPER = new ProductRowMapper();

    @Autowired
    public ProductRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Product findById(Long id) {
        String sql = """
                SELECT id, category_id, name, description, price, inactive
                FROM product
                WHERE id = ?
                AND inactive = FALSE
                """;
        return jdbc.queryForObject(sql, ROW_MAPPER, id);
    }

    public List<Product> findAll() {
        String sql = """
                SELECT id, category_id, name, description, price, inactive
                FROM product
                WHERE inactive = FALSE
                """;
        return jdbc.query(sql, ROW_MAPPER);
    }

    public int save(Product product) {
        String sql = """
                INSERT INTO product (category_id, name, description, price, inactive)
                VALUES (?, ?, ?, ?, ?)
                """;
        return jdbc.update(sql, product.getCategoryId(), product.getName(), product.getDescription(), product.getPrice(), product.isInactive());
    }

    public int update(Product product) {
        String sql = """
                UPDATE product
                SET category_id = ?, name = ?, description = ?, price = ?, inactive = ?
                WHERE id = ?
                """;
        return jdbc.update(sql, product.getCategoryId(), product.getName(), product.getDescription(), product.getPrice(), product.isInactive(), product.getId());
    }

    public int softDelete(Long id) {
        String sql = """
                UPDATE product
                SET inactive = TRUE
                WHERE id = ?
                """;
        return jdbc.update(sql, id);
    }

    // ************************
    //  Specific queries below
    // ************************
    
    public Product findByIdIncludingInactive(Long id) {
        String sql = """
                SELECT id, category_id, name, description, price, inactive
                FROM product
                WHERE id = ?
                """;
        return jdbc.queryForObject(sql, ROW_MAPPER, id);
    }

    public List<Product> findAllIncludingInactive() {
        String sql = """
                SELECT id, category_id, name, description, price, inactive
                FROM product
                """;
        return jdbc.query(sql, ROW_MAPPER);
    }

    public List<Product> findByName(String keyword) {
        String sql = """
                SELECT id, category_id, name, description, price, inactive
                FROM product
                WHERE name LIKE ?
                AND inactive = FALSE
                """;
        String searchPattern = "%" + keyword + "%";
        return jdbc.query(sql, ROW_MAPPER, searchPattern);
    }

    public List<Product> findByCategory(Long categoryId) {
        String sql = """
                SELECT id, category_id, name, description, price, inactive
                FROM product
                WHERE category_id = ?
                AND inactive = FALSE
                """;
        return jdbc.query(sql, ROW_MAPPER, categoryId);
    }
}