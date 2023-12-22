package com.ecommerce.backend.controllers;

import com.ecommerce.backend.dtos.OrderWithItemsDto;
import com.ecommerce.backend.entities.Order;
import com.ecommerce.backend.mappers.OrderMapper;
import com.ecommerce.backend.mappers.OrderWithItemsMapper;
import com.ecommerce.backend.repositories.OrderRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class OrderController {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    private final OrderWithItemsMapper orderWithItemsMapper;

    public OrderController(OrderRepository orderRepository,
                           OrderMapper orderMapper, OrderWithItemsMapper orderWithItemsMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.orderWithItemsMapper = orderWithItemsMapper;
    }

    @GetMapping("/orders")
    public List<OrderWithItemsDto> getAllOrders() {
        List<Order> ordersList = orderRepository.findAll();
        return ordersList.stream()
                .map(orderWithItemsMapper::toDto)
                .collect(Collectors.toList());
    }
}
