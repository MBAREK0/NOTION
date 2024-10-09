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

        // creat new user
//        User user = new User();
//        user.setUsername("mohamed");
//        user.setPassword("mm");
//        user.setFirstName("mohamed");
//        user.setLastName("mohamed");
//        user.setEmail("l@l.com");
//        user.setRole(UserOrManager.manager);
//        user.setCreatedAt(LocalDateTime.now());
//        user.setUpdatedAt(LocalDateTime.now());
//
//        // save
//       UserService userService = new UserService(entityManager);
//        userService.createUser(user);
//
//        response.getWriter().println("User saved successfully.");


   }

   public void checkTaskCrud(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
       try {
           entityManager.getTransaction().begin();  // Ensure transaction starts

           // Get the user
           UserService userService = new UserService(entityManager); // Pass the entity manager
           User user = userService.getUserByEmail("m@m.com").get();

           TagService tagService = new TagService(entityManager); // Pass the entity manager

           // Create a new task
           Task task = new Task();
           task.setTitle("Complete the project szz");
           task.setDescription("Complete the project on timssse");
           task.setStatus(TaskStatus.completed);
           task.setUser(user);
           task.setManager(user);
              task.setStartDate(LocalDate.now());
              task.setEndDate(LocalDate.now().plusDays(5));

           task.setChanged(false);
           task.setCreatedAt(LocalDateTime.now());
           task.setUpdatedAt(LocalDateTime.now());

           // Get tags and add them to the task
           Tag importantTag = tagService.getTagById(33).get();
           Tag workTag = tagService.getTagById(27).get();

           task.addTag(importantTag);
           task.addTag(workTag);

           // Persist the task
           entityManager.persist(task);

           // Commit the transaction
           entityManager.getTransaction().commit();

           response.getWriter().println("Task and tags saved successfully.");
       } catch (Exception e) {
           if (entityManager.getTransaction().isActive()) {
               entityManager.getTransaction().rollback();
           }
           e.printStackTrace();
           response.getWriter().println("Error saving task: " + e.getMessage());
       } finally {
           entityManager.close();
       }

   }

}