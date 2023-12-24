package com.ecommerce.backend.repositories;

import com.ecommerce.backend.entities.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {

    @Override
    List<User> findAll();

    List<User> findAll(Sort sort);
}