package com.tcc.serveme.api.repository;

import com.tcc.serveme.api.model.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.sql.Statement;
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

    public long save(Product product) {
        String sql = """
                INSERT INTO product (fk_category, name, description, price, inactive)
                VALUES (?, ?, ?, ?, ?)
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                sql,
                Statement.RETURN_GENERATED_KEYS
            );
            ps.setLong(1, product.getCategory().getId());
            ps.setString(2, product.getName());
            ps.setString(3, product.getDescription());
            ps.setBigDecimal(4, product.getPrice());
            ps.setBoolean(5, product.isInactive());
            return ps;
        }, keyHolder);
       return keyHolder.getKey().longValue();
    }

    public int update(Product product) {
        String sql = """
                UPDATE product
                SET fk_category = ?, name = ?, description = ?, price = ?, inactive = ?
                WHERE id = ?
                """;
        return jdbc.update(sql, product.getCategory(), product.getName(), product.getDescription(), product.getPrice(), product.isInactive(), product.getId());
    }

    public int softDelete(Long id) {
        String sql = """
                UPDATE product
                SET inactive = TRUE
                WHERE id = ?
                """;
        return jdbc.update(sql, id);
    }

    // *******************************
    // Product-specific queries below
    // *******************************

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

    public List<Product> findByNameIncludingInactive(String keyword) {
        String sql = """
                SELECT id, fk_category, name, description, price, inactive
                FROM product
                WHERE name LIKE ?
                """;
        String searchPattern = "%" + keyword + "%";
        return jdbc.query(sql, new BeanPropertyRowMapper<>(Product.class), searchPattern);
    }
}