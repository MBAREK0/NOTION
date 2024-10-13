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

    public UserService(EntityManager entityManager) {
        this.userRepository = new UserRepositoryImpl(entityManager);
    }

    public User createUser(User user) {
       String password =  PasswordUtil.hashPassword(user.getPassword());
        user.setPassword(password);
        return userRepository.createUser(user);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.getUserById(id);
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
