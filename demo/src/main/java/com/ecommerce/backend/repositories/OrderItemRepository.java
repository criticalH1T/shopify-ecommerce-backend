package com.ecommerce.backend.repositories;

import com.ecommerce.backend.entities.OrderItem;
import org.springframework.data.repository.CrudRepository;

public interface OrderItemRepository extends CrudRepository<OrderItem, Integer> {
}