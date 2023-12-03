package com.ecommerce.backend.repositories;

import com.ecommerce.backend.entities.Recipe;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RecipeRepository extends CrudRepository<Recipe, Integer> {

    @Override
    List<Recipe> findAll();

}
