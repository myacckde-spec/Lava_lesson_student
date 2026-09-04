package com.itproger.itshop.service;

import com.itproger.itshop.entity.User;
import com.itproger.itshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(String username, String password, String email) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Пользователь с таким логином уже существует");
        }
        
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setRole(User.Role.USER);
        
        userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User updateUser(Long id, String username, String email, String password, User.Role role) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));

        // Проверяем, что логин не занят другим пользователем
        if (!user.getUsername().equals(username) && userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Логин уже занят");
        }

        user.setUsername(username);
        user.setEmail(email);
        user.setRole(role);

        if (password != null && !password.isEmpty()) {
            user.setPassword(passwordEncoder.encode(password));
        }

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
}
