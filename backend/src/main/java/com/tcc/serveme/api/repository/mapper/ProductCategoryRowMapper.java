package com.tcc.serveme.api.repository.mapper;

import com.tcc.serveme.api.model.ProductCategory;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductCategoryRowMapper implements RowMapper<ProductCategory> {

    @Override
    public ProductCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ProductCategory(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getBoolean("inactive")
        );
    }
}
