package com.MBAREK0.web.controller;

import com.MBAREK0.web.config.PersistenceManager;
import com.MBAREK0.web.entity.User;
import com.MBAREK0.web.entity.UserRole;
import com.MBAREK0.web.service.TokenService;
import com.MBAREK0.web.service.UserService;
import com.MBAREK0.web.util.ResponseHandler;
import com.MBAREK0.web.objCreator.CreateObj;
import com.MBAREK0.web.validation.validator.UserValidator;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class UserController extends HttpServlet {

    private EntityManager entityManager;
    private UserService userService;
    private TokenService tokenService ;

    public UserController() {
        entityManager = PersistenceManager.getEntityManager();
        userService = new UserService(entityManager);
        tokenService = new TokenService(entityManager);
    }

    @Override
    public void init() throws ServletException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("list".equals(action)) {
            listUsers(request, response);
        } else if ("create".equals(action)) {
            showCreateForm(request, response);
        } else if ("edit".equals(action)) {
            showEditForm(request, response);
        } else if ("delete".equals(action)) {
            deleteUser(request, response);
        } else {
            listUsers(request, response);
        }
    }

    private void listUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user.getRole().equals(UserRole.user)){
            ResponseHandler.handleError(request,response,"","You are not allowed to access this page");
        }
        List<User> users = userService.getAllUsers();
        request.setAttribute("users", users);
        request.getRequestDispatcher("/WEB-INF/views/users/userList.jsp").forward(request, response);
    }

    private void showCreateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/users/createForm.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long userId = Long.parseLong(request.getParameter("id"));
        Optional<User> user_optional = userService.getUserById(userId);
        if (!user_optional.isPresent()) {
             ResponseHandler.handleError(request, response,"users", "User not found.");
            return;
        }
        User user = user_optional.get();
        request.setAttribute("user", user);
        request.getRequestDispatcher("/WEB-INF/views/users/updateForm.jsp").forward(request, response);
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long userId = Long.parseLong(request.getParameter("id"));
        userService.deleteUser(userId);
        ResponseHandler.handleSuccess(request, response, "users","User deleted successfully.");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("create".equals(action)) {
            createUser(request, response);
        } else if ("edit".equals(action)) {
            updateUser(request, response);
        }
    }

    private void createUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User newUser = new User();


        if (!CreateObj.createUserObj(newUser, request, response)) {
            return;
        }

        if (!UserValidator.isValidUser(newUser)) {
             ResponseHandler.handleError(request, response,"users", "Invalid user data.");
            return;
        }

        if (userService.createUser(newUser) == null) {
             ResponseHandler.handleError(request, response,"users", "User with email " + request.getParameter("email") + " already exists.");
            return;
        }

        if (newUser.getRole().equals(UserRole.user)){
            tokenService.saveTokenForUser(newUser);
        }


        ResponseHandler.handleSuccess(request, response,"users", "User created successfully.");
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long userId = Long.parseLong(request.getParameter("id"));
        User user = userService.getUserById(userId).orElse(null);

        if (user == null) {
             ResponseHandler.handleError(request, response,"users", "User not found.");
            return;
        }

        if (!CreateObj.createUserObj(user, request, response)) {
            return;
        }

        if (!UserValidator.isValidUser(user)) {
             ResponseHandler.handleError(request, response,"users", "Invalid user data.");
            return;
        }

        user.setUpdatedAt(LocalDateTime.now());
//        if (user.getRole().equals(UserOrManager.user)){
//            tokenService.saveTokenForUser(user);
//        }else{
//            if (user.getToken() != null){
//                tokenService.deleteToken(user.getToken());
//                user.setToken(null);
//            }
//        }

        userService.updateUser(user);
        ResponseHandler.handleSuccess(request, response, "users","User updated successfully.");
    }


    @Override
    public void destroy() {
        PersistenceManager.close();
    }
}
