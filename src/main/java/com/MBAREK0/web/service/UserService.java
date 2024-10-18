package com.MBAREK0.web.service;

import com.MBAREK0.web.entity.User;
import com.MBAREK0.web.entity.UserRole;
import com.MBAREK0.web.repository.UserRepository;
import com.MBAREK0.web.repository.implementation.UserRepositoryImpl;
import com.MBAREK0.web.util.PasswordUtil;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User createUser(User user) {
        Optional<User> optionalUser = getUserByEmail(user.getEmail());
        optionalUser.ifPresent(value -> {
            throw new IllegalArgumentException("User with this email already exists");
        });
        optionalUser = getUserByUsername(user.getUsername());
        optionalUser.ifPresent(value -> {
            throw new IllegalArgumentException("User with this username already exists");
        });

        String password =  PasswordUtil.hashPassword(user.getPassword());
        user.setPassword(password);
        return userRepository.createUser(user);
    }

    public Optional<User> getUserById(Long id)
    {
        if (id == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        return userRepository.getUserById(id);

    }

    public Optional<User> getUserByUsername(String username) {
        if (username == null) {
            throw new IllegalArgumentException("Username cannot be null");
        }
        if (username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (username.length() < 3 || username.length() > 20) {  // Adjust based on your requirements
            throw new IllegalArgumentException("Username must be between 3 and 20 characters long");
        }

        return userRepository.getUserByUsername(username);
    }


    public Optional<User> getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public List<User> getUsersByRole(UserRole role) {
        return userRepository.getUsersByRole(role);
    }

    public User updateUser(User user) {
        return userRepository.updateUser(user);
    }

    public User deleteUser(Long id) {
        return userRepository.deleteUser(id);
    }

    public List<User> getUsersWithEligibleDoubleTokens() {
        return userRepository.getUsersWithEligibleDoubleTokens();
    }
}
