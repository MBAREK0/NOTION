package com.MBAREK0.web.controller;

import com.MBAREK0.web.entity.User;
import com.MBAREK0.web.entity.UserOrManager;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class UserController extends HttpServlet {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private UserService userService;

    public UserController() {
        entityManagerFactory = Persistence.createEntityManagerFactory("default");
        entityManager = entityManagerFactory.createEntityManager();
        userService = new UserService(entityManager);
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
            response.sendRedirect(request.getContextPath() + "/users?action=list");
            return;
        }
        User user = user_optional.get();
        request.setAttribute("user", user);
        request.getRequestDispatcher("/WEB-INF/views/users/updateForm.jsp").forward(request, response);
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long userId = Long.parseLong(request.getParameter("id"));
        userService.deleteUser(userId);
        String message = "User deleted successfully.";
        request.getSession().setAttribute("message", message);
        response.sendRedirect(request.getContextPath() + "/users?action=list");
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
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String role = request.getParameter("role");

        User newUser = new User(username, password, firstName, lastName, email, UserOrManager.valueOf(role));
        if (userService.createUser(newUser) == null) {
            String message = "User with email " + email + " already exists.";
            request.getSession().setAttribute("errorMessage", message);
        }
        String message = "User created successfully.";
        request.getSession().setAttribute("message", message);
        response.sendRedirect(request.getContextPath() + "/users?action=list");
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long userId = Long.parseLong(request.getParameter("id"));
        User user = userService.getUserById(userId).get();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String role = request.getParameter("role");

        // Update role as needed

        User updatedUser = new User(username, password, firstName, lastName, email, UserOrManager.valueOf(role),user.getCreatedAt(),user.getUpdatedAt());
        updatedUser.setUpdatedAt(LocalDateTime.now());

        updatedUser.setId(userId);
        userService.updateUser(updatedUser);
        String message = "User updated successfully.";
        request.getSession().setAttribute("message", message);
        response.sendRedirect(request.getContextPath() + "/users?action=list");
    }
}
