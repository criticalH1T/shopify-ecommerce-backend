package com.ecommerce.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_login_info", indexes = {
        @Index(name = "email", columnList = "email", unique = true)
})
@Builder
@AllArgsConstructor
public class UserLoginInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_login_id", nullable = false)
    private Integer user_login_info_id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToOne(mappedBy = "userLoginInfo")
    private User user;

    public UserLoginInfo() {

    }
}