package com.example.taskmanager.controller;

import com.example.taskmanager.dto.RegisterRequest;
import com.example.taskmanager.dto.UserResponse;
import com.example.taskmanager.entity.User;
import com.example.taskmanager.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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
            @RequestBody RegisterRequest request
    ) {
        try {
            User user = userService.register(
                    request.getEmail(),
                    request.getPassword()
            );

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(
                            new UserResponse(
                                    user.getId(),
                                    user.getEmail()
                            )
                    );

        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of(
                            "message",
                            e.getMessage()
                    ));
        }
    }

    @PostMapping("/guest")
    public ResponseEntity<?> guest(
            HttpServletRequest request
    ) {
        User user = userService.createGuest();

        loginUser(user.getEmail(), request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of(
                        "email", user.getEmail(),
                        "guest", true
                ));
    }

    @PostMapping("/upgrade")
    public ResponseEntity<?> upgrade(
            @RequestBody RegisterRequest request,
            Authentication authentication,
            HttpServletRequest httpRequest
    ) {
        try {
            User user = userService.upgradeGuest(
                    authentication.getName(),
                    request.getEmail(),
                    request.getPassword()
            );

            loginUser(user.getEmail(), httpRequest);

            return ResponseEntity.ok(
                    Map.of(
                            "email", user.getEmail(),
                            "guest", false
                    )
            );

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
            Authentication authentication
    ) {
        User user = userService.getByEmail(
                authentication.getName()
        );

        return ResponseEntity.ok(
                Map.of(
                        "email", user.getEmail(),
                        "guest", user.isGuest()
                )
        );
    }

    private void loginUser(
            String email,
            HttpServletRequest request
    ) {
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        email,
                        null,
                        List.of(
                                new SimpleGrantedAuthority("ROLE_USER")
                        )
                );

        SecurityContext context =
                SecurityContextHolder.createEmptyContext();

        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);

        request.getSession(true).setAttribute(
                HttpSessionSecurityContextRepository
                        .SPRING_SECURITY_CONTEXT_KEY,
                context
        );
    }
}