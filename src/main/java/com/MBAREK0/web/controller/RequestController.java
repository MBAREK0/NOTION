package com.MBAREK0.web.controller;

import com.MBAREK0.web.config.PersistenceManager;
import com.MBAREK0.web.entity.*;
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
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class RequestController extends HttpServlet {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private TaskService taskService;
    private UserService userService;

    public RequestController() {
        entityManagerFactory = Persistence.createEntityManagerFactory("default");
        entityManager = PersistenceManager.getEntityManager();
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
        } else if ("accept".equals(action)) {
            acceptRequest(req, resp);
        } else {
            listInbox(req, resp);
        }
    }

    private void listInbox(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if ( (int) req.getSession().getAttribute("requestsCount") > 0) {
            req.getSession().setAttribute("requestsCount", 0);
        }
        User user = (User) req.getSession().getAttribute("user");
        List<TaskModificationRequest> requests = taskService.getAllTaskModificationRequestsByManagerId(user.getId());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm");

        req.setAttribute("formatter", formatter);
        req.setAttribute("inboxes", requests);
        req.getRequestDispatcher("/WEB-INF/views/inboxes/inboxList.jsp").forward(req, resp);
    }

    private void acceptRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String strId = req.getParameter("id");


        Long id = 0L;
        if (strId != null) id = Long.parseLong(strId);
        else ResponseHandler.handleError(req, resp, "inbox", "Inbox id is required");


        Optional<TaskModificationRequest> request = taskService.getTaskModificationRequestById(Long.parseLong(strId));

        if (request.isEmpty()) ResponseHandler.handleError(req, resp, "inbox", "Inbox not found");

        TaskModificationRequest taskModificationRequest = request.get();
        User user = taskModificationRequest.getUser();
        Task task = taskModificationRequest.getTask();

        taskModificationRequest.setManagerResponse(true);
        taskModificationRequest.setResponseTime(LocalDateTime.now());

        long hoursBetween = Duration.between(taskModificationRequest.getRequestTime(), LocalDateTime.now()).toHours();
        if (hoursBetween >= 12) {

            taskModificationRequest.setStatus(RequestStatus.expired);
            taskService.updateTaskModificationRequest(taskModificationRequest);
            ResponseHandler.handleError(req, resp, "inbox", "Task modification request expired");
            return;
        }

        List<User> users = userService.getUsersByRole(UserRole.user);
        users = users.stream().filter(u -> u != user).toList();

        req.setAttribute("users", users);
        req.setAttribute("task", task);
        req.setAttribute("taskModificationRequest", taskModificationRequest);
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
        String strTaskModificationRequestId = req.getParameter("request_id");

        if (strTaskId == null || strUserId == null || strTaskModificationRequestId == null || strTaskId.isEmpty() || strUserId.isEmpty() || strTaskModificationRequestId.isEmpty()) {
            ResponseHandler.handleError(req, resp, "inbox", "Task and User id are required");
            return;
        }

        Long taskId = Long.parseLong(strTaskId);
        Long userId = Long.parseLong(strUserId);
        Long taskModificationRequestId = Long.parseLong(strTaskModificationRequestId);


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

        if (task.getUser().getId() == user.getId()) {

            Optional<TaskModificationRequest> opTaskModificationRequest = taskService.getTaskModificationRequestById(taskModificationRequestId);

            if (opTaskModificationRequest.isEmpty()) {
                ResponseHandler.handleError(req, resp, "inbox", "Task modification request not found");
                return;
            }
            TaskModificationRequest taskModificationRequest = opTaskModificationRequest.get();

            taskModificationRequest.setStatus(RequestStatus.accepted);
           if (taskService.updateTaskModificationRequest(taskModificationRequest) != null) {
                 ResponseHandler.handleSuccess(req, resp, "tasks", "Task assigned successfully");
           }
           else ResponseHandler.handleError(req, resp, "inbox", "Task assignment failed");
        }  else ResponseHandler.handleError(req, resp, "inbox", "Task assignment failed");


    }

    @Override
    public void destroy() {
        PersistenceManager.close();
    }

}
