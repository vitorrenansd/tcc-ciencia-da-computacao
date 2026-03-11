package com.tcc.serveme.api.repository;

import com.tcc.serveme.api.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    Optional<Order> findById(Long id);
    List<Order> findAll();
    Long save(Order order);

    List<Order> findAllPendingByShiftId(Long cashShiftId);
    List<Order> findAllInProgressByShiftId(Long cashShiftId);
    List<Order> findAllServedByShiftId(Long cashShiftId);
    List<Order> findAllPaidByShiftId(Long cashShiftId);
    List<Order> findAllCanceledByShiftId(Long cashShiftId);
    int markAsInProgress(Long id);
    int markAsServed(Long id);
    int markAsPaid(Long id);
    int cancel(Long id);
}