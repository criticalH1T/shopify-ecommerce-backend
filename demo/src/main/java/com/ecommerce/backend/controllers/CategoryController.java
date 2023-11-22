package com.ecommerce.backend.controllers;

import com.ecommerce.backend.repositories.CategoryRepository;
import com.ecommerce.backend.entities.Category;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(path = "/")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/categories")
    public Iterable<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @GetMapping(path = "/categories/{id}")
    public Optional<Category> findById(@PathVariable Integer id) { return categoryRepository.findById(id); }

    @GetMapping
    @RequestMapping
    public String hello_repository() {
        return "Hello Repository!";
    }
}
