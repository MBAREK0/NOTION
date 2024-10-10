package com.MBAREK0.web.controller;

import com.MBAREK0.web.entity.User;
import com.MBAREK0.web.entity.UserOrManager;
import com.MBAREK0.web.util.PasswordUtil;
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

public class AuthController extends HttpServlet {
    private EntityManagerFactory entityManagerFactory;
    private UserService userService;
    private EntityManager entityManager;

    public AuthController() {
        entityManagerFactory = Persistence.createEntityManagerFactory("default");
        entityManager = entityManagerFactory.createEntityManager();
        userService = new UserService(entityManager);
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
        response.sendRedirect(request.getContextPath() + "/");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


      try {
          String email = request.getParameter("email");
          String password = request.getParameter("password");

          User user = userService.getUserByEmail(email).orElse(null);

          if(user != null && PasswordUtil.checkPassword(password, user.getPassword())){

              UserOrManager role = user.getRole();
              request.getSession().setAttribute("user", user);
              request.getSession().setAttribute("role", role);

              response.sendRedirect(request.getContextPath() + "/tasks");

          }else {
              ResponseHandler.handleError(request, response,"", "Invalid email or password.");
          }

      }catch (Exception e){
          String message = "An error occurred while processing your request" + e.getMessage();
          ResponseHandler.handleError(request, response,"", message);
      }
    }


}
