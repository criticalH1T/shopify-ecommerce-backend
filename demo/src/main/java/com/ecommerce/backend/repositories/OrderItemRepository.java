package com.ecommerce.backend.repositories;

import com.ecommerce.backend.entities.OrderItem;
import com.ecommerce.backend.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderItemRepository extends CrudRepository<OrderItem, Integer> {

    @Override
    List<OrderItem> findAll();

}