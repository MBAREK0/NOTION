package com.MBAREK0.web.controller;

import com.MBAREK0.web.config.PersistenceManager;
import com.MBAREK0.web.entity.*;
import com.MBAREK0.web.service.InboxService;
import com.MBAREK0.web.service.TaskService;
import com.MBAREK0.web.service.UserService;
import com.MBAREK0.web.util.ResponseHandler;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class InboxController extends HttpServlet {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private InboxService inboxService;
    private TaskService taskService;
    private UserService userService;

    public InboxController() {
        entityManagerFactory = Persistence.createEntityManagerFactory("default");
        entityManager = PersistenceManager.getEntityManager();
        inboxService = new InboxService(entityManager);
        taskService = new TaskService(entityManager);
        userService = new UserService(entityManager);
    }


    @Override
    public void init() throws ServletException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("list".equals(action)) {
            listInbox(req, resp);
        } else if ("create_request".equals(action)) {
            createRequest(req, resp);
        }else if ("accept".equals(action)) {
            acceptRequest(req, resp);
        } else {
            listInbox(req, resp);
        }
    }

    private void listInbox(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User user = (User) req.getSession().getAttribute("user");
        List<Inbox> inboxes = inboxService.getAllInboxByUserId(user.getId());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm");

        req.setAttribute("formatter", formatter);
        req.setAttribute("inboxes", inboxes);
        req.getRequestDispatcher("/WEB-INF/views/inboxes/inboxList.jsp").forward(req, resp);
    }

    private void createRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String strId = req.getParameter("id");

        Long id = 0L;

        if (strId != null) id = Long.parseLong(strId);
        else ResponseHandler.handleError(req, resp,"tasks", "Task id is required");


        Optional<Task> opTask = taskService.getTaskById(id);
        if (opTask.isEmpty()) ResponseHandler.handleError(req, resp,"tasks", "Task not found");

        Task task = opTask.get();
        User user = task.getUser();
        User manager = task.getManager();
        Token token = user.getToken();

        if (token.getModifyTokenCount() == 0) {
            ResponseHandler.handleError(req, resp,"tasks", "You don't have enough modify token");
            return;
        }

        Inbox inbox = new Inbox(task, user, manager);


        if (task.isChanged() == true) {
            ResponseHandler.handleError(req, resp,"tasks", "Task already changed");
            return;
        }

        inbox = inboxService.createInbox(inbox);

        if (inbox.getId() != null) {

            token.setModifyTokenCount(token.getModifyTokenCount() - 1);
            user.setToken(token);
            userService.updateUser(user);
            req.getSession().setAttribute("user", user);
            ResponseHandler.handleSuccess(req, resp, "tasks", "Request sent successfully");
        }
        else ResponseHandler.handleError(req, resp, "tasks", "Request failed");

    }

    private void acceptRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String strId = req.getParameter("id");

        Long id = 0L;
        if (strId != null) id = Long.parseLong(strId);
        else ResponseHandler.handleError(req, resp, "inbox", "Inbox id is required");


        Optional<Inbox> opInbox = inboxService.getInboxById(id);
        if (opInbox.isEmpty()) ResponseHandler.handleError(req, resp, "inbox", "Inbox not found");

        Inbox inbox = opInbox.get();
        User user = inbox.getUser();
        Task task = inbox.getTask();

        List<User> users = userService.getUsersByRole(UserOrManager.user);
        users = users.stream().filter(u -> u != user).toList();

        req.setAttribute("users", users);
        req.setAttribute("task", task);
        req.setAttribute("inbox", inbox);
        req.getRequestDispatcher("/WEB-INF/views/inboxes/changeAssignedUser.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("accept_request".equals(action)) {
            acceptPostRequest(req, resp);
        }else {
            listInbox(req, resp);
        }
    }

    private void acceptPostRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String strTaskId = req.getParameter("task_id");
        String strUserId = req.getParameter("user_id");
        String strInboxId = req.getParameter("inbox_id");

        if (strTaskId == null || strUserId == null || strInboxId == null || strTaskId.isEmpty() || strUserId.isEmpty() || strInboxId.isEmpty()) {
            ResponseHandler.handleError(req, resp, "inbox", "Task and User id are required");
            return;
        }

        Long taskId = Long.parseLong(strTaskId);
        Long userId = Long.parseLong(strUserId);

        Optional<Task> opTask = taskService.getTaskById(taskId);
        if (opTask.isEmpty()) {
            ResponseHandler.handleError(req, resp, "inbox", "Task not found");
            return;
        }
        Task task = opTask.get();

        Optional<User> opUser = userService.getUserById(userId);
        if (opUser.isEmpty()) {
            ResponseHandler.handleError(req, resp, "inbox", "User not found");
            return;
        }
        User user = opUser.get();

        if (user.getId() == task.getUser().getId()) {
            ResponseHandler.handleError(req, resp, "inbox", "Task already assigned to this user");
            return;
        }
        task.setUser(user);
        task.setChanged(true);

        task = taskService.updateTask(task);

        if (task.getId() != null) {
            Long inboxId = Long.parseLong(strInboxId);
           if (inboxService.deleteInbox(inboxId) != null) ResponseHandler.handleSuccess(req, resp, "tasks", "Task assigned successfully");
           else ResponseHandler.handleError(req, resp, "inbox", "Task assignment failed");
        }  else ResponseHandler.handleError(req, resp, "inbox", "Task assignment failed");


    }

    @Override
    public void destroy() {
        PersistenceManager.close();
    }

}
