package com.tcc.serveme.api.repository.mapper;

import com.tcc.serveme.api.model.Product;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper implements RowMapper<Product>{

    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Product(
            rs.getLong("id"),
            rs.getLong("category_id"),
            rs.getString("name"),
            rs.getString("description"),
            rs.getBigDecimal("price"),
            rs.getBoolean("inactive")
        );
    }
}