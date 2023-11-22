package com.ecommerce.backend.dtos;

import com.ecommerce.backend.entities.Product;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link Product}
 */
@Value
public class ProductDto implements Serializable {
    Integer id;
    @NotNull
    @Size(max = 30)
    String name;
    @NotNull
    @Size(max = 255)
    String description;
    @NotNull
    BigDecimal price;
    @NotNull
    Integer stockQuantity;
    @NotNull
    Integer volume;
    String imageUrl;
}