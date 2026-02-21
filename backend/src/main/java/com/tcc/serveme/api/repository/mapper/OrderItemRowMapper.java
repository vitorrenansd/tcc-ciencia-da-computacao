package com.tcc.serveme.api.repository.mapper;

import com.tcc.serveme.api.model.OrderItem;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderItemRowMapper implements RowMapper<OrderItem> {

    @Override
    public OrderItem mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new OrderItem(
            rs.getLong("id"),
            rs.getLong("order_id"),
            rs.getLong("product_id"),
            rs.getString("product_name"),
            rs.getBigDecimal("product_price"),
            rs.getInt("quantity"),
            rs.getString("notes"),
            rs.getBoolean("canceled")
        );
    }
}