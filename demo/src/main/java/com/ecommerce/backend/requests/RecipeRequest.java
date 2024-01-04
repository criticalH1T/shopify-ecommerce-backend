package com.ecommerce.backend.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.ecommerce.backend.entities.Product;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecipeRequest {
    private String description;
    private String name;
    @JsonProperty("recipeImage")
    private String image_path;
    private List<String> ingredients;
    @JsonProperty("product")
    private Integer productId;
    private List<String> steps;
}
