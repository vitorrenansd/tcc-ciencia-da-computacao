package com.tcc.serveme.api.repository;

import com.tcc.serveme.api.model.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class ProductRepository {
    private final JdbcTemplate jdbc;

    @Autowired
    public ProductRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Product findById(Long id) {
        String sql = """
                SELECT id, fk_category, name, description, price, inactive
                FROM product
                WHERE id = ?
                AND inactive = FALSE
                """;
        Product product = jdbc.queryForObject(sql, new BeanPropertyRowMapper<>(Product.class), id);
        return product;
    }

    public List<Product> findAll() {
        String sql = """
                SELECT id, fk_category, name, description, price, inactive
                FROM product
                WHERE inactive = FALSE
                """;
        List<Product> products = jdbc.query(sql, new BeanPropertyRowMapper<>(Product.class));
        return products;
    }

    public int save(Product product) {
        String sql = """
                INSERT INTO product (fk_category, name, description, price, inactive)
                VALUES (?, ?, ?, ?, ?)
                """;
        return jdbc.update(sql, product.getCategory(), product.getName(), product.getDescription(), product.getPrice(), product.isInactive());
    }

    public boolean update(Product product) {
        String sql = """
                UPDATE product
                SET fk_category = ?, name = ?, description = ?, price = ?, inactive = ?
                WHERE id = ?
                """;
        int rows = jdbc.update(sql, product.getCategory(), product.getName(), product.getDescription(), product.getPrice(), product.isInactive(), product.getId());
        return rows == 1;
    }

    public boolean softDelete(Long id) {
        String sql = """
                UPDATE product
                SET inactive = TRUE
                WHERE id = ?
                """;
        int rows = jdbc.update(sql, id);
        return rows == 1;
    }

    // ************************
    //  Specific queries below
    // ************************
    
    public Product findByIdIncludingInactive(Long id) {
        String sql = """
                SELECT id, fk_category, name, description, price, inactive
                FROM product
                WHERE id = ?
                """;
        Product product = jdbc.queryForObject(sql, new BeanPropertyRowMapper<>(Product.class), id);
        return product;
    }

    public List<Product> findAllIncludingInactive() {
        String sql = """
                SELECT id, fk_category, name, description, price, inactive
                FROM product
                """;
        List<Product> products = jdbc.query(sql, new BeanPropertyRowMapper<>(Product.class));
        return products;
    }

    public List<Product> findByName(String keyword) {
        String sql = """
                SELECT id, fk_category, name, description, price, inactive
                FROM product
                WHERE name LIKE ?
                AND inactive = FALSE
                """;
        String searchPattern = "%" + keyword + "%";
        return jdbc.query(sql, new BeanPropertyRowMapper<>(Product.class), searchPattern);
    }

    public List<Product> findByCategory(Long categoryId) {
        String sql = """
                SELECT id, fk_category, name, description, price, inactive
                FROM product
                WHERE fk_category = ?
                AND inactive = FALSE
                """;
        return jdbc.query(sql, new BeanPropertyRowMapper<>(Product.class), categoryId);
    }
}