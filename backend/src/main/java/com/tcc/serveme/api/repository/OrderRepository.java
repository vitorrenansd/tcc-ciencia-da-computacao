package com.tcc.serveme.api.repository;

import com.tcc.serveme.api.model.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class OrderRepository {
    private final JdbcTemplate jdbc;

    @Autowired
    public OrderRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }
    
    public Order findById(Long id) {
        String sql = """
                SELECT id, table_number, waiter, created_at, status
                FROM order
                WHERE id = ?
                """;
        Order order = jdbc.queryForObject(sql, new BeanPropertyRowMapper<>(Order.class), id);
        return order;
    }

    public List<Order> findAll() {
        String sql = """
                SELECT id, table_number, waiter, created_at, status
                FROM order
                """;
        List<Order> orders = jdbc.query(sql, new BeanPropertyRowMapper<>(Order.class));
        return orders;
    }

    public int save(Order order) {
        String sql = """
                INSERT INTO order (table_number, waiter, created_at, status)
                VALUES (?, ?, ?, ?)
                """;
        return jdbc.update(sql, order.getTableNumber(), order.getWaiter(), order.getCreatedAt(), order.getStatus());
    }

    // ************************
    //  Specific queries below
    // ************************

    public List<Order> findByTableNumber(String tableNumber) {
        String sql = """
                SELECT id, table_number, customer_name, created_at, status
                FROM order
                WHERE table_number LIKE ?
                """;
        String searchPattern = tableNumber + "%";
        return jdbc.query(sql, new BeanPropertyRowMapper<>(Order.class), searchPattern);
    }

    public List<Order> findByCustomerName(String keyword) {
        String sql = """
                SELECT id, table_number, customer_name, created_at, status
                FROM order
                WHERE customer_name LIKE ?
                """;
        String searchPattern = "%" + keyword + "%";
        return jdbc.query(sql, new BeanPropertyRowMapper<>(Order.class), searchPattern);
    }

    public boolean markAsInProgress(Long id) {
        String sql = """
                UPDATE order
                SET status = 'IN_PROGRESS'
                WHERE id = ?
                """;
        int rows = jdbc.update(sql, id);
        return rows == 1;
    }

    public boolean markAsCompleted(Long id) {
        String sql = """
                UPDATE order
                SET status = 'COMPLETED'
                WHERE id = ?
                """;
        int rows = jdbc.update(sql, id);
        return rows == 1;
    }

    public boolean cancel(Long id) {
        String sql = """
                UPDATE order
                SET status = 'CANCELED'
                WHERE id - ?
                """;
        int rows = jdbc.update(sql, id);
        return rows == 1;
    }
}