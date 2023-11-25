package com.ecommerce.backend.controllers;

import com.ecommerce.backend.dtos.OrderItemDto;
import com.ecommerce.backend.dtos.ProductDto;
import com.ecommerce.backend.entities.OrderItem;
import com.ecommerce.backend.entities.Product;
import com.ecommerce.backend.mappers.OrderItemMapper;
import com.ecommerce.backend.mappers.OrderItemMapperImpl;
import com.ecommerce.backend.repositories.OrderItemRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class OrderItemController {

    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    public OrderItemController(OrderItemRepository orderItemRepository, OrderItemMapperImpl orderItemMapper) {
        this.orderItemRepository = orderItemRepository;
        this.orderItemMapper = orderItemMapper;
    }

    @GetMapping("/order_items")
    public List<OrderItemDto> getAllProducts() {
        List<OrderItem> orderItemList = orderItemRepository.findAll();
        return orderItemList.stream()
                .map(orderItemMapper::toDto)
                .collect(Collectors.toList());
    }

}
