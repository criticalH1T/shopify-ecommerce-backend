package com.ecommerce.backend.repositories;

import com.ecommerce.backend.entities.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, Integer> {

    @Override
    List<Product> findAll();
    Optional <Product> findByCategoryCategoryNameAndId(String categoryName, Integer productId);
}