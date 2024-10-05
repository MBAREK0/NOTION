package com.MBAREK0.web.filter;

import com.MBAREK0.web.entity.UserOrManager;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ManagerFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Code to check if the user is a manager
        // If the user is not a manager, redirect to the home page
        // Otherwise, continue with the request

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        UserOrManager role = (UserOrManager) httpRequest.getSession().getAttribute("role");

        if (role == null || !role.equals(UserOrManager.manager)) {
            ((HttpServletResponse) response).sendRedirect(httpRequest.getContextPath() + "/");
            return;
        }

        chain.doFilter(request, response);


    }

    @Override
    public void destroy() {
        // Cleanup code
    }


}
