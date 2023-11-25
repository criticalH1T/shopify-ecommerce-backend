package com.ecommerce.backend.repositories;

import com.ecommerce.backend.entities.UserLoginInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserLoginInfoRepository extends CrudRepository<UserLoginInfo, Integer> {

    @Override
    List<UserLoginInfo> findAll();

}