package com.MBAREK0.web.controller;


import com.MBAREK0.web.entity.*;

import com.MBAREK0.web.repository.TaskRepository;
import com.MBAREK0.web.repository.implementation.TaskRepositoryImpl;
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
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;


public class Test extends HttpServlet {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private TaskService taskService;

    public Test() {
        entityManagerFactory = Persistence.createEntityManagerFactory("default");
        entityManager = entityManagerFactory.createEntityManager();
        taskService = new TaskService(entityManager);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        Optional<Task> task = taskService.getTaskById(147L);
        List<TaskModificationRequest> requests = task.get().getTaskModificationRequests();
        PrintWriter out = response.getWriter();
        out.println("Task: " + task.get().getTitle());
        out.println("Requests: ");
        for (TaskModificationRequest request1 : requests) {
            out.println("Request: " + request1.getId());
        }
//        Optional<User> user = new UserService(entityManager).getUserById(30L);
//
//        Optional<User> manager = new UserService(entityManager).getUserById(29L);
//
//        taskService.requestTaskModification(task.get(), user.get(), manager.get());
//
//        response.setContentType("text/html");
//        PrintWriter out = response.getWriter();
//        out.println("drdrdrdrdrdrdrdrdrdrdrdrdrdrdrdrdrdrdrdrdrdrdrd");



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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] selectedTags = req.getParameterValues("tags");
        String startDate = req.getParameter("startDate");
        String endDate = req.getParameter("endDate");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        PrintWriter out = resp.getWriter();
        out.println("Start Date: " + startDate);
        out.println("End Date: " + endDate);
        out.println("------------------- ");

        try {
            LocalDate pstartDate = LocalDate.parse(startDate, formatter);
            LocalDate pendDate = LocalDate.parse(endDate, formatter);
            out.println("Start Date: " + pstartDate);
            out.println("End Date: " + pendDate);

        } catch (DateTimeParseException e) {
            out.println("Invalid date format");
        }
    }
}