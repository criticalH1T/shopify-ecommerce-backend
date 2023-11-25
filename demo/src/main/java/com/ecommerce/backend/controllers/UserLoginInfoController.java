package com.ecommerce.backend.controllers;

import com.ecommerce.backend.dtos.UserLoginInfoDto;
import com.ecommerce.backend.entities.UserLoginInfo;
import com.ecommerce.backend.mappers.UserLoginInfoMapper;
import com.ecommerce.backend.repositories.UserLoginInfoRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserLoginInfoController {

    private final UserLoginInfoRepository userLoginInfoRepository;
    private final UserLoginInfoMapper userLoginInfoMapper;

    public UserLoginInfoController(UserLoginInfoRepository userLoginInfoRepository,
                                   UserLoginInfoMapper userLoginInfoMapper) {
        this.userLoginInfoRepository = userLoginInfoRepository;
        this.userLoginInfoMapper = userLoginInfoMapper;
    }

    @GetMapping("/user_login_info")
    public List<UserLoginInfoDto> getAllLoginInfo() {
        List<UserLoginInfo> loginInfoList = userLoginInfoRepository.findAll();
        return loginInfoList.stream()
                .map(userLoginInfoMapper::toDto)
                .collect(Collectors.toList());
    }
}
