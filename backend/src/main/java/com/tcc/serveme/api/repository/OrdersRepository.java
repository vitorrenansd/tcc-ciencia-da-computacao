package com.tcc.serveme.api.repository;

import com.tcc.serveme.api.model.Orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class OrdersRepository {
    private final JdbcTemplate jdbc;

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
        Orders order = jdbc.queryForObject(sql, new BeanPropertyRowMapper<>(Orders.class), id);
        return order;
    }

    public List<Orders> findAll() {
        String sql = """
                SELECT id, table_number, customer_name, created_at, status
                FROM orders
                """;
        List<Orders> orders = jdbc.query(sql, new BeanPropertyRowMapper<>(Orders.class));
        return orders;
    }

    public int save(Orders order) {
        String sql = """
                INSERT INTO orders (table_number, customer_name, created_at, status)
                VALUES (?, ?, ?, ?)
                """;
        return jdbc.update(sql, order.getTableNumber(), order.getCustomerName(), order.getCreatedAt(), order.getStatus());
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
        return jdbc.query(sql, new BeanPropertyRowMapper<>(Orders.class), searchPattern);
    }

    public List<Orders> findActiveOrdersByCustomerName(String keyword) {
        String sql = """
                SELECT id, table_number, customer_name, created_at, status
                FROM orders
                WHERE customer_name LIKE ?
                AND status IN ('PENDING', 'IN_PROGRESS')
                """;
        String searchPattern = "%" + keyword + "%";
        return jdbc.query(sql, new BeanPropertyRowMapper<>(Orders.class), searchPattern);
    }

    public boolean markAsInProgress(Long id) {
        String sql = """
                UPDATE orders
                SET status = 'IN_PROGRESS'
                WHERE id = ?
                """;
        int rows = jdbc.update(sql, id);
        return rows == 1;
    }

    public boolean markAsCompleted(Long id) {
        String sql = """
                UPDATE orders
                SET status = 'COMPLETED'
                WHERE id = ?
                """;
        int rows = jdbc.update(sql, id);
        return rows == 1;
    }

    public boolean cancel(Long id) {
        String sql = """
                UPDATE orders
                SET status = 'CANCELED'
                WHERE id = ?
                """;
        int rows = jdbc.update(sql, id);
        return rows == 1;
    }
}