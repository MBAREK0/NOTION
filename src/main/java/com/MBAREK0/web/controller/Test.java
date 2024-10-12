package com.MBAREK0.web.controller;


import com.MBAREK0.web.entity.*;

import com.MBAREK0.web.service.TagService;

import com.MBAREK0.web.service.TaskService;
import com.MBAREK0.web.service.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


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


}