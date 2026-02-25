package com.tcc.serveme.api.service;

import com.tcc.serveme.api.dto.order.*;
import com.tcc.serveme.api.mapper.OrderMapper;
import com.tcc.serveme.api.model.Order;
import com.tcc.serveme.api.model.OrderItem;
import com.tcc.serveme.api.model.Product;
import com.tcc.serveme.api.repository.OrderRepository;
import com.tcc.serveme.api.repository.OrderItemRepository;

import com.tcc.serveme.api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepo;
    private final OrderItemRepository orderItemRepo;
    private final ProductRepository productRepo;

    @Autowired
    public OrderService(OrderRepository orderRepo, OrderItemRepository orderItemRepo, ProductRepository productRepo) {
        this.orderRepo = orderRepo;
        this.orderItemRepo = orderItemRepo;
        this.productRepo = productRepo;
    }

    @Transactional
    public Long createOrder(NewOrderRequest request) {
        BigDecimal total = BigDecimal.ZERO;
        List<Product> products = new ArrayList<>();

        // Busca e valida todos os produtos
        for (NewOrderItemRequest itemRequest : request.items()) {
            Product product = productRepo.findByIdActive(itemRequest.productId())
                    .orElseThrow(() -> new RuntimeException("Product not found or inactive. ID: " + itemRequest.productId()));

            products.add(product);

            BigDecimal itemTotal = product.getPrice()
                    .multiply(BigDecimal.valueOf(itemRequest.quantity()));
            total = total.add(itemTotal);
        }

        // Criacao do pedido em banco
        Order order = new Order(request.tableNumber(), request.customerName(), total);
        Long orderId = orderRepo.save(order);

        // Loop de criacao dos itens no banco usando os produtos ja buscados
        for (int i = 0; i < request.items().size(); i++) {
            NewOrderItemRequest itemRequest = request.items().get(i);
            Product product = products.get(i);

            OrderItem item = new OrderItem(
                    orderId,
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    itemRequest.quantity(),
                    itemRequest.notes()
            );
            orderItemRepo.save(item);
        }
        return orderId;
    }

    public List<PendingOrdersResponse> getPendingOrders() {
        return orderRepo.findAllPendingOrders()
                .stream()
                .map(OrderMapper::toResponse)
                .toList();
    }

    public OrderDetailsResponse findDetailsById(Long id) {
        // SE order existir, executa o map
        // SE NAO existir, retorna null
        return orderRepo.findById(id)
                .map(order -> {
                    List<OrderItemDetailsResponse> items = orderItemRepo.findDetailedByOrderId(id);
                    return OrderMapper.toResponse(order, items);
                })
                .orElse(null);
    }
}