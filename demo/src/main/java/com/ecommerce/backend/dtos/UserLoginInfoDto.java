package com.ecommerce.backend.dtos;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.ecommerce.backend.entities.UserLoginInfo}
 */
@Value
public class UserLoginInfoDto implements Serializable {
    Integer user_login_info_id;
    String email;
    String password;
    UserDto user;
}