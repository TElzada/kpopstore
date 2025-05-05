package com.example.kpopstore.services;

import com.example.kpopstore.entities.User;
import com.example.kpopstore.entities.User.Role;
import com.example.kpopstore.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User getUserById(UUID userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new RuntimeException("User not found with id " + userId);
        }
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username " + username));
    }

    public User createUser(String username, String email, String password, Role role) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username is already taken");
        }
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email is already in use");
        }

        String hashedPassword = passwordEncoder.encode(password);

        User newUser = new User(username, email, hashedPassword, role);
        return userRepository.save(newUser);
    }

    public User registerUser(String username, String email, String password) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username is already taken");
        }
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email is already in use");
        }

        String hashedPassword = passwordEncoder.encode(password);

        User newUser = new User(username, email, hashedPassword, Role.USER);
        return userRepository.save(newUser);
    }

    public User updateUser(UUID userId, String email, String password, Role role) {
        User existingUser = getUserById(userId);
        existingUser.setEmail(email);
        existingUser.setPassword(password);
        existingUser.setRole(role);
        return userRepository.save(existingUser);
    }

    public void deleteUser(UUID userId) {
        User existingUser = getUserById(userId);
        userRepository.delete(existingUser);
    }

    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
