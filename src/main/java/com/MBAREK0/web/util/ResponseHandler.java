package com.MBAREK0.web.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ResponseHandler {
    public static void handleError(HttpServletRequest request, HttpServletResponse response, String path, String message) throws IOException {
        request.getSession().setAttribute("errorMessage", message);
        response.sendRedirect(request.getContextPath() + "/"+path);
    }

    public static void handleSuccess(HttpServletRequest request, HttpServletResponse response,String path, String message) throws IOException, IOException {
        request.getSession().setAttribute("message", message);
        response.sendRedirect(request.getContextPath() + "/"+path);
    }
}
