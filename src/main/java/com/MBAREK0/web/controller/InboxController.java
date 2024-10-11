package com.MBAREK0.web.controller;

import com.MBAREK0.web.entity.User;
import com.MBAREK0.web.entity.UserOrManager;
import com.MBAREK0.web.service.InboxService;
import com.MBAREK0.web.service.TagService;
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

public class InboxController extends HttpServlet {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private InboxService inboxService;


    public InboxController() {
        entityManagerFactory = Persistence.createEntityManagerFactory("default");
        entityManager = entityManagerFactory.createEntityManager();
        inboxService = new InboxService(entityManager);
    }


    @Override
    public void init() throws ServletException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("list".equals(action)) {
            listInbox(req, resp);
        } else if ("create".equals(action)) {
            showCreateForm(req, resp);
        } else {
            listInbox(req, resp);
        }
    }

    private void listInbox(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user.getRole().equals(UserOrManager.user)){
            ResponseHandler.handleError(req,resp,"","You are not allowed to access this page");
        }
    }

    private void showCreateForm(HttpServletRequest req, HttpServletResponse resp) {
    }



}
