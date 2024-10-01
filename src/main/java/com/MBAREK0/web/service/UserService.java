package com.MBAREK0.web.service;

import com.MBAREK0.web.entity.User;
import com.MBAREK0.web.repository.UserRepository;
import com.MBAREK0.web.repository.UserRepositoryImpl;

import java.util.List;
import java.util.Optional;

public class UserService {

    private UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepositoryImpl();
    }

    public User createUser(User user) {
        return userRepository.createUser(user);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.getUserById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public User updateUser(User user) {
        return userRepository.updateUser(user);
    }

    public User deleteUser(Long id) {
        return userRepository.deleteUser(id);
    }
}
