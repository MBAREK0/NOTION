package com.MBAREK0.web.controller;


import com.MBAREK0.web.entity.User;
import com.MBAREK0.web.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Test extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService userService = new UserService();

        List<User> users = userService.getAllUsers();


        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        users.forEach(user -> {
            out.println("<h1>");
            out.println("User ID: " + user.getId() + "<br>");
            out.println("User Email: " + user.getEmail() + "<br>");
        });


    }
}
