package com.ecommerce.backend.requests;

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
    private String image_path;
    private List<String> ingredients;
    private Product product;
    private List<String> steps;
}
