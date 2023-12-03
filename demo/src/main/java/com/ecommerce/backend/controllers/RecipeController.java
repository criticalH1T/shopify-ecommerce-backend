package com.ecommerce.backend.controllers;

import com.ecommerce.backend.dtos.RecipeDto;
import com.ecommerce.backend.entities.Recipe;
import com.ecommerce.backend.mappers.RecipeMapper;
import com.ecommerce.backend.repositories.RecipeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeRepository recipeRepository;

    private final RecipeMapper recipeMapper;

    public RecipeController(RecipeRepository recipeRepository, RecipeMapper recipeMapper) {
        this.recipeRepository = recipeRepository;
        this.recipeMapper = recipeMapper;
    }

    @GetMapping
    public List<RecipeDto> getAllRecipes() {
        List<Recipe> recipes = recipeRepository.findAll();
        return recipes.stream()
                .map(recipeMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public RecipeDto getRecipeById(@PathVariable Integer id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recipe with id " + id + " not found."));
        return recipeMapper.toDto(recipe);
    }
}
