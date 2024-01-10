package com.ecommerce.backend.controllers;

import com.ecommerce.backend.auth.GenericResponse;
import com.ecommerce.backend.exceptions.StatusCode;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cookies")
public class CookieController {
    @DeleteMapping("/deleteJwt")
    public ResponseEntity<GenericResponse> deleteJwt(HttpServletResponse response) {
        Cookie cookieToDelete = new Cookie("jwt", null);
        cookieToDelete.setMaxAge(0);
        cookieToDelete.setPath("/");
        cookieToDelete.setHttpOnly(true);
        response.addCookie(cookieToDelete);
        GenericResponse responseBody = GenericResponse.builder()
                .responseMessage("Jwt removed successfully.")
                .status(StatusCode.SUCCESS)
                .build();
        return ResponseEntity.ok(responseBody);
    }
}
