package com.example.taskmanager.service;

import com.example.taskmanager.entity.User;
import com.example.taskmanager.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(String email, String password) {

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("このメールアドレスは既に登録されています");
        }

        User user = new User();

        user.setEmail(email);

        // 生のパスワードをそのままDBに保存しない
        user.setPassword(passwordEncoder.encode(password));

        return userRepository.save(user);
    }
}