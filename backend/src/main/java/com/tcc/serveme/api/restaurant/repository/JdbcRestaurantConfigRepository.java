package com.tcc.serveme.api.restaurant.repository;

import com.tcc.serveme.api.restaurant.entity.RestaurantConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcRestaurantConfigRepository implements RestaurantConfigRepository {
    private final JdbcTemplate jdbc;
    private static final RowMapper<RestaurantConfig> ROW_MAPPER =
            (rs, rowNum) -> new RestaurantConfig(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("icon_filename")
                    // Caso adicionar novas colunas na tabela, atualizar aqui
            );

    @Autowired
    public JdbcRestaurantConfigRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public int update(RestaurantConfig restaurantConfig) {
        String sql = """
                UPDATE restaurant_config
                SET name = UPPER(?)
                WHERE id = ?
                """;
        return jdbc.update(sql,
                restaurantConfig.getName(),
                restaurantConfig.getId()
        );
    }

    @Override
    public void updateIconFilename(Long id, String iconFilename) {
        String sql = """
                UPDATE restaurant_config
                SET icon_filename = ?
                WHERE id = ?
                """;
        jdbc.update(sql, iconFilename, id);
    }

    @Override
    public Optional<RestaurantConfig> find(Long id) {
        String sql = """
                SELECT id, name, icon_filename
                FROM restaurant_config
                WHERE id = ?
                """;
        List<RestaurantConfig> result = jdbc.query(sql, ROW_MAPPER, id);
        return result.stream().findFirst();
    }
}