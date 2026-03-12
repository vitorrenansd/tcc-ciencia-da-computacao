package com.tcc.serveme.api.product.repository;

import com.tcc.serveme.api.product.entity.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcProductRepository implements ProductRepository {
    private final JdbcTemplate jdbc;
    private static final RowMapper<Product> ROW_MAPPER =
            (rs, rowNum) -> new Product(
                    rs.getLong("id"),
                    rs.getLong("category_id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getBigDecimal("price"),
                    rs.getBoolean("inactive")
                    // Caso adicionar novas colunas na tabela, atualizar aqui
            );

    @Autowired
    public JdbcProductRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Optional<Product> findById(Long id) {
        String sql = """
                SELECT id, category_id, name, description, price, inactive
                FROM product
                WHERE id = ?
                """;
        List<Product> result = jdbc.query(sql, ROW_MAPPER, id);
        return result.stream().findFirst();
    }

    @Override
    public List<Product> findAll() {
        String sql = """
                SELECT id, category_id, name, description, price, inactive
                FROM product
                """;
        return jdbc.query(sql, ROW_MAPPER);
    }

    @Override
    public int save(Product product) {
        String sql = """
                INSERT INTO product (category_id, name, description, price, inactive)
                VALUES (?, ?, ?, ?, ?)
                """;
        return jdbc.update(sql, product.getCategoryId(), product.getName(), product.getDescription(), product.getPrice(), product.isInactive());
    }

    @Override
    public int update(Product product) {
        String sql = """
                UPDATE product
                SET category_id = ?, name = ?, description = ?, price = ?, inactive = ?
                WHERE id = ?
                """;
        return jdbc.update(sql, product.getCategoryId(), product.getName(), product.getDescription(), product.getPrice(), product.isInactive(), product.getId());
    }

    @Override
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

    @Override
    public Optional<Product> findByIdActive(Long id) {
        String sql = """
                SELECT id, category_id, name, description, price, inactive
                FROM product
                WHERE id = ?
                AND inactive = FALSE
                """;
        List<Product> result = jdbc.query(sql, ROW_MAPPER, id);
        return result.stream().findFirst();
    }

    @Override
    public List<Product> findAllActive() {
        String sql = """
                SELECT id, category_id, name, description, price, inactive
                FROM product
                WHERE inactive = FALSE
                """;
        return jdbc.query(sql, ROW_MAPPER);
    }

    @Override
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

    @Override
    public List<Product> findAllActiveByCategory(Long categoryId) {
        String sql = """
                SELECT id, category_id, name, description, price, inactive
                FROM product
                WHERE category_id = ?
                AND inactive = FALSE
                ORDER BY name
                """;
        return jdbc.query(sql, ROW_MAPPER, categoryId);
    }
}