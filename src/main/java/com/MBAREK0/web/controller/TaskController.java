package com.MBAREK0.web.controller;

import com.MBAREK0.web.config.PersistenceManager;
import com.MBAREK0.web.entity.*;
import com.MBAREK0.web.service.*;
import com.MBAREK0.web.util.DateUtil;
import com.MBAREK0.web.util.ResponseHandler;
import com.MBAREK0.web.objCreator.CreateObj;
import com.MBAREK0.web.validation.validator.TaskValidator;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;

public class TaskController extends HttpServlet {

    private EntityManager entityManager;
    private TaskService taskService;
    private TagService tagService;
    private UserService userService;
    private TokenService tokenService;

    public TaskController() {
        entityManager = PersistenceManager.getEntityManager();
        taskService = new TaskService(entityManager);
        tagService = new TagService(entityManager);
        userService = new UserService(entityManager);
        tokenService = new TokenService(entityManager);
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
        } else if ("create_request".equals(action)) {
            createRequest(req, resp);
        }
        else {
            listTasks(req, resp);
        }
    }

    private void listTasks(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");

        List<Task> tasks;

        if (user.getRole().equals(UserRole.manager)) {
            tasks = taskService.getAllTasksByManagerId(user.getId());
        } else {
            tasks = taskService.getAllTasksByUserId(user.getId());
        }

        List<Task> managerTasks = tasks.stream().filter(task -> task.getManager().getId() != task.getUser().getId()).toList();
        List<Task> userTasks = tasks.stream().filter(task -> task.getManager().getId() == task.getUser().getId()).toList();

        List<String> statusList = new ArrayList<>(List.of(TaskStatus.values()).stream().map(TaskStatus::toString).toList());
        if (statusList.contains(TaskStatus.overdue.toString())) {
            statusList.remove(TaskStatus.overdue.toString());
        }

        if (user.getRole().equals(UserRole.user)) {
            Optional<Token> opToken = tokenService.getTokenByUser(user);
            if (opToken.isEmpty()) {
                ResponseHandler.handleError(req, resp, "tasks", "Token not found!");
                return;
            }
            user.setToken(opToken.get());
            int m_token = user.getToken().getModifyTokenCount();
            int d_token = user.getToken().getDeleteTokenCount();
            req.setAttribute("m_token", m_token);
            req.setAttribute("d_token", d_token);
        }

        req.setAttribute("statusList", statusList);
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
        List<User> users = userService.getUsersByRole(UserRole.user);
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
        List<User> users = userService.getUsersByRole(UserRole.user);
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


        if (task.isChanged() == true) {
            ResponseHandler.handleError(req, resp,"tasks", "Task already changed");
            return;
        }


       TaskModificationRequest requestTaskModification =  taskService.requestTaskModification(task, user, manager);

        if (requestTaskModification.getId() != null) {

            token.setModifyTokenCount(token.getModifyTokenCount() - 1);
            user.setToken(token);
            userService.updateUser(user);
            req.getSession().setAttribute("user", user);

            ResponseHandler.handleSuccess(req, resp, "tasks", "Request sent successfully");
        }
        else ResponseHandler.handleError(req, resp, "tasks", "Request failed");

    }


    private void deleteTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        User user = (User) req.getSession().getAttribute("user");
        Optional<Task> optionalTask = taskService.getTaskById(id);
        if (optionalTask.isEmpty()) {
            ResponseHandler.handleError(req, resp, "tasks", "Task not found!");
            return;
        }
        Task task = optionalTask.get();

        if (user.getRole().equals(UserRole.user)){
            if (task.getManager().getId() == user.getId()) {
                taskService.deleteTask(id);
                ResponseHandler.handleSuccess(req, resp, "tasks", "Task is deleted!");
                return;

            }else if (task.getUser().getId() == user.getId()){
                Token token = user.getToken();
                if (token.getDeleteTokenCount() == 0) {
                    ResponseHandler.handleError(req, resp, "tasks", "You don't have enough delete token");
                    return;
                }
                taskService.deleteTask(id);
                token.setDeleteTokenCount(token.getDeleteTokenCount() - 1);
                user.setToken(token);
                userService.updateUser(user);
                req.getSession().setAttribute("user", user);
                ResponseHandler.handleSuccess(req, resp, "tasks", "Task is deleted!");
                return;
            }else {
                ResponseHandler.handleError(req, resp, "tasks", "You are not allowed to delete this task!");
                return;
            }
        }

        taskService.deleteTask(id);
        ResponseHandler.handleSuccess(req, resp, "tasks", "Task is deleted successfully!");
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");

        if ("create".equals(action)) createTask(req, resp);
        else if ("edit".equals(action)) updateTask(req, resp);
        else if ("update_status".equals(action)) updateTaskStatus(req, resp);
        else listTasks(req, resp);

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

        resp.sendRedirect(req.getContextPath() + "/tasks");
    }

    @Override
    public void destroy() {
        PersistenceManager.close();
    }
}
