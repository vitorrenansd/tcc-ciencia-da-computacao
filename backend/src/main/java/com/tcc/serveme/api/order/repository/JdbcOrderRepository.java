package com.tcc.serveme.api.order.repository;

import com.tcc.serveme.api.order.entity.Order;
import com.tcc.serveme.api.order.entity.OrderStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcOrderRepository implements OrderRepository {
    private final JdbcTemplate jdbc;
    private static final RowMapper<Order> ROW_MAPPER =
            (rs, rowNum) -> new Order(
                    rs.getLong("id"),
                    rs.getLong("cash_shift_id"),
                    rs.getInt("table_number"),
                    rs.getString("customer_name"),
                    rs.getBigDecimal("total_price"),
                    OrderStatus.valueOf(rs.getString("status")),
                    rs.getTimestamp("created_at").toLocalDateTime()
                    // Caso adicionar novas colunas no banco, atualizar aqui
            );

    @Autowired
    public JdbcOrderRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Optional<Order> findById(Long id) {
        String sql = """
                SELECT id, cash_shift_id, table_number, customer_name, total_price, status, created_at
                FROM orders
                WHERE id = ?
                """;
        List<Order> result = jdbc.query(sql, ROW_MAPPER, id);
        return result.stream().findFirst();
    }

    @Override
    public List<Order> findAll() {
        String sql = """
                SELECT id, cash_shift_id, table_number, customer_name, total_price, status, created_at
                FROM orders
                """;
        return jdbc.query(sql, ROW_MAPPER);
    }

    @Override
    public Long save(Order order) {
        String sql = """
                INSERT INTO orders (cash_shift_id, table_number, customer_name, total_price, status, created_at)
                VALUES (?, ?, ?, ?, ?, ?)
                """;
        // Keyholder necessário para adição de itens em pedidos novos
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1 ,order.getCashShiftId());
            ps.setInt(2, order.getTableNumber());
            ps.setString(3, order.getCustomerName());
            ps.setBigDecimal(4, order.getTotalPrice());
            ps.setString(5, order.getStatus().name());
            ps.setTimestamp(6, Timestamp.valueOf(order.getCreatedAt()));
            return ps;
        }, keyHolder);
        return keyHolder.getKeyAs(Long.class);
    }

    // ************************
    //  Specific queries below
    // ************************

    @Override
    public List<Order> findAllPendingByShiftId(Long cashShiftId) {
        String sql = """
                SELECT id, cash_shift_id, table_number, customer_name, total_price, status, created_at
                FROM orders
                WHERE cash_shift_id = ?
                AND status IN ('PENDING')
                """;
        return jdbc.query(sql, ROW_MAPPER, cashShiftId);
    }

    @Override
    public List<Order> findAllInProgressByShiftId(Long cashShiftId) {
        String sql = """
                SELECT id, cash_shift_id, table_number, customer_name, total_price, status, created_at
                FROM orders
                WHERE cash_shift_id = ?
                AND status IN ('IN_PROGRESS')
                """;
        return jdbc.query(sql, ROW_MAPPER, cashShiftId);
    }

    @Override
    public List<Order> findAllServedByShiftId(Long cashShiftId) {
        String sql = """
                SELECT id, cash_shift_id, table_number, customer_name, total_price, status, created_at
                FROM orders
                WHERE cash_shift_id = ?
                AND status IN ('SERVED')
                """;
        return jdbc.query(sql, ROW_MAPPER, cashShiftId);
    }

    @Override
    public List<Order> findAllPaidByShiftId(Long cashShiftId) {
        String sql = """
                SELECT id, cash_shift_id, table_number, customer_name, total_price, status, created_at
                FROM orders
                WHERE cash_shift_id = ?
                AND status IN ('PAID')
                """;
        return jdbc.query(sql, ROW_MAPPER, cashShiftId);
    }

    @Override
    public List<Order> findAllCanceledByShiftId(Long cashShiftId) {
        String sql = """
                SELECT id, cash_shift_id, table_number, customer_name, total_price, status, created_at
                FROM orders
                WHERE cash_shift_id = ?
                AND status IN ('CANCELED')
                """;
        return jdbc.query(sql, ROW_MAPPER, cashShiftId);
    }

    @Override
    public int markAsInProgress(Long id) {
        String sql = """
                UPDATE orders
                SET status = 'IN_PROGRESS'
                WHERE id = ?
                """;
        return jdbc.update(sql, id);
    }

    @Override
    public int markAsServed(Long id) {
        String sql = """
                UPDATE orders
                SET status = 'SERVED'
                WHERE id = ?
                """;
        return jdbc.update(sql, id);
    }

    @Override
    public int markAsPaid(Long id) {
        String sql = """
                UPDATE orders
                SET status = 'PAID'
                WHERE id = ?
                """;
        return jdbc.update(sql, id);
    }

    @Override
    public int cancel(Long id) {
        String sql = """
                UPDATE orders
                SET status = 'CANCELED'
                WHERE id = ?
                """;
        return jdbc.update(sql, id);
    }
}