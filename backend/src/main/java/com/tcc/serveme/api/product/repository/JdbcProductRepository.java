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
                    rs.getString("image_filename"),
                    rs.getBoolean("active"),
                    rs.getBoolean("available")
                    // Caso adicionar novas colunas na tabela, atualizar aqui
            );

    @Autowired
    public JdbcProductRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Optional<Product> findById(Long id) {
        String sql = """
                SELECT id, category_id, name, description, price, image_filename, active, available
                FROM product
                WHERE id = ?
                """;
        List<Product> result = jdbc.query(sql, ROW_MAPPER, id);
        return result.stream().findFirst();
    }

    @Override
    public List<Product> findAll() {
        String sql = """
                SELECT id, category_id, name, description, price, image_filename, active, available
                FROM product
                LIMIT 100
                """;
        return jdbc.query(sql, ROW_MAPPER);
    }

    @Override
    public int save(Product product) {
        String sql = """
                INSERT INTO product (category_id, name, description, price, image_filename, active, available)
                VALUES (?, UPPER(?), UPPER(?), ?, ?, ?, ?)
                """;
        return jdbc.update(sql,
                product.getCategoryId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getImageFilename(),
                product.isActive(),
                product.isAvailable()
        );
    }

    @Override
    public int update(Product product) {
        String sql = """
                UPDATE product
                SET category_id = ?,
                    name = UPPER(?),
                    description = UPPER(?),
                    price = ?,
                    image_filename = ?,
                    active = ?,
                    available = ?
                WHERE id = ?
                """;
        return jdbc.update(sql,
                product.getCategoryId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getImageFilename(),
                product.isActive(),
                product.isAvailable(),
                product.getId()
        );
    }

    @Override
    public int softDelete(Long id) {
        String sql = """
                UPDATE product
                SET active = FALSE
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
                SELECT id, category_id, name, description, price, image_filename, active, available
                FROM product
                WHERE id = ?
                AND active = TRUE
                """;
        List<Product> result = jdbc.query(sql, ROW_MAPPER, id);
        return result.stream().findFirst();
    }

    @Override
    public List<Product> findAllByName(String keyword) {
        String sql = """
                SELECT id, category_id, name, description, price, image_filename, active, available
                FROM product
                WHERE UPPER(name) LIKE UPPER(?)
                AND active = TRUE
                """;
        String searchPattern = "%" + keyword + "%";
        return jdbc.query(sql, ROW_MAPPER, searchPattern);
    }

    @Override
    public List<Product> findAllByCategory(Long categoryId) {
        String sql = """
                SELECT id, category_id, name, description, price, image_filename, active, available
                FROM product
                WHERE category_id = ?
                ORDER BY name
                """;
        return jdbc.query(sql, ROW_MAPPER, categoryId);
    }

    @Override
    public List<Product> findAllAvailableByCategory(Long categoryId) {
        String sql = """
                SELECT id, category_id, name, description, price, image_filename, active, available
                FROM product
                WHERE category_id = ?
                AND active = TRUE
                AND available = TRUE
                ORDER BY name
                """;
        return jdbc.query(sql, ROW_MAPPER, categoryId);
    }
}