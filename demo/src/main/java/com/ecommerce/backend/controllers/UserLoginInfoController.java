package com.ecommerce.backend.controllers;

import com.ecommerce.backend.entities.UserLoginInfo;
import com.ecommerce.backend.repositories.UserLoginInfoRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserLoginInfoController {

    private final UserLoginInfoRepository userLoginInfoRepository;

    public UserLoginInfoController(UserLoginInfoRepository userLoginInfoRepository) {
        this.userLoginInfoRepository = userLoginInfoRepository;
    }

    @GetMapping("/user_login_info")
    public Iterable<UserLoginInfo> getAllLoginInfo() {
        return userLoginInfoRepository.findAll();
    }
}
