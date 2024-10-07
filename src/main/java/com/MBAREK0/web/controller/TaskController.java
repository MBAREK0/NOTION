package com.MBAREK0.web.controller;

import com.MBAREK0.web.entity.Tag;
import com.MBAREK0.web.entity.Task;
import com.MBAREK0.web.entity.User;
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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class TaskController extends HttpServlet {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private TaskService taskService;
    private TagService tagService;
    private UserService userService;

    public TaskController() {
        entityManagerFactory = Persistence.createEntityManagerFactory("default");
        entityManager = entityManagerFactory.createEntityManager();
        taskService = new TaskService(entityManager);
        tagService = new TagService(entityManager);
        userService = new UserService(entityManager);
    }
    @Override
    public void init() throws ServletException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("create".equals(action)) {
            showCreateForm(req, resp);
        } else if ("edit".equals(action)) {
            showEditForm(req, resp);
        } else if ("delete".equals(action)) {
            deleteTask(req, resp);
        }else if ("details".equals(action)) {
            taskDetails(req, resp);
        }else {
            listTasks(req, resp);
        }

    }

    private void listTasks(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Task> tasks = taskService.getAllTasks();
        req.setAttribute("tasks", tasks);
        req.getRequestDispatcher("/WEB-INF/views/tasks/taskList.jsp").forward(req, resp);
    }

    private void taskDetails(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        Task task = taskService.getTaskById(id);
        LocalDate start_date = task.getStartDate();
        LocalDate end_date = task.getEndDate();

        // Use DateTimeFormatter for LocalDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        String formattedStartDate = start_date.format(formatter);
        String formattedEndDate = end_date.format(formatter);

        req.setAttribute("formattedStartDate", formattedStartDate);
        req.setAttribute("formattedEndDate", formattedEndDate);

        req.setAttribute("task", task);
        req.getRequestDispatcher("/WEB-INF/views/tasks/taskDetails.jsp").forward(req, resp);
    }

    private void showCreateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Tag> tags = tagService.gatAllTags();
        req.setAttribute("tags", tags);
        List<User> users = userService.getAllUsers();
        req.setAttribute("users", users);

        req.getRequestDispatcher("/WEB-INF/views/tasks/createForm.jsp").forward(req, resp);
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Tag> tags = tagService.gatAllTags();
        req.setAttribute("tags", tags);
        req.getRequestDispatcher("/WEB-INF/views/tasks/editForm.jsp").forward(req, resp);
    }

    private void deleteTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("create".equals(action)) {
            createTask(req, resp);
        } else if ("edit".equals(action)) {
            updateTask(req, resp);
        } else {
            listTasks(req, resp);
        }

    }

    private void createTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    private void updateTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

}
