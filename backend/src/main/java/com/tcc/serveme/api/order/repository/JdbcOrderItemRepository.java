package com.tcc.serveme.api.order.repository;

import com.tcc.serveme.api.order.entity.OrderItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class JdbcOrderItemRepository implements OrderItemRepository {
    private final JdbcTemplate jdbc;
    private static final RowMapper<OrderItem> ROW_MAPPER =
            (rs, rowNum) -> new OrderItem(
                    rs.getLong("id"),
                    rs.getLong("order_id"),
                    rs.getLong("product_id"),
                    rs.getString("product_name"),
                    rs.getBigDecimal("product_price"),
                    rs.getInt("quantity"),
                    rs.getString("notes"),
                    rs.getBoolean("canceled")
                    // Caso adicionar novas colunas no banco, atualizar aqui
            );

    @Autowired
    public JdbcOrderItemRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }


    @Override
    public int save(OrderItem orderItem) {
        String sql = """
                INSERT INTO order_item (order_id, product_id, product_name, product_price, quantity, notes)
                VALUES (?, ?, UPPER(?), ?, ?, UPPER(?))
                """;
        return jdbc.update(sql,
                orderItem.getOrderId(),
                orderItem.getProductId(),
                orderItem.getProductName(),
                orderItem.getProductPrice(),
                orderItem.getQuantity(),
                orderItem.getNotes()
        );
    }

    @Override
    public boolean hasActiveItems(Long orderId) {
        String sql = """
            SELECT COUNT(*) FROM order_item
            WHERE order_id = ?
            AND canceled = FALSE
            """;
        Integer count = jdbc.queryForObject(sql, Integer.class, orderId);
        return count != null && count > 0;
    }

    // ************************
    //  Specific queries below
    // ************************

    @Override
    public List<OrderItem> findByOrderId(Long orderId) {
        String sql = """
        SELECT
            id,
            order_id,
            product_id,
            product_name,
            product_price,
            quantity,
            notes,
            canceled
        FROM order_item
        WHERE order_id = ?
        """;

        return jdbc.query(sql, ROW_MAPPER, orderId);
    }

    @Override
    public List<OrderItem> findActiveOrderItemsByTableNumber(Integer tableNumber) {
        String sql = """
                SELECT
                    oi.id,
                    oi.order_id,
                    oi.product_id,
                    oi.product_name,
                    oi.product_price,
                    oi.quantity,
                    oi.notes,
                    oi.canceled
                FROM order_item oi
                INNER JOIN orders o ON o.id = oi.order_id
                WHERE o.table_number = ?
                AND o.status IN ('PENDING', 'IN_PROGRESS', 'SERVED')
                """;
        return jdbc.query(sql, ROW_MAPPER, tableNumber);
    }

    @Override
    public int cancel(Long id) {
        String sql = """
                UPDATE order_item
                SET canceled = TRUE
                WHERE id = ?
                """;
        return jdbc.update(sql, id);
    }
}