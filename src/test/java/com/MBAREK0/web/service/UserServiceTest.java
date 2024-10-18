package com.MBAREK0.web.service;

import com.MBAREK0.web.entity.User;
import com.MBAREK0.web.entity.UserRole;

import com.MBAREK0.web.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void UserService_getUserById_returnsUser() {
        User user = new User();
        Long userId = 1L;
        String email = "test@test.com";
        String password = "password";
        user.setId(userId);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(UserRole.user);
        user.setCreatedAt(java.time.LocalDateTime.now());
        user.setUpdatedAt(java.time.LocalDateTime.now());
        user.setUsername("test");
        user.setFirstName("test");
        user.setLastName("test");


        //When
        when(userRepository.getUserById(userId)).thenReturn(Optional.of(user));
        Optional<User> result = userService.getUserById(userId);

        //Then
        assertTrue(result.isPresent());
        assertEquals(userId, result.get().getId());
        verify(userRepository).getUserById(userId);

    }

    @Test
    void UserService_getUserById_returnsEmptyOptionalWhenUserNotFound() {
        Long userId = 2L;

        // When
        when(userRepository.getUserById(userId)).thenReturn(Optional.empty());
        Optional<User> result = userService.getUserById(userId);

        // Then
        assertFalse(result.isPresent());
        verify(userRepository).getUserById(userId);
    }

    @Test
    void UserService_getUserByEmail_returnsUser() {
        User user = new User();
        Long userId = 1L;
        String email = "test@test.com";
        String password = "password";
        user.setId(userId);
        user.setEmail(email);
        user.setPassword(password);

        //When
        when(userRepository.getUserByEmail(email)).thenReturn(Optional.of(user));
        Optional<User> result = userService.getUserByEmail(email);

        //Then
        assertTrue(result.isPresent());
        assertEquals(email, result.get().getEmail());
        verify(userRepository).getUserByEmail(email);

    }

    @Test
    void UserService_getUserByEmail_returnsEmptyOptionalWhenUserNotFound() {

        String email = "test@test.com";

        // When
        when(userRepository.getUserByEmail(email)).thenReturn(Optional.empty());
        Optional<User> result = userService.getUserByEmail(email);

        // Then
        assertFalse(result.isPresent());
        verify(userRepository).getUserByEmail(email);
    }

    @Test
    void UserService_createUser_returnsUser() {
        User user = new User();
        Long userId = 1L;
        String email = "test@test.com";
        String password = "password";
        user.setId(userId);
        user.setEmail(email);
        user.setPassword(password);

        //When
        when(userRepository.createUser(user)).thenReturn(user);
        User result = userService.createUser(user);

        //Then
        assertEquals(userId, result.getId());
        assertEquals(email, result.getEmail());
        verify(userRepository).createUser(user);
        assertNotEquals(password, result.getPassword());

    }

    @Test
    void UserService_updateUser_returnsUser() {
        User user = new User();
        Long userId = 1L;
        String email = "test@test.com";
        String password = "password";
        user.setId(userId);
        user.setEmail(email);
        user.setPassword(password);

        //When
        when(userRepository.updateUser(user)).thenReturn(user);
        User result = userService.updateUser(user);

        //Then
        assertEquals(userId, result.getId());
        assertEquals(email, result.getEmail());
        verify(userRepository).updateUser(user);

    }

    @Test
    void UserService_deleteUser_returnsUser() {
        User user = new User();
        Long userId = 1L;
        String email = "test@test.com";
        String password = "password";
        user.setId(userId);
        user.setEmail(email);
        user.setPassword(password);

        //When
        when(userRepository.deleteUser(userId)).thenReturn(user);
        User result = userService.deleteUser(userId);

        //Then
        assertEquals(userId, result.getId());
        assertEquals(email, result.getEmail());
        verify(userRepository).deleteUser(userId);

    }

    @Test
    void UserService_getAllUsers_returnsListOfUsers() {
        User user = new User();
        Long userId = 1L;
        String email = "test@test.com";
        String password = "password";
        user.setId(userId);
        user.setEmail(email);
        user.setPassword(password);

        //When
        when(userRepository.getAllUsers()).thenReturn(List.of(user));
        List<User> result = userService.getAllUsers();

        //Then
        assertEquals(1, result.size());
        assertEquals(userId, result.get(0).getId());
        assertEquals(email, result.get(0).getEmail());
        verify(userRepository).getAllUsers();

    }

    @Test
    void UserService_getUsersByRole_returnsListOfUsers() {
        User user = new User();
        Long userId = 1L;
        String email = "test@test.com";
        String password = "password";
        user.setId(userId);
        user.setEmail(email);
        user.setPassword(password);

        UserRole role = UserRole.user;

        //When
        when(userRepository.getUsersByRole(role)).thenReturn(List.of(user));
        List<User> result = userService.getUsersByRole(role);

        //Then
        assertEquals(1, result.size());
        assertEquals(userId, result.get(0).getId());
        assertEquals(email, result.get(0).getEmail());
        verify(userRepository).getUsersByRole(role);


    }

//    getUsersWithEligibleDoubleTokens
    @Test
    void UserService_getUsersWithEligibleDoubleTokens_returnsListOfUsers() {
        User user = new User();
        Long userId = 1L;
        String email = "test@test.com";
        String password = "password";
        user.setId(userId);
        user.setEmail(email);
        user.setPassword(password);
        user.setEligibleForDoubleTokens(1);

        //When
        when(userRepository.getUsersWithEligibleDoubleTokens()).thenReturn(List.of(user));
        List<User> result = userService.getUsersWithEligibleDoubleTokens();

        //Then
        assertEquals(1, result.size());
        assertEquals(userId, result.get(0).getId());
        assertEquals(email, result.get(0).getEmail());

        verify(userRepository).getUsersWithEligibleDoubleTokens();
    }
}