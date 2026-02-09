package com.tcc.serveme.api.repository.mapper;

import com.tcc.serveme.api.model.Orders;
import com.tcc.serveme.api.model.enums.OrderStatus;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrdersRowMapper implements RowMapper<Orders> {

    @Override
    public Orders mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Orders(
            rs.getLong("id"),
            rs.getInt("table_number"),
            rs.getString("customer_name"),
            OrderStatus.valueOf(rs.getString("status")),
            rs.getTimestamp("created_at").toLocalDateTime()
        );
    }
}