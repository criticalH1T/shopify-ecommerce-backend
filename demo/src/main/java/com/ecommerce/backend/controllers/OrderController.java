package com.ecommerce.backend.controllers;

import com.ecommerce.backend.dtos.OrderWithItemsDto;
import com.ecommerce.backend.entities.Order;
import com.ecommerce.backend.mappers.OrderMapper;
import com.ecommerce.backend.mappers.OrderWithItemsMapper;
import com.ecommerce.backend.repositories.OrderRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository orderRepository;
    private final OrderWithItemsMapper orderWithItemsMapper;

    public OrderController(OrderRepository orderRepository,
                           OrderMapper orderMapper, OrderWithItemsMapper orderWithItemsMapper) {
        this.orderRepository = orderRepository;
        this.orderWithItemsMapper = orderWithItemsMapper;
    }

    @GetMapping
    public List<OrderWithItemsDto> getAllOrders() {
        List<Order> ordersList = orderRepository.findAll();
        return ordersList.stream()
                .map(orderWithItemsMapper::toDto)
                .collect(Collectors.toList());
    }
}
