package com.ecommerce.backend.controllers;

import com.ecommerce.backend.dtos.ProductDto;
import com.ecommerce.backend.entities.Product;
import com.ecommerce.backend.mappers.ProductMapper;
import com.ecommerce.backend.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductController(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @GetMapping("/products")
    public List<ProductDto> getAllProducts() {
        List<Product> productList = productRepository.findAll();
        return productList.stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/products/{id}")
    public ProductDto findById(@PathVariable Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product with id " + id + " not found."));
        return productMapper.toDto(product);
    }

    @GetMapping(path = "{categoryName}/{productId}")
    public  ProductDto findProductByCategoryName(@PathVariable String categoryName, @PathVariable Integer productId) {
        Product product = productRepository.findByCategoryCategoryNameAndId(categoryName, productId)
                .orElseThrow(() -> new EntityNotFoundException("Product in category " + categoryName + " with id " + productId + " not found."));
        return productMapper.toDto(product);
    }
}