package com.ecommerce.backend.dtos;

import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link com.ecommerce.backend.entities.OrderItem}
 */
@Value
public class OrderItemDto implements Serializable {
    Integer id;
    OrderDto order;
    Integer quantity;
    BigDecimal subtotal;
    ProductDto product;
}