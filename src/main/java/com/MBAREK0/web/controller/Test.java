package com.MBAREK0.web.controller;


import com.MBAREK0.web.entity.User;
import com.MBAREK0.web.myUtil.PasswordUtil;
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

        long id = Long.parseLong("4");
        User user = userService.getUserById(id).get();


        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<h1>");
        if(PasswordUtil.checkPassword("mbarek", user.getPassword())){
            out.println("Password is correct");
        }else {
            out.println("Password is incorrect");
        }
        out.println("</h1>");


    }
}
