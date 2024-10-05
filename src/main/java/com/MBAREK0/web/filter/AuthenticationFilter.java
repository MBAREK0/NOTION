package com.MBAREK0.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        // Check if user is logged in
        boolean isLoggedIn = (session != null && session.getAttribute("user") != null);

        String requestURI = httpRequest.getRequestURI();

        if (!isLoggedIn && !requestURI.equals(httpRequest.getContextPath() + "/")) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/");
            return;
        }

        // Proceed with the next filter or servlet in the chain
        chain.doFilter(request, response);
    }

    // is called by the web container to indicate to a filter that it is being taken out of service.
    @Override
    public void destroy() {
    }
}
