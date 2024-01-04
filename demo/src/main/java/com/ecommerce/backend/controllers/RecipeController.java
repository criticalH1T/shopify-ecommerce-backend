package com.ecommerce.backend.controllers;

import com.ecommerce.backend.auth.GenericResponse;
import com.ecommerce.backend.dtos.RecipeDto;
import com.ecommerce.backend.entities.Product;
import com.ecommerce.backend.entities.Recipe;
import com.ecommerce.backend.exceptions.StatusCode;
import com.ecommerce.backend.mappers.RecipeMapper;
import com.ecommerce.backend.repositories.ProductRepository;
import com.ecommerce.backend.repositories.RecipeRepository;
import com.ecommerce.backend.requests.RecipeRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper;
    private final ProductRepository productRepository;

    public RecipeController(RecipeRepository recipeRepository, RecipeMapper recipeMapper,
            ProductRepository productRepository) {
        this.recipeRepository = recipeRepository;
        this.recipeMapper = recipeMapper;
        this.productRepository = productRepository;
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

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<GenericResponse> deleteRecipeById(@PathVariable Integer id) {
        try {
            Recipe recipe = recipeRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Recipe with id " + id + " not found."));

            recipeRepository.delete(recipe);
            GenericResponse response = GenericResponse.builder()
                    .responseMessage("Recipe with id " + id + " deleted successfully.")
                    .status(StatusCode.SUCCESS)
                    .build();
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            GenericResponse response = GenericResponse.builder()
                    .responseMessage("Recipe with id " + id + " not found.")
                    .status(StatusCode.NOT_FOUND)
                    .build();
            return ResponseEntity.status(404).body(response);
        } catch (Exception e) {
            GenericResponse response = GenericResponse.builder()
                    .responseMessage("Error deleting recipe with id: " + id)
                    .status(StatusCode.INTERNAL_SERVER_ERROR)
                    .build();
            return ResponseEntity.status(500).body(response);
        }
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<GenericResponse> updateRecipeById(@PathVariable Integer id,
            @RequestBody RecipeRequest updatedRecipe) {
        try {
            Recipe existingRecipe = recipeRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Recipe with id " + id + " not found."));

            Product product = null;
            if (!Objects.equals(updatedRecipe.getProductId(), null)) {
                product = productRepository.findById(updatedRecipe.getProductId())
                        .orElseThrow(() -> new EntityNotFoundException(
                                "Product with id " + updatedRecipe.getProductId() + "not found."));
            }

            existingRecipe.setDescription(updatedRecipe.getDescription());
            existingRecipe.setName(updatedRecipe.getName());
            existingRecipe.setImage_path(updatedRecipe.getImage_path());
            existingRecipe.setSteps(updatedRecipe.getSteps());
            existingRecipe.setIngredients(updatedRecipe.getIngredients());
            existingRecipe.setProduct(product);

            recipeRepository.save(existingRecipe);

            GenericResponse response = GenericResponse.builder()
                    .responseMessage("Recipe with id " + id + " updated successfully.")
                    .status(StatusCode.SUCCESS)
                    .build();
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            GenericResponse response = GenericResponse.builder()
                    .responseMessage("Recipe with id " + id + " not found.")
                    .status(StatusCode.NOT_FOUND)
                    .build();
            return ResponseEntity.status(404).body(response);
        } catch (Exception e) {
            GenericResponse response = GenericResponse.builder()
                    .responseMessage("Error updating recipe with id: " + id)
                    .status(StatusCode.INTERNAL_SERVER_ERROR)
                    .build();
            return ResponseEntity.status(500).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<GenericResponse> createRecipe(@RequestBody RecipeRequest createdRecipe) {
        try {
            Product product = productRepository.findById(createdRecipe.getProductId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Product with id " + createdRecipe.getProductId() + "not found."));

            Recipe newRecipe = Recipe.builder()
                    .description(createdRecipe.getDescription())
                    .name(createdRecipe.getName())
                    .image_path(createdRecipe.getImage_path())
                    .steps(createdRecipe.getSteps())
                    .ingredients(createdRecipe.getIngredients())
                    .product(product)
                    .build();

            recipeRepository.save(newRecipe);
            GenericResponse response = GenericResponse.builder()
                    .responseMessage("Recipe created successfully.")
                    .status(StatusCode.SUCCESS)
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            GenericResponse response = GenericResponse.builder()
                    .responseMessage("Error creating recipe")
                    .status(StatusCode.INTERNAL_SERVER_ERROR)
                    .build();
            return ResponseEntity.status(500).body(response);
        }
    }
}
