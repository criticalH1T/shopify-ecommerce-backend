package com.ecommerce.backend.controllers;

import com.ecommerce.backend.dtos.CategoryDto;
import com.ecommerce.backend.mappers.CategoryMapper;
import com.ecommerce.backend.repositories.CategoryRepository;
import com.ecommerce.backend.entities.Category;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/categories")
public class CategoryController {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryController(CategoryRepository categoryRepository,
                              CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @GetMapping
    public List<CategoryDto> getAllCategories() {
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList.stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public CategoryDto findById(@PathVariable Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category with id " + id + " not found."));
        return categoryMapper.toDto(category);
    }
}
