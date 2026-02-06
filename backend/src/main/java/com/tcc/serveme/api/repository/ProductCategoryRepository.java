package com.tcc.serveme.api.repository;

import com.tcc.serveme.api.model.ProductCategory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class ProductCategoryRepository {
    private final JdbcTemplate jdbc;

    @Autowired
    public ProductCategoryRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public ProductCategory findById(Long id) {
        String sql = """
                SELECT id, name, inactive
                FROM product_category
                WHERE id = ?
                AND inactive = FALSE
                """;
        ProductCategory productCategory = jdbc.queryForObject(sql, new BeanPropertyRowMapper<>(ProductCategory.class), id);
        return productCategory;
    }

    public List<ProductCategory> findAll() {
        String sql = """
                SELECT id, name, inactive
                FROM product_category
                WHERE inactive = FALSE
                """;
        List<ProductCategory> productCategories = jdbc.query(sql, new BeanPropertyRowMapper<>(ProductCategory.class));
        return productCategories;
    }

    public int save(ProductCategory productCategory) {
        String sql = """
                INSERT INTO product_category (name, inactive)
                VALUES (?, ?)
                """;
        return jdbc.update(sql, productCategory.getName(), productCategory.isInactive());
    }

    public boolean update(ProductCategory productCategory) {
        String sql = """
                UPDATE product_category
                SET name = ?, inactive = ?
                WHERE id = ?
                """;
        int rows = jdbc.update(sql, productCategory.getName(), productCategory.isInactive(), productCategory.getId());
        return rows == 1;
    }

    public boolean softDelete(Long id) {
        String sql = """
                UPDATE product_category
                SET inactive = TRUE
                WHERE id = ?
                """;
        int rows = jdbc.update(sql, id);
        return rows == 1;
    }

    // ************************
    //  Specific queries below
    // ************************

    public ProductCategory findByIdIncludingInactive(Long id) {
        String sql = """
                SELECT id, name, inactive
                FROM product_category
                WHERE id = ?
                """;
        ProductCategory productCategory = jdbc.queryForObject(sql, new BeanPropertyRowMapper<>(ProductCategory.class), id);
        return productCategory;
    }

    public List<ProductCategory> findAllIncludingInactive() {
        String sql = """
                SELECT id, name, inactive
                FROM product_category
                """;
        List<ProductCategory> productCategories = jdbc.query(sql, new BeanPropertyRowMapper<>(ProductCategory.class));
        return productCategories;
    }

    public List<ProductCategory> findByName(String keyword) {
        String sql = """
                SELECT id, name, inactive
                FROM product_category
                WHERE name LIKE ?
                AND inactive = FALSE
                """;
        String searchPattern = "%" + keyword + "%";
        return jdbc.query(sql, new BeanPropertyRowMapper<>(ProductCategory.class), searchPattern);
    }
}