package com.example.taskmanager.controller;

import com.example.taskmanager.dto.RegisterRequest;
import com.example.taskmanager.dto.UserResponse;
import com.example.taskmanager.entity.User;
import com.example.taskmanager.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterRequest request) {

        try {
            User user = userService.register(
                    request.getEmail(),
                    request.getPassword()
            );

            UserResponse response = new UserResponse(
                    user.getId(),
                    user.getEmail()
            );

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(response);

        } catch (IllegalArgumentException e) {

            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of(
                            "message",
                            e.getMessage()
                    ));
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(
            Authentication authentication) {

        return ResponseEntity.ok(
                Map.of(
                        "email",
                        authentication.getName()
                )
        );
    }
}