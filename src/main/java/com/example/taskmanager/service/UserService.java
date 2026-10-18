package com.example.taskmanager.service;

import com.example.taskmanager.entity.User;
import com.example.taskmanager.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(String email, String password) {

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException(
                    "このメールアドレスはすでに登録されています"
            );
        }

        User user = new User();

        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setGuest(false);

        return userRepository.save(user);
    }

    public User createGuest() {

        String guestId = UUID.randomUUID().toString();

        User user = new User();

        user.setEmail(
                "guest_" + guestId + "@guest.local"
        );

        user.setPassword(
                passwordEncoder.encode(UUID.randomUUID().toString())
        );

        user.setGuest(true);

        return userRepository.save(user);
    }

    public User upgradeGuest(
            String currentEmail,
            String newEmail,
            String newPassword
    ) {

        User user = getByEmail(currentEmail);

        if (!user.isGuest()) {
            throw new IllegalArgumentException(
                    "ゲストユーザーではありません"
            );
        }

        if (userRepository.existsByEmail(newEmail)) {
            throw new IllegalArgumentException(
                    "このメールアドレスはすでに登録されています"
            );
        }

        user.setEmail(newEmail);
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setGuest(false);

        return userRepository.save(user);
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "ユーザーが見つかりません"
                        )
                );
    }
}