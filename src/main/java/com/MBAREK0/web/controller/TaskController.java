package com.MBAREK0.web.controller;

import com.MBAREK0.web.entity.Tag;
import com.MBAREK0.web.entity.Task;
import com.MBAREK0.web.entity.TaskStatus;
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
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
        List<Task> managerTasks = tasks.stream().filter(task -> task.getManager().getId() != task.getUser().getId()).toList();
        List<Task> userTasks = tasks.stream().filter(task -> task.getManager().getId() == task.getUser().getId()).toList();
        req.setAttribute("managerTasks", managerTasks);
        req.setAttribute("userTasks", userTasks);
        req.setAttribute("tasks", tasks);
        req.getRequestDispatcher("/WEB-INF/views/tasks/taskList.jsp").forward(req, resp);
    }

    private void taskDetails(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        Optional<Task> opTask = taskService.getTaskById(id);
        if(opTask.isEmpty()){
            req.setAttribute("message", "Task not found!");
            req.getRequestDispatcher(req.getContextPath() + "/tasks").forward(req, resp);
            return;
        }
        Task task = opTask.get();
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

        Long id = Long.parseLong(req.getParameter("id"));
        Optional<Task> opTask =  taskService.getTaskById(id);
        if(opTask.isEmpty()){
            req.setAttribute("message", "Task not found!");
            req.getRequestDispatcher(req.getContextPath() + "/tasks").forward(req, resp);
            return;
        }
        Task task = opTask.get();

        List<Tag> tags = tagService.gatAllTags();
        req.setAttribute("tags", tags);
        List<User> users = userService.getAllUsers();
        req.setAttribute("users", users);
        req.setAttribute("task", task);

        req.getRequestDispatcher("/WEB-INF/views/tasks/editForm.jsp").forward(req, resp);

}

    private void deleteTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        if (taskService.deleteTask(id) != null) {
            req.setAttribute("message", "Task is deleted!");
        } else {
            req.setAttribute("message", "Task is not deleted!");
        }
        resp.sendRedirect(req.getContextPath() + "/tasks");
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
        HttpSession session = req.getSession();

        String title = req.getParameter("title");
        if (title == null || title.isEmpty()) {
            req.setAttribute("message", "Title is required!");
            req.getRequestDispatcher("/WEB-INF/views/tasks/createForm.jsp").forward(req, resp);
            return;
        }
        String description = req.getParameter("description");
        if (description == null || description.isEmpty()) {
            req.setAttribute("message", "Description is required!");
            req.getRequestDispatcher("/WEB-INF/views/tasks/createForm.jsp").forward(req, resp);
            return;
        }
        String startDate = req.getParameter("startDate");
        if (startDate == null || startDate.isEmpty()) {
            req.setAttribute("message", "Start Date is required!");
            req.getRequestDispatcher("/WEB-INF/views/tasks/createForm.jsp").forward(req, resp);
            return;
        }
        String endDate = req.getParameter("endDate");
        if (endDate == null || endDate.isEmpty()) {
            req.setAttribute("message", "End Date is required!");
            req.getRequestDispatcher("/WEB-INF/views/tasks/createForm.jsp").forward(req, resp);
            return;
        }
        Long userId = Long.parseLong(req.getParameter("userId"));
        if (userId == null) {
            req.setAttribute("message", "Please select a user!");
            req.getRequestDispatcher("/WEB-INF/views/tasks/createForm.jsp").forward(req, resp);
            return;
        }
        Optional<User> user = userService.getUserById(userId);

        if (user.isEmpty()) {
            req.setAttribute("message", "User not found!");
            req.getRequestDispatcher("/WEB-INF/views/tasks/createForm.jsp").forward(req, resp);
            return;
        }

        User manager = (User) session.getAttribute("user");

        String[] tags = req.getParameterValues("tags");
        List<String> tagsList = null;
        if (tags != null) {
            tagsList = Arrays.asList(tags);
            // Convert to ArrayList
            tagsList = new ArrayList<>(tagsList);

        } else {
            req.setAttribute("message", "Please select at least one tag!");
            req.getRequestDispatcher("/WEB-INF/views/tasks/createForm.jsp").forward(req, resp);
        }
        List<Tag> tagObjList = tagService.getTagsByIds(tagsList);
        if (tagObjList.isEmpty()) {
            req.setAttribute("message", "Please select at least one tag!");
            req.getRequestDispatcher("/WEB-INF/views/tasks/createForm.jsp").forward(req, resp);
        }


        Task task = new Task(title, description, TaskStatus.pending, LocalDate.parse(startDate), LocalDate.parse(endDate), user.get() , manager);
        task.setTags(new HashSet<>(tagObjList));


        Task t = taskService.createTask(task);

        if(t.getId() != null){
            req.setAttribute("message", "Task is created successfully!");
            resp.sendRedirect(req.getContextPath() + "/tasks");
        }else{
            req.setAttribute("message", "Failed to create task!");
            req.getRequestDispatcher(req.getContextPath() + "/tasks?action=create").forward(req, resp);
        }

    }

    private void updateTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

}
