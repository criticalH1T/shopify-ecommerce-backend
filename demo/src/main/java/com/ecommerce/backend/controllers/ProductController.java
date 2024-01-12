package com.ecommerce.backend.controllers;

import com.ecommerce.backend.auth.GenericResponse;
import com.ecommerce.backend.dtos.ProductDto;
import com.ecommerce.backend.entities.Category;
import com.ecommerce.backend.entities.Product;
import com.ecommerce.backend.exceptions.StatusCode;
import com.ecommerce.backend.mappers.ProductMapper;
import com.ecommerce.backend.repositories.CategoryRepository;
import com.ecommerce.backend.repositories.ProductRepository;
import com.ecommerce.backend.requests.ProductRequest;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    public ProductController(ProductRepository productRepository, ProductMapper productMapper,
            CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public List<ProductDto> getAllProducts() {
        List<Product> productList = productRepository.findAll();
        return productList.stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public ProductDto findById(@PathVariable Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product with id " + id + " not found."));
        return productMapper.toDto(product);
    }

    @GetMapping(path = "{categoryName}/{productId}")
    public ProductDto findProductByCategoryName(@PathVariable String categoryName, @PathVariable Integer productId) {
        Product product = productRepository.findByCategoryCategoryNameAndId(categoryName, productId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Product in category " + categoryName + " with id " + productId + " not found."));
        return productMapper.toDto(product);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<GenericResponse> deleteProductById(@PathVariable Integer id) {
        try {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Product with id " + id + " not found."));
            productRepository.delete(product);
            GenericResponse response = GenericResponse.builder()
                    .responseMessage("Product with id " + id + " deleted successfully.")
                    .status(StatusCode.SUCCESS)
                    .build();
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            GenericResponse response = GenericResponse.builder()
                    .responseMessage("Product with id " + id + " not found.")
                    .status(StatusCode.NOT_FOUND)
                    .build();
            return ResponseEntity.status(404).body(response);
        } catch (Exception e) {
            GenericResponse response = GenericResponse.builder()
                    .responseMessage("Error deleting product with id: " + id)
                    .status(StatusCode.INTERNAL_SERVER_ERROR)
                    .build();
            return ResponseEntity.status(500).body(response);
        }
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<GenericResponse> updateProductById(@PathVariable Integer id,
            @RequestBody ProductRequest updatedProduct) {

        try {
            Product existingProduct = productRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Product with id " + id + "not found."));

            Category category = categoryRepository.findById(updatedProduct.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Category with id " + updatedProduct.getCategoryId() + "not found."));

            existingProduct.setCategory(category);
            existingProduct.setDescription(updatedProduct.getDescription());
            existingProduct.setImageUrl(updatedProduct.getImageUrl());
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setPrice(updatedProduct.getPrice());
            existingProduct.setStockQuantity(updatedProduct.getStockQuantity());
            existingProduct.setVolume(updatedProduct.getVolume());

            productRepository.save(existingProduct);

            GenericResponse response = GenericResponse.builder()
                    .responseMessage("Product with id " + id + " updated successfully.")
                    .status(StatusCode.SUCCESS)
                    .build();
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            GenericResponse response = GenericResponse.builder()
                    .responseMessage("Product with id " + id + " not found.")
                    .status(StatusCode.NOT_FOUND)
                    .build();
            return ResponseEntity.status(404).body(response);
        } catch (Exception e) {
            GenericResponse response = GenericResponse.builder()
                    .responseMessage("Error updating product with id: " + id)
                    .status(StatusCode.INTERNAL_SERVER_ERROR)
                    .build();
            return ResponseEntity.status(500).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<GenericResponse> createProduct(@RequestBody ProductRequest createdProduct) {
        try {
            Category category = categoryRepository.findById(createdProduct.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Category with id " + createdProduct.getCategoryId() + "not found."));

            Product newProduct = Product.builder()
                    .category(category)
                    .description(createdProduct.getDescription())
                    .imageUrl(createdProduct.getImageUrl())
                    .name(createdProduct.getName())
                    .price(createdProduct.getPrice())
                    .stockQuantity(createdProduct.getStockQuantity())
                    .volume(createdProduct.getVolume())
                    .build();

            productRepository.save(newProduct);

            GenericResponse response = GenericResponse.builder()
                    .responseMessage("Product created successfully.")
                    .status(StatusCode.SUCCESS)
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            GenericResponse response = GenericResponse.builder()
                    .responseMessage("Error creating product")
                    .status(StatusCode.INTERNAL_SERVER_ERROR)
                    .build();
            return ResponseEntity.status(500).body(response);
        }
    }
}