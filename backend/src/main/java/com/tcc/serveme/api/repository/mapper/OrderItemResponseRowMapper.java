package com.tcc.serveme.api.repository.mapper;

import com.tcc.serveme.api.dto.order.OrderItemDetailsResponse;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderItemResponseRowMapper implements RowMapper<OrderItemDetailsResponse> {

    @Override
    public OrderItemDetailsResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new OrderItemDetailsResponse(
                rs.getLong("id"),
                rs.getLong("product_id"),
                rs.getString("product_name"),
                rs.getBigDecimal("product_price"),
                rs.getInt("quantity"),
                rs.getString("notes"),
                rs.getBoolean("canceled")
        );
    }
}
