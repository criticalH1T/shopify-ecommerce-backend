package com.ecommerce.backend.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private int categoryId;
    private String description;
    private String imageUrl;
    private String name;
    private BigDecimal price;
    private int stockQuantity;
    private int volume;
}
