package com.tcc.serveme.api.order.repository;

import com.tcc.serveme.api.order.entity.Order;
import com.tcc.serveme.api.order.entity.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    Optional<Order> findById(Long id);
    List<Order> findAll();
    Long save(Order order);
    int updateStatus(Long id, OrderStatus status);

    List<Order> findAllByShiftIdAndStatus(Long cashShiftId, OrderStatus status);
    List<Order> findAllByShiftId(Long cashShiftId);
    List<Order> findAllPendingByShiftId(Long cashShiftId);
    List<Order> findAllInProgressByShiftId(Long cashShiftId);
    List<Order> findAllServedByShiftId(Long cashShiftId);
    List<Order> findAllPaidByShiftId(Long cashShiftId);
    List<Order> findAllCanceledByShiftId(Long cashShiftId);
}