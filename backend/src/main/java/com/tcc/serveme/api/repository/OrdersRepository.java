package com.tcc.serveme.api.repository;

import com.tcc.serveme.api.model.Orders;
import com.tcc.serveme.api.repository.mapper.OrdersRowMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class OrdersRepository {
    private final JdbcTemplate jdbc;
    private static final OrdersRowMapper ROW_MAPPER = new OrdersRowMapper();

    @Autowired
    public OrdersRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }
    
    public Orders findById(Long id) {
        String sql = """
                SELECT id, table_number, customer_name, created_at, status
                FROM orders
                WHERE id = ?
                """;
        return jdbc.queryForObject(sql, ROW_MAPPER, id);
    }

    public List<Orders> findAll() {
        String sql = """
                SELECT id, table_number, customer_name, created_at, status
                FROM orders
                """;
        return jdbc.query(sql, ROW_MAPPER);
    }

    public Long save(Orders order) { // keyholder necessario para adicao de item em order nova
        String sql = """
                INSERT INTO orders (table_number, customer_name, created_at, status)
                VALUES (?, ?, ?, ?)
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, order.getTableNumber());
            ps.setString(2, order.getCustomerName());
            ps.setTimestamp(3, Timestamp.valueOf(order.getCreatedAt()));
            ps.setString(4, order.getStatus().name());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    // ************************
    //  Specific queries below
    // ************************

    public List<Orders> findActiveOrdersByTableNumber(String tableNumber) {
        String sql = """
                SELECT id, table_number, customer_name, created_at, status
                FROM orders
                WHERE table_number LIKE ?
                AND status IN ('PENDING', 'IN_PROGRESS')
                """;
        String searchPattern = tableNumber + "%";
        return jdbc.query(sql, ROW_MAPPER, searchPattern);
    }

    public List<Orders> findActiveOrdersByCustomerName(String keyword) {
        String sql = """
                SELECT id, table_number, customer_name, created_at, status
                FROM orders
                WHERE customer_name LIKE ?
                AND status IN ('PENDING', 'IN_PROGRESS')
                """;
        String searchPattern = "%" + keyword + "%";
        return jdbc.query(sql, ROW_MAPPER, searchPattern);
    }

    public int markAsInProgress(Long id) {
        String sql = """
                UPDATE orders
                SET status = 'IN_PROGRESS'
                WHERE id = ?
                """;
        return jdbc.update(sql, id);
    }

    public int markAsCompleted(Long id) {
        String sql = """
                UPDATE orders
                SET status = 'COMPLETED'
                WHERE id = ?
                """;
        return jdbc.update(sql, id);
    }

    public int cancel(Long id) {
        String sql = """
                UPDATE orders
                SET status = 'CANCELED'
                WHERE id = ?
                """;
        return jdbc.update(sql, id);
    }
}