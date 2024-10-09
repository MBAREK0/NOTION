package com.MBAREK0.web.util.createObj;

import com.MBAREK0.web.entity.User;
import com.MBAREK0.web.entity.UserOrManager;
import com.MBAREK0.web.util.ResponseHandler;
import com.MBAREK0.web.util.validator.UserValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class CreateUserObj {

    public static Boolean createUserObj(User user, HttpServletRequest request, HttpServletResponse response) throws IOException {
        user.setUsername(request.getParameter("username"));
        user.setPassword(request.getParameter("password"));
        user.setFirstName(request.getParameter("firstName"));
        user.setLastName(request.getParameter("lastName"));
        user.setEmail(request.getParameter("email"));
        if (request.getParameter("role") != null && UserValidator.isValidRole(request.getParameter("role")))
            user.setRole(UserOrManager.valueOf(request.getParameter("role")));
        else{
            ResponseHandler.handleError(request, response,"users", "Invalid user data.");
            return false;
        }
        return true;
    }

}
