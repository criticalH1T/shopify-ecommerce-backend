package com.ecommerce.backend.controllers;

import com.ecommerce.backend.auth.GenericResponse;
import com.ecommerce.backend.exceptions.StatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping()
    public ResponseEntity<GenericResponse> isAdmin() {
        GenericResponse response = GenericResponse.builder()
                .responseMessage("Access allowed.")
                .status(StatusCode.SUCCESS)
                .build();
        return ResponseEntity.ok(response);
    }
}
