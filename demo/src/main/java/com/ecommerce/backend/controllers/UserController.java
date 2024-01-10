package com.ecommerce.backend.controllers;

import com.ecommerce.backend.auth.GenericResponse;
import com.ecommerce.backend.dtos.UserDto;
import com.ecommerce.backend.entities.User;
import com.ecommerce.backend.exceptions.StatusCode;
import com.ecommerce.backend.mappers.UserMapper;
import com.ecommerce.backend.repositories.UserRepository;
import com.ecommerce.backend.services.JwtService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final JwtService jwtService;

    public UserController(UserRepository userRepository, UserMapper userMapper, JwtService jwtService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.jwtService = jwtService;
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        List<User> userList = userRepository.findAll(Sort.by(Sort.Direction.ASC, "userId"));
        return userList.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<GenericResponse> SwitchUserRole(@PathVariable Integer userId,
                                                          @CookieValue(name = "jwt") String jwt) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found."));
        Integer jwtUserId = Integer.valueOf(jwtService.extractUserId(jwt));
        // if active admin is trying to change his role to a user
        if (Objects.equals(jwtUserId, user.getUserId())) {
            GenericResponse response = GenericResponse.builder()
                    .responseMessage("You cannot change your role to USER as" +
                            " doing this would remove your access to the admin dashboard")
                    .status(StatusCode.CONFLICT)
                    .build();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
        if (user.getRole().equals("ADMIN")) {
            user.setRole("USER");
        } else user.setRole("ADMIN");
        userRepository.save(user);
        GenericResponse response = GenericResponse.builder()
                .responseMessage("User role updated.")
                .status(StatusCode.SUCCESS)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<GenericResponse> DeleteUser(@PathVariable Integer userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            return ResponseEntity.ok(GenericResponse.builder()
                    .responseMessage("User deleted.")
                    .status(StatusCode.SUCCESS)
                    .build());
        }
        else return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(GenericResponse.builder()
                        .responseMessage("User not found.")
                        .status(StatusCode.NOT_FOUND)
                .build());
    }
}
