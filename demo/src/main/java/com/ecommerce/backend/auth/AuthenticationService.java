package com.ecommerce.backend.auth;

import com.ecommerce.backend.entities.User;
import com.ecommerce.backend.entities.UserLoginInfo;
import com.ecommerce.backend.exceptions.StatusCode;
import com.ecommerce.backend.repositories.UserLoginInfoRepository;
import com.ecommerce.backend.repositories.UserRepository;
import com.ecommerce.backend.services.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserLoginInfoRepository userLoginInfoRepository;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public RegisterResponse register(RegisterRequest request) {
        UserLoginInfo userLoginInfo = UserLoginInfo.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .address(request.getAddress())
                .userLoginInfo(userLoginInfo)
                .role("USER")
                .build();
        userLoginInfoRepository.save(userLoginInfo);
        userRepository.save(user);
        return RegisterResponse.builder()
                .statusCode(StatusCode.SUCCESS)
                .responseMessage("Registration successful.")
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request,
                                               HttpServletResponse response) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        UserLoginInfo userLoginInfo = userLoginInfoRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
        Map<String, Integer> extraClaims = new HashMap<>();
        Integer userId = userLoginInfo.getUser().getUserId();
        extraClaims.put("userId", userId);
        var jwtToken = jwtService.generateToken(extraClaims, userLoginInfo);
        Cookie cookie = new Cookie("jwt", jwtToken);
        cookie.setMaxAge(3600); // 1 hour
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        return AuthenticationResponse.builder()
                .statusCode(StatusCode.SUCCESS)
                .responseMessage("Authentication successful.")
                .build();
    }
}
