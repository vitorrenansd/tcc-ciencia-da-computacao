package com.tcc.serveme.api.repository.mapper;

import com.tcc.serveme.api.dto.order.OrderItemResponse;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderItemResponseRowMapper implements RowMapper<OrderItemResponse> {

    @Override
    public OrderItemResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new OrderItemResponse(
                rs.getLong("id"),
                rs.getLong("product_id"),
                rs.getString("product_name"),
                rs.getBigDecimal("price"),
                rs.getInt("quantity"),
                rs.getString("notes"),
                rs.getBoolean("canceled")
        );
    }
}
