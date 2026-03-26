package com.tcc.serveme.api.order.service;

import com.tcc.serveme.api.exception.BadRequestException;
import com.tcc.serveme.api.exception.ConflictException;
import com.tcc.serveme.api.exception.NotFoundException;
import com.tcc.serveme.api.order.entity.OrderStatus;
import com.tcc.serveme.api.order.mapper.OrderItemMapper;
import com.tcc.serveme.api.order.mapper.OrderMapper;
import com.tcc.serveme.api.cashshift.entity.CashShift;
import com.tcc.serveme.api.order.dto.*;
import com.tcc.serveme.api.order.entity.Order;
import com.tcc.serveme.api.order.entity.OrderItem;
import com.tcc.serveme.api.product.entity.Product;
import com.tcc.serveme.api.cashshift.repository.CashShiftRepository;
import com.tcc.serveme.api.order.repository.OrderRepository;
import com.tcc.serveme.api.order.repository.OrderItemRepository;
import com.tcc.serveme.api.product.repository.ProductRepository;

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
    private final CashShiftRepository cashShiftRepo;

    @Autowired
    public OrderService(OrderRepository orderRepo, OrderItemRepository orderItemRepo, ProductRepository productRepo, CashShiftRepository cashShiftRepo) {
        this.orderRepo = orderRepo;
        this.orderItemRepo = orderItemRepo;
        this.productRepo = productRepo;
        this.cashShiftRepo = cashShiftRepo;
    }


    // Cria pedido novo no banco
    @Transactional
    public Long createOrder(NewOrderRequest request) {
        CashShift openShift = cashShiftRepo.findOpenShift()
                .orElseThrow(() -> new ConflictException("Sem caixa aberto, impossível criar pedidos."));

        BigDecimal total = BigDecimal.ZERO;
        List<Product> products = new ArrayList<>();

        // Busca e valida todos os produtos
        for (NewOrderItemRequest itemRequest : request.items()) {
            Product product = productRepo.findByIdActive(itemRequest.productId())
                    .orElseThrow(() -> new NotFoundException("Produto não encontrado ou inativo. ID: " + itemRequest.productId()));

            products.add(product);

            BigDecimal itemTotal = product.getPrice()
                    .multiply(BigDecimal.valueOf(itemRequest.quantity()));
            total = total.add(itemTotal);
        }

        // Criação do pedido em banco
        Order order = new Order(openShift.getId(), request.tableNumber(), request.customerName(), total);
        Long orderId = orderRepo.save(order);

        // Loop de criação dos itens no banco usando os produtos já buscados
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

    // Retorna os detalhes de um pedido (com itens) pelo ID
    public OrderDetailsResponse findDetailsById(Long id) {
        return orderRepo.findById(id)
                .map(order -> {
                    List<OrderItemDetailsResponse> items = orderItemRepo
                            .findByOrderId(id) // Busca os itens do pedido no repo
                            .stream()
                            .map(OrderItemMapper::toDetailsResponse) // Mapeia os itens para um DTO valido
                            .toList();
                    return OrderMapper.toDetailsResponse(order, items); // Retorna os dados do pedido + itens
                })
                .orElseThrow(() -> new NotFoundException("Pedido não encontrado. ID: " + id));
    }

    // Atualiza o status de um pedido
    public void updateOrderStatus(Long id, UpdateOrderStatusRequest request) {
        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Pedido não encontrado. ID: " + id));

        if (order.getStatus().isTerminal()) {
            throw new ConflictException("Pedido já está finalizado com status: " + order.getStatus().name());
        }

        orderRepo.updateStatus(id, request.status());
    }

    // Cancela um item individual de um pedido
    public void cancelOrderItem(Long orderId, Long itemId) {
        orderRepo.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Pedido não encontrado. ID: " + orderId));

        List<OrderItem> items = orderItemRepo.findByOrderId(orderId);
        OrderItem item = items.stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Item não encontrado. ID: " + itemId));

        if (item.isCanceled()) {
            throw new ConflictException("Item já está cancelado. ID: " + itemId);
        }

        orderItemRepo.cancel(itemId);
    }

    // Substitui os 5 métodos getPendingOrders, getOrdersInProgress, etc.
    public List<OrdersByStatusResponse> getOrdersByStatus(String statusParam) {
        OrderStatus status;
        try {
            status = OrderStatus.valueOf(statusParam.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Status inválido: " + statusParam);
        }

        CashShift openShift = cashShiftRepo.findOpenShift()
                .orElseThrow(() -> new ConflictException("Sem caixa aberto, impossível visualizar pedidos."));

        return orderRepo.findAllByShiftIdAndStatus(openShift.getId(), status)
                .stream()
                .map(OrderMapper::toOrdersByStatus)
                .toList();
    }

    public List<OrdersByStatusResponse> getAllOrders() {
        CashShift openShift = cashShiftRepo.findOpenShift()
                .orElseThrow(() -> new ConflictException("Sem caixa aberto, impossível visualizar pedidos."));

        return orderRepo.findAllByShiftId(openShift.getId())
                .stream()
                .map(OrderMapper::toOrdersByStatus)
                .toList();
    }
}