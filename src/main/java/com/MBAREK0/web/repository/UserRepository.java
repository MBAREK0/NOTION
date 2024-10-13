package com.MBAREK0.web.repository;

import com.MBAREK0.web.entity.User;
import com.MBAREK0.web.entity.UserRole;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
     User createUser(User user);
     Optional<User> getUserById(Long id);
     Optional<User> getUserByEmail(String email);
     List<User> getAllUsers();

     List<User> getUsersWithEligibleDoubleTokens();

     User updateUser(User user);
     User deleteUser(Long id);
     List<User> getUsersByRole(UserRole role);
}
