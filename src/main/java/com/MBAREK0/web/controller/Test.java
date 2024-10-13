package com.MBAREK0.web.controller;


import com.MBAREK0.web.entity.*;

import com.MBAREK0.web.service.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;


public class Test extends HttpServlet {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public Test() {
        entityManagerFactory = Persistence.createEntityManagerFactory("default");
        entityManager = entityManagerFactory.createEntityManager();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {





    }

    private void createManager() {

        User user = new User();
        user.setEmail("a@a.com");
        user.setUsername("a");
        user.setPassword("password");
        user.setRole(UserRole.manager);
        user.setFirstName("a");
        user.setLastName("a");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        UserService userService = new UserService(entityManager);
        userService.createUser(user);

    }


}