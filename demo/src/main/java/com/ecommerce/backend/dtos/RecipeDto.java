package com.ecommerce.backend.dtos;

import com.ecommerce.backend.entities.Recipe;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link Recipe}
 */
@Value
public class RecipeDto implements Serializable {
    Integer id;
    @NotNull
    ProductDto product;
    @NotNull
    String name;
    @NotNull
    String description;
    @NotNull
    List<String> ingredients;
    @NotNull
    List<String> steps;
    @NotNull
    String image_path;

}