package com.ecommerce.backend.controllers;

import com.ecommerce.backend.dtos.UserLoginInfoDto;
import com.ecommerce.backend.entities.UserLoginInfo;
import com.ecommerce.backend.mappers.UserLoginInfoMapper;
import com.ecommerce.backend.repositories.UserLoginInfoRepository;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user-login-info")
public class UserLoginInfoController {

    private final UserLoginInfoRepository userLoginInfoRepository;
    private final UserLoginInfoMapper userLoginInfoMapper;

    public UserLoginInfoController(UserLoginInfoRepository userLoginInfoRepository,
                                   UserLoginInfoMapper userLoginInfoMapper) {
        this.userLoginInfoRepository = userLoginInfoRepository;
        this.userLoginInfoMapper = userLoginInfoMapper;
    }

    @GetMapping
    public List<UserLoginInfoDto> getAllLoginInfo() {
        List<UserLoginInfo> loginInfoList = userLoginInfoRepository.findAll();
        return loginInfoList.stream()
                .map(userLoginInfoMapper::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/save-user")
    public ResponseEntity<String> saveUserLoginInfo(@RequestBody UserLoginInfo userLoginInfo) {
        try {
            userLoginInfoRepository.save(userLoginInfo);
            return new ResponseEntity<>("User login info saved.", HttpStatusCode.valueOf(200));
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getCause().toString(), HttpStatusCode.valueOf(500));
        }
    }
}
