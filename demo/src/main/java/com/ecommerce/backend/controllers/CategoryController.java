package com.ecommerce.backend.controllers;

import com.ecommerce.backend.dtos.CategoryDto;
import com.ecommerce.backend.mappers.CategoryMapper;
import com.ecommerce.backend.repositories.CategoryRepository;
import com.ecommerce.backend.entities.Category;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/")
public class CategoryController {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryController(CategoryRepository categoryRepository,
                              CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @GetMapping("/categories")
    public List<CategoryDto> getAllCategories() {
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList.stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/categories/{id}")
    public Optional<Category> findById(@PathVariable Integer id) { return categoryRepository.findById(id); }

    @GetMapping
    @RequestMapping
    public String hello_repository() {
        return "Hello Repository!";
    }
}
