package com.ecommerce.backend.controllers;

import com.ecommerce.backend.dtos.OrderDto;
import com.ecommerce.backend.entities.Order;
import com.ecommerce.backend.mappers.OrderMapper;
import com.ecommerce.backend.repositories.OrderRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class OrderController {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderController(OrderRepository orderRepository,
                           OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @GetMapping("/orders")
    public List<OrderDto> getAllOrders() {
        List<Order> ordersList = orderRepository.findAll();
        return ordersList.stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }
}
