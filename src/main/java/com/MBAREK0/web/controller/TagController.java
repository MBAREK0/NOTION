package com.MBAREK0.web.controller;

import com.MBAREK0.web.entity.Tag;
import com.MBAREK0.web.service.TagService;
import com.MBAREK0.web.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class TagController extends HttpServlet {

    private TagService tagService;

    @Override
    public void init() throws ServletException {
        tagService = new TagService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("list".equals(action)) {
            listTags(req, resp);
        } else if ("create".equals(action)) {
            showCreateForm(req, resp);
        } else if ("edit".equals(action)) {
            showEditForm(req, resp);
        } else if ("delete".equals(action)) {
            deleteTag(req, resp);
        } else {
            listTags(req, resp);
        }

    }

    private void listTags(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/tags/tagList.jsp").forward(req, resp);
    }

    private void showCreateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/tags/createForm.jsp").forward(req, resp);
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/tags/editForm.jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService userService = new UserService();
        String action = req.getParameter("action");
        if ("create".equals(action)) {
            createTag(req, resp);
        } else if ("edit".equals(action)) {
            editTag(req, resp);
        } else if ("delete".equals(action)) {
            deleteTag(req, resp);
        }
    }

    private void createTag(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        Tag tag = new Tag();
        tag.setName(name);
        if (tagService.createTag(tag) == null){
            String message = "Tag already exists";
            req.getSession().setAttribute("errorMessage", message);
        }else {
            String message = "tag created successfully";
            req.getSession().setAttribute("message", message);
        }
        resp.sendRedirect(req.getContextPath() + "/tags?action=list");

    }

    private void editTag(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("id"));
        String name = req.getParameter("name");

        if (tagService.getTagById(id).isEmpty()){
            String message = "Tag not found";
            req.getSession().setAttribute("errorMessage", message);
            resp.sendRedirect(req.getContextPath() + "/tags?action=list");
            return;
        }
        Tag tag = tagService.getTagById(id).get();
        tag.setName(name);
        if (tagService.updateTag(tag) == null){
            String message = "Tag not found";
            req.getSession().setAttribute("errorMessage", message);
            resp.sendRedirect(req.getContextPath() + "/tags?action=list");
            return;
        }else {
            String message = "tag updated successfully";
            req.getSession().setAttribute("message", message);
        }
        resp.sendRedirect(req.getContextPath() + "/tags?action=list");
    }

    private void deleteTag(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("id"));
        if (tagService.getTagById(id).isEmpty()){
            String message = "Tag not found";
            req.getSession().setAttribute("errorMessage", message);
            resp.sendRedirect(req.getContextPath() + "/tags?action=list");
            return;
        }
        Tag tag = tagService.getTagById(id).get();

       if ( tagService.deleteTag(tag) != null){
           String message = "tag deleted successfully";
           req.getSession().setAttribute("message", message);
       }else {
           String message = "tag not found";
           req.getSession().setAttribute("errorMessage", message);
         }

        resp.sendRedirect(req.getContextPath() + "/tags?action=list");
    }


}
