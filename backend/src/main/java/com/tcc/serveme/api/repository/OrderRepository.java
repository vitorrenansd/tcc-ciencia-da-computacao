package com.tcc.serveme.api.repository;

import com.tcc.serveme.api.model.Order;
import com.tcc.serveme.api.repository.mapper.OrderRowMapper;

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
public class OrderRepository {
    private final JdbcTemplate jdbc;
    private static final OrderRowMapper ROW_MAPPER = new OrderRowMapper();

    @Autowired
    public OrderRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }
    
    public Order findById(Long id) {
        String sql = """
                SELECT id, table_number, customer_name, created_at, status
                FROM orders
                WHERE id = ?
                """;
        return jdbc.queryForObject(sql, ROW_MAPPER, id);
    }

    public List<Order> findAll() {
        String sql = """
                SELECT id, table_number, customer_name, created_at, status
                FROM orders
                """;
        return jdbc.query(sql, ROW_MAPPER);
    }

    public Long save(Order order) { // Keyholder necessário para adição de itens em pedidos novos
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
        return keyHolder.getKeyAs(Long.class);
    }

    // ************************
    //  Specific queries below
    // ************************

    public List<Order> findAllPendingOrders() {
        String sql = """
                SELECT id, table_number, customer_name, created_at, status
                FROM orders
                WHERE status IN ('PENDING')
                """;
        return jdbc.query(sql, ROW_MAPPER);
    }

    public int markAsInProgress(Long id) {
        String sql = """
                UPDATE orders
                SET status = 'IN_PROGRESS'
                WHERE id = ?
                """;
        return jdbc.update(sql, id);
    }

    public int markAsServed(Long id) {
        String sql = """
                UPDATE orders
                SET status = 'SERVED'
                WHERE id = ?
                """;
        return jdbc.update(sql, id);
    }

    public int markAsPaid(Long id) {
        String sql = """
                UPDATE orders
                SET status = 'PAID'
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