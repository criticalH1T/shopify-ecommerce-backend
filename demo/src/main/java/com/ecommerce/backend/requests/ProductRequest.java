package com.ecommerce.backend.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("category")
    private Integer categoryId;
    private String description;
    @JsonProperty("productImage")
    private String imageUrl;
    private String name;
    private BigDecimal price;
    private int stockQuantity;
    private int volume;
}
