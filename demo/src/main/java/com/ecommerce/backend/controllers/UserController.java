package com.ecommerce.backend.controllers;

import com.ecommerce.backend.dtos.UserDto;
import com.ecommerce.backend.entities.User;
import com.ecommerce.backend.mappers.UserMapper;
import com.ecommerce.backend.repositories.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class UserController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserController(UserRepository userRepository,
                          UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @GetMapping("/users")
    public List<UserDto> getAllUsers() {
        List<User> userList = userRepository.findAll();
        return userList.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }
}
