package com.ecommerce.backend.repositories;

import org.springframework.data.repository.CrudRepository;
import com.ecommerce.backend.entities.ContactUs;

import java.util.List;
import java.util.Optional;

public interface ContactUsRepository extends CrudRepository<ContactUs, Integer> {
    @Override
    List<ContactUs> findAll();
    Optional<ContactUs> findById(Integer id);
}
