package com.skillswapplatform.user.service;

import com.skillswapplatform.user.model.User;
import com.skillswapplatform.user.model.UserRole;
import com.skillswapplatform.user.repository.UserRepository;
import com.skillswapplatform.web.dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(RegisterRequest registerRequest) {
        // Validate passwords match
        if (!registerRequest.getPassword().equals(registerRequest.getRepeatedPassword())) {
            throw new IllegalArgumentException("Passwords do not match!");
        }

        // Check if username already exists
        if (userRepository.getUserByUsername(registerRequest.getUsername()) != null) {
            throw new IllegalArgumentException("Username already exists!");
        }

        // Check if email already exists
        if (userRepository.getUserByEmail(registerRequest.getEmail()) != null) {
            throw new IllegalArgumentException("Email already in use.");
        }

        User user = User.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .email(registerRequest.getEmail())
                .firstName(registerRequest.getFirstName())
                .role(UserRole.USER)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .isDeleted(false)
                .build();

        userRepository.save(user);
    }
}
