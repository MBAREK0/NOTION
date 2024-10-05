package com.MBAREK0.web.controller;


import com.MBAREK0.web.entity.User;
import com.MBAREK0.web.entity.UserOrManager;
import com.MBAREK0.web.util.PasswordUtil;
import com.MBAREK0.web.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class Test extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService userService = new UserService();

        User user = new User();
        user.setUsername("m");
        user.setEmail("m@m.com");
        user.setPassword("mmmm");
        user.setFirstName("M");
        user.setLastName("M");
        user.setRole(UserOrManager.manager);
        user.setCreatedAt(java.time.LocalDateTime.now());
        user.setUpdatedAt(java.time.LocalDateTime.now());



        userService.createUser(user);
        if (user.getId() != null) {
            PrintWriter out = response.getWriter();
            out.println("User created successfully");
        } else {
            PrintWriter out = response.getWriter();
            out.println("User creation failed");
        }

    }
}
