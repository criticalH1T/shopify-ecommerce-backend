package com.ecommerce.backend.dtos;

import com.ecommerce.backend.entities.User;
import lombok.Value;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link User}
 */
@Value
public class UserDto implements Serializable {
    Integer userId;
    String firstName;
    String lastName;
    String address;
    String role;
    Set<OrderDto> orders;
}