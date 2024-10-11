package com.MBAREK0.web.controller;

import com.MBAREK0.web.entity.*;
import com.MBAREK0.web.service.TagService;
import com.MBAREK0.web.service.TaskService;
import com.MBAREK0.web.service.UserService;
import com.MBAREK0.web.util.DateUtil;
import com.MBAREK0.web.util.ResponseHandler;
import com.MBAREK0.web.objCreator.CreateObj;
import com.MBAREK0.web.validation.validator.TaskValidator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
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
        User user = (User) req.getSession().getAttribute("user");
        List<Task> tasks;
        if (user.getRole().equals(UserOrManager.manager)) {
            tasks = taskService.getAllTasksByManagerId(user.getId());
        } else {
            tasks = taskService.getAllTasksByUserId(user.getId());
        }
        List<Task> managerTasks = tasks.stream().filter(task -> task.getManager().getId() != task.getUser().getId()).toList();
        List<Task> userTasks = tasks.stream().filter(task -> task.getManager().getId() == task.getUser().getId()).toList();
        String role  = user.getRole().toString();
        req.setAttribute("role",role);
        req.setAttribute("managerTasks", managerTasks);
        req.setAttribute("userTasks", userTasks);
        req.setAttribute("tasks", tasks);
        req.getRequestDispatcher("/WEB-INF/views/tasks/taskList.jsp").forward(req, resp);
    }

    private void taskDetails(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        Optional<Task> opTask = taskService.getTaskById(id);
        if(opTask.isEmpty()){
            ResponseHandler.handleError(req, resp, "tasks", "Task not found!");
        }

        Task task = opTask.get();

        String formattedStartDate = DateUtil.getFormattedDate(task.getStartDate());
        String formattedEndDate = DateUtil.getFormattedDate(task.getEndDate());

        req.setAttribute("formattedStartDate", formattedStartDate);
        req.setAttribute("formattedEndDate", formattedEndDate);
        req.setAttribute("task", task);

        req.getRequestDispatcher("/WEB-INF/views/tasks/taskDetails.jsp").forward(req, resp);
    }

    private void showCreateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Tag> tags = tagService.gatAllTags();
        req.setAttribute("tags", tags);
        List<User> users = userService.getUsersByRole(UserOrManager.user);
        users = users.stream().filter(user -> user.getId() != ((User) req.getSession().getAttribute("user")).getId()).toList();

        User user = (User) req.getSession().getAttribute("user");
        String role  = user.getRole().toString();

        req.setAttribute("role",role);
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

        // get the status of tag for select options
        List<String> statusList = new ArrayList<>(List.of(TaskStatus.values()).stream().map(TaskStatus::toString).toList());
        if (statusList.contains(TaskStatus.overdue.toString())) {
            statusList.remove(TaskStatus.overdue.toString());
        }

        List<Tag> tags = tagService.gatAllTags();
        List<User> users = userService.getUsersByRole(UserOrManager.user);
        users = users.stream().filter(user -> user.getId() != ((User) req.getSession().getAttribute("user")).getId()).toList();

        User user = (User) req.getSession().getAttribute("user");
        String role  = user.getRole().toString();
        req.setAttribute("role",role);
        req.setAttribute("statusList", statusList);
        req.setAttribute("tags", tags);
        req.setAttribute("users", users);
        req.setAttribute("task", task);

        req.getRequestDispatcher("/WEB-INF/views/tasks/editForm.jsp").forward(req, resp);

}

    private void deleteTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        taskService.deleteTask(id);

        req.setAttribute("message", "Task is deleted!");

        resp.sendRedirect(req.getContextPath() + "/tasks");
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("create".equals(action)) {
            createTask(req, resp);
        } else if ("edit".equals(action)) {
            updateTask(req, resp);
        }else if ("update_status".equals(action)) {
            updateTaskStatus(req, resp);
        }
        else {
            listTasks(req, resp);
        }

    }

    private void createTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Task task = CreateObj.createTaskObj(userService, tagService, req, resp);

        if(!TaskValidator.isValidTask(task) || task == null){
            ResponseHandler.handleError(req, resp, "tasks", "Task creation failed!, Invalid task data.");
            return;
        }

        if (task == null) {
            ResponseHandler.handleError(req, resp, "tasks", "Task creation failed!, Invalid task data.");
            return;
        }

        task = taskService.createTask(task);

        if(task.getId() != null)
            ResponseHandler.handleSuccess(req, resp, "tasks", "Task created successfully!");
        else
            ResponseHandler.handleError(req, resp, "tasks", "Task creation failed!");

    }

    private void updateTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Long id = Long.parseLong(req.getParameter("id"));
        Optional<Task> opTask = taskService.getTaskById(id);
        if(opTask.isEmpty()){
            ResponseHandler.handleError(req, resp, "tasks", "Task not found!");
            return;
        }

        Task task = opTask.get();

        Task UpdatedTask = CreateObj.updateTaskObj(task,userService, tagService, req, resp);

        if(!TaskValidator.isValidTask(UpdatedTask) || UpdatedTask == null){
            ResponseHandler.handleError(req, resp, "tasks", "Task update failed!, Invalid task data.");
            return;
        }

        taskService.updateTask(UpdatedTask);

        ResponseHandler.handleSuccess(req, resp, "tasks", "Task updated successfully!");

    }

    private void updateTaskStatus(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("task_id"));
        Optional<Task> opTask = taskService.getTaskById(id);
        if(opTask.isEmpty()){
            ResponseHandler.handleError(req, resp, "tasks", "Task not found!");
            return;
        }

        Task task = opTask.get();
        TaskStatus status = TaskStatus.valueOf(req.getParameter("status"));
        task.setStatus(status);
        taskService.updateTask(task);

        ResponseHandler.handleSuccess(req, resp, "tasks", "Task status updated successfully!");
    }
}
