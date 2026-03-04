package com.tcc.serveme.api.service;

import com.tcc.serveme.api.dto.order.*;
import com.tcc.serveme.api.mapper.OrderItemMapper;
import com.tcc.serveme.api.mapper.OrderMapper;
import com.tcc.serveme.api.model.CashShift;
import com.tcc.serveme.api.model.Order;
import com.tcc.serveme.api.model.OrderItem;
import com.tcc.serveme.api.model.Product;
import com.tcc.serveme.api.repository.CashShiftRepository;
import com.tcc.serveme.api.repository.OrderRepository;
import com.tcc.serveme.api.repository.OrderItemRepository;

import com.tcc.serveme.api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "Sem caixa aberto, impossível criar pedidos."));

        BigDecimal total = BigDecimal.ZERO;
        List<Product> products = new ArrayList<>();

        // Busca e valida todos os produtos
        for (NewOrderItemRequest itemRequest : request.items()) {
            Product product = productRepo.findByIdActive(itemRequest.productId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado ou inativo. ID: " + itemRequest.productId()));

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

    // Retorna um List com todos os pedidos de status PENDING
    public List<OrdersByStatusResponse> getPendingOrders() {
        CashShift openShift = cashShiftRepo.findOpenShift()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "Sem caixa aberto, impossível visualizar pedidos pendentes."));
        return orderRepo.findAllPendingByShiftId(openShift.getId())
                .stream()
                .map(OrderMapper::toOrdersByStatus) // Mapeia o retorno do repo para um DTO valido
                .toList();
    }

    public List<OrdersByStatusResponse> getOrdersInProgress() {
        CashShift openShift = cashShiftRepo.findOpenShift()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "Sem caixa aberto, impossível visualizar pedidos em progresso."));
        return orderRepo.findAllInProgressByShiftId(openShift.getId())
                .stream()
                .map(OrderMapper::toOrdersByStatus) // Mapeia o retorno do repo para um DTO valido
                .toList();
    }

    public List<OrdersByStatusResponse> getServedOrders() {
        CashShift openShift = cashShiftRepo.findOpenShift()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "Sem caixa aberto, impossível visualizar pedidos servidos."));
        return orderRepo.findAllServedByShiftId(openShift.getId())
                .stream()
                .map(OrderMapper::toOrdersByStatus) // Mapeia o retorno do repo para um DTO valido
                .toList();
    }

    public List<OrdersByStatusResponse> getPaidOrders() {
        CashShift openShift = cashShiftRepo.findOpenShift()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "Sem caixa aberto, impossível visualizar pedidos pagos."));
        return orderRepo.findAllPaidByShiftId(openShift.getId())
                .stream()
                .map(OrderMapper::toOrdersByStatus) // Mapeia o retorno do repo para um DTO valido
                .toList();
    }

    public List<OrdersByStatusResponse> getCanceledOrders() {
        CashShift openShift = cashShiftRepo.findOpenShift()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "Sem caixa aberto, impossível visualizar pedidos cancelados."));
        return orderRepo.findAllCanceledByShiftId(openShift.getId())
                .stream()
                .map(OrderMapper::toOrdersByStatus) // Mapeia o retorno do repo para um DTO valido
                .toList();
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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado. ID: " + id));
    }
}