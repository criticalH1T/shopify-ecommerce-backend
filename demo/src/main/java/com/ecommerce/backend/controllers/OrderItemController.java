package com.ecommerce.backend.controllers;

import com.ecommerce.backend.dtos.OrderItemDto;
import com.ecommerce.backend.entities.*;
import com.ecommerce.backend.mappers.OrderItemMapper;
import com.ecommerce.backend.mappers.OrderItemMapperImpl;
import com.ecommerce.backend.repositories.OrderItemRepository;
import com.ecommerce.backend.repositories.OrderRepository;
import com.ecommerce.backend.repositories.ProductRepository;
import com.ecommerce.backend.repositories.UserLoginInfoRepository;
import com.ecommerce.backend.requests.OrderRequest;
import com.ecommerce.backend.services.CookieService;
import com.ecommerce.backend.services.JwtService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class OrderItemController {

    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;
    private final JwtService jwtService;
    private final UserLoginInfoRepository userLoginInfoRepository;
    private final CookieService cookieService;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public OrderItemController(OrderItemRepository orderItemRepository, OrderItemMapperImpl orderItemMapper, JwtService jwtService, UserLoginInfoRepository userLoginInfoRepository, CookieService cookieService, ProductRepository productRepository, OrderRepository orderRepository) {
        this.orderItemRepository = orderItemRepository;
        this.orderItemMapper = orderItemMapper;
        this.jwtService = jwtService;
        this.userLoginInfoRepository = userLoginInfoRepository;
        this.cookieService = cookieService;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/order_items")
    public List<OrderItemDto> getAllProducts() {
        List<OrderItem> orderItemList = orderItemRepository.findAll();
        return orderItemList.stream()
                .map(orderItemMapper::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/order_items")
    public void addOrder(@RequestBody List<OrderRequest> orderRequest, HttpServletRequest request) {
        String jwt = cookieService.getJwtFromCookie(request);
        String userEmail = jwtService.extractUsername(jwt);
        UserLoginInfo userLoginInfo = userLoginInfoRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
        User user = userLoginInfo.getUser();
        BigDecimal orderTotal = orderRequest.stream()
                .map(OrderRequest::getOrderQuantitySubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = Order.builder()
                .user(user)
                .orderDate(LocalDate.now())
                .orderTotal(orderTotal)
                .build();

        orderRepository.save(order);

        for (OrderRequest orderItemRequest: orderRequest) {
            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(productRepository.findById(orderItemRequest.getProductId())
                            .orElseThrow(() -> new EntityNotFoundException("Entity not found.")))
                    .quantity(orderItemRequest.getOrderedQuantity())
                    .subtotal(orderItemRequest.getOrderQuantitySubtotal())
                    .build();

            orderItemRepository.save(orderItem);
        }
    }
}
