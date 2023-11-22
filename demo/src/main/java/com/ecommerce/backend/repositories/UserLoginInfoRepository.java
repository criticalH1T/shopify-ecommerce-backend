package com.ecommerce.backend.repositories;

import com.ecommerce.backend.entities.UserLoginInfo;
import org.springframework.data.repository.CrudRepository;

public interface UserLoginInfoRepository extends CrudRepository<UserLoginInfo, Integer> {
}