package com.tcc.serveme.api.repository;

import com.tcc.serveme.api.model.ProductCategory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductCategoryRepository {
    private final JdbcTemplate jdbc;
    private static final RowMapper<ProductCategory> ROW_MAPPER =
            (rs, rowNum) -> new ProductCategory(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getBoolean("inactive")
            );

    @Autowired
    public ProductCategoryRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public ProductCategory findById(Long id) {
        String sql = """
                SELECT id, name, inactive
                FROM product_category
                WHERE id = ?
                """;
        return jdbc.queryForObject(sql, ROW_MAPPER, id);
    }

    public List<ProductCategory> findAll() {
        String sql = """
                SELECT id, name, inactive
                FROM product_category
                """;
        return jdbc.query(sql, ROW_MAPPER);
    }

    public int save(ProductCategory productCategory) {
        String sql = """
                INSERT INTO product_category (name, inactive)
                VALUES (?, ?)
                """;
        return jdbc.update(sql, productCategory.getName(), productCategory.isInactive());
    }

    public int update(ProductCategory productCategory) {
        String sql = """
                UPDATE product_category
                SET name = ?, inactive = ?
                WHERE id = ?
                """;
        return jdbc.update(sql, productCategory.getName(), productCategory.isInactive(), productCategory.getId());
    }

    public int softDelete(Long id) {
        String sql = """
                UPDATE product_category
                SET inactive = TRUE
                WHERE id = ?
                """;
        return jdbc.update(sql, id);
    }

    // ************************
    //  Specific queries below
    // ************************

    public List<ProductCategory> findAllActive() {
        String sql = """
                SELECT id, name, inactive
                FROM product_category
                WHERE inactive = FALSE
                """;
        return jdbc.query(sql, ROW_MAPPER);
    }

    public List<ProductCategory> findByNameActive(String keyword) {
        String sql = """
                SELECT id, name, inactive
                FROM product_category
                WHERE name LIKE ?
                AND inactive = FALSE
                """;
        String searchPattern = "%" + keyword + "%";
        return jdbc.query(sql, ROW_MAPPER, searchPattern);
    }
}