package com.ecommerce.backend.repositories;

import com.ecommerce.backend.entities.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Integer> {


}