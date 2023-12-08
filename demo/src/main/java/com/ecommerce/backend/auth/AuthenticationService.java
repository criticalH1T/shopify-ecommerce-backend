package com.ecommerce.backend.auth;

import com.ecommerce.backend.entities.User;
import com.ecommerce.backend.entities.UserLoginInfo;
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
                .build();
        userLoginInfoRepository.save(userLoginInfo);
        userRepository.save(user);
        return RegisterResponse.builder()
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
        var user = userLoginInfoRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
        var jwtToken = jwtService.generateToken(user);
        Cookie cookie = new Cookie("jwt", jwtToken);
        cookie.setMaxAge(3 * 24 * 60 * 60); // 3 days
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        return AuthenticationResponse.builder()
                .responseMessage("Authentication successful.")
                .build();
    }
}
