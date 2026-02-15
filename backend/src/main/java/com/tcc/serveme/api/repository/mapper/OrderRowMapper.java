package com.tcc.serveme.api.repository.mapper;

import com.tcc.serveme.api.model.Order;
import com.tcc.serveme.api.model.enums.OrderStatus;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderRowMapper implements RowMapper<Order> {

    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Order(
            rs.getLong("id"),
            rs.getInt("table_number"),
            rs.getString("customer_name"),
            OrderStatus.valueOf(rs.getString("status")),
            rs.getTimestamp("created_at").toLocalDateTime()
        );
    }
}