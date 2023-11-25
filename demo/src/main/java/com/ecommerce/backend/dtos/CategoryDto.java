package com.ecommerce.backend.dtos;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.ecommerce.backend.entities.Category}
 */
@Value
public class CategoryDto implements Serializable {
    Integer id;
    String categoryName;
}