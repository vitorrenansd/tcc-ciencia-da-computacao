package com.tcc.serveme.api.repository;

import com.tcc.serveme.api.model.OrderItem;
import com.tcc.serveme.api.repository.mapper.OrderItemRowMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class OrderItemRepository {
    private final JdbcTemplate jdbc;
    private static final OrderItemRowMapper ROW_MAPPER = new OrderItemRowMapper();

    @Autowired
    public OrderItemRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public int save(OrderItem orderItem) {
        String sql = """
                INSERT INTO order_item (order_id, product_id, quantity, notes)
                VALUES (?, ?, ?, ?)
                """;
        return jdbc.update(sql, orderItem.getOrderId(), orderItem.getProductId(), orderItem.getQuantity(), orderItem.getNotes());
    }
    
    // ************************
    //  Specific queries below
    // ************************

    public List<OrderItem> findActiveOrderItemsByTableNumber(Integer tableNumber) {
        String sql = """
                SELECT 
                    oi.id,
                    oi.order_id,
                    oi.product_id,
                    oi.quantity,
                    oi.notes,
                    oi.canceled
                FROM order_item oi
                INNER JOIN orders o ON o.id = oi.order_id
                WHERE o.table_number = ?
                AND o.status IN ('PENDING', 'IN_PROGRESS')
                """;
        List<OrderItem> items = jdbc.query(sql, ROW_MAPPER, tableNumber);
        return items;
    }

    public List<OrderItem> findActiveOrderItemsByCustomerName(String customerName) {
        String sql = """
                SELECT
                    oi.id,
                    oi.order_id,
                    oi.product_id,
                    oi.quantity,
                    oi.notes,
                    oi.canceled
                FROM order_item oi
                INNER JOIN orders o ON o.id = oi.order_id
                WHERE o.customer_name LIKE ?
                AND o.status IN ('PENDING', 'IN_PROGRESS')
                """;
        String searchPattern = "%" + customerName + "%";
        List<OrderItem> items = jdbc.query(sql, ROW_MAPPER, searchPattern);
        return items;
    }

    public boolean cancel(Long id) {
        String sql = """
                UPDATE order_item
                SET canceled = TRUE
                WHERE id = ?
                """;
        int rows = jdbc.update(sql, id);
        return rows == 1;
    }
}