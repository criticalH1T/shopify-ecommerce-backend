package com.ecommerce.backend.repositories;

import com.ecommerce.backend.entities.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Integer> {

    @Override
    List<Order> findAll();
}