package com.tcc.serveme.api.repository;

import com.tcc.serveme.api.model.OrderItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class OrderItemRepository {
    private final JdbcTemplate jdbc;

    @Autowired
    public OrderItemRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }
    
}