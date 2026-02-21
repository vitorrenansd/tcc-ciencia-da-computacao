package com.tcc.serveme.api.repository;

import com.tcc.serveme.api.dto.order.OrderItemDetailsResponse;
import com.tcc.serveme.api.model.OrderItem;
import com.tcc.serveme.api.repository.mapper.OrderItemResponseRowMapper;
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
                INSERT INTO order_item (order_id, product_id, product_name, product_price, quantity, notes)
                VALUES (?, ?, ?, ?, ?, ?)
                """;
        return jdbc.update(sql, orderItem.getOrderId(), orderItem.getProductId(), orderItem.getProductName(), orderItem.getProductPrice(), orderItem.getQuantity(), orderItem.getNotes());
    }
    
    // ************************
    //  Specific queries below
    // ************************

    public List<OrderItemDetailsResponse> findDetailedByOrderId(Long orderId) {
        String sql = """
        SELECT
            oi.id,
            oi.product_id,
            p.name AS product_name,
            p.price AS product_price,
            oi.quantity,
            oi.notes,
            oi.canceled
        FROM order_item oi
        JOIN product p ON p.id = oi.product_id
        WHERE oi.order_id = ?
        """;

        return jdbc.query(sql, new OrderItemResponseRowMapper(), orderId);
    }

    public List<OrderItem> findActiveOrderItemsByTableNumber(Integer tableNumber) {
        String sql = """
                SELECT
                    oi.id,
                    oi.order_id,
                    oi.product_id,
                    oi.product_name
                    oi.product_price
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

    public int cancel(Long id) {
        String sql = """
                UPDATE order_item
                SET canceled = TRUE
                WHERE id = ?
                """;
        return jdbc.update(sql, id);
    }
}