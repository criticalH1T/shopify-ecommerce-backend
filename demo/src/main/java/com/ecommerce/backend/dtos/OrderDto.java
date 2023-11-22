package com.ecommerce.backend.dtos;

import com.ecommerce.backend.entities.Order;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for {@link Order}
 */
@Value
public class OrderDto implements Serializable {
    Integer id;
    LocalDate orderDate;
    BigDecimal orderTotal;
}