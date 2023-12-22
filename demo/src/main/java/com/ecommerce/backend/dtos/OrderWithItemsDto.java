package com.ecommerce.backend.dtos;

import com.ecommerce.backend.dtos.OrderItemDto;
import com.ecommerce.backend.dtos.UserDto;
import com.ecommerce.backend.entities.Order;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

/**
 * DTO for {@link Order}
 */
@Value
public class OrderWithItemsDto implements Serializable {
    Integer id;
    LocalDate orderDate;
    BigDecimal orderTotal;
    Set<OrderItemDto> orderItems;
    UserDto user;
}