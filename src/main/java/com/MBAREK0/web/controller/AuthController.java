package com.MBAREK0.web.controller;

import com.MBAREK0.web.config.PersistenceManager;
import com.MBAREK0.web.entity.TaskModificationRequest;
import com.MBAREK0.web.entity.User;
import com.MBAREK0.web.entity.UserRole;
import com.MBAREK0.web.service.TaskService;
import com.MBAREK0.web.util.PasswordUtil;
import com.MBAREK0.web.service.UserService;
import com.MBAREK0.web.util.ResponseHandler;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class AuthController extends HttpServlet {

    private UserService userService;
    private EntityManager entityManager;
    private TaskService taskService;

    public AuthController() {
        entityManager = PersistenceManager.getEntityManager();
        userService = new UserService(entityManager);
        taskService = new TaskService(entityManager);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("logout".equals(action)) {
            logout(request, response);
        } else {
            showLoginForm(request, response);
        }
    }

    public void showLoginForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/auth/auth.jsp").forward(request, response);
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        request.getSession().setAttribute("user", null);
        request.getSession().setAttribute("role", null);
        response.sendRedirect(request.getContextPath() + "/");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


      try {
          String email = request.getParameter("email");
          String password = request.getParameter("password");

          User user = userService.getUserByEmail(email).orElse(null);

          if(user != null && PasswordUtil.checkPassword(password, user.getPassword())){

              UserRole role = user.getRole();
              request.getSession().setAttribute("user", user);
              request.getSession().setAttribute("role", role);

              if (user.getRole().equals(UserRole.manager)) {
                  List<TaskModificationRequest> requests = taskService.getAllTaskModificationRequestsByManagerId(user.getId());
                  request.getSession().setAttribute("requestsCount", requests.size());
              }

              response.sendRedirect(request.getContextPath() + "/tasks");

          }else {
              ResponseHandler.handleError(request, response,"", "Invalid email or password.");
          }

      }catch (Exception e){
          String message = "An error occurred while processing your request" + e.getMessage();
          ResponseHandler.handleError(request, response,"", message);
      }
    }

    @Override
    public void destroy() {
        PersistenceManager.close();
    }

}
