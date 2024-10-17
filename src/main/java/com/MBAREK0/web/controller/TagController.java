package com.MBAREK0.web.controller;

import com.MBAREK0.web.config.PersistenceManager;
import com.MBAREK0.web.entity.Tag;
import com.MBAREK0.web.repository.UserRepository;
import com.MBAREK0.web.repository.implementation.UserRepositoryImpl;
import com.MBAREK0.web.service.TagService;
import com.MBAREK0.web.service.UserService;
import com.MBAREK0.web.util.ResponseHandler;
import com.MBAREK0.web.validation.validator.Validator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class TagController extends HttpServlet {

    private EntityManager entityManager;
    private TagService tagService;
    private UserService userService;

    public TagController() {
        entityManager = PersistenceManager.getEntityManager();
        tagService = new TagService(entityManager);
        UserRepository userRepository = new UserRepositoryImpl(entityManager);

        userService = new UserService(userRepository);
    }

    @Override
    public void init() throws ServletException {
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
        List<Tag> tags = tagService.gatAllTags();
        req.setAttribute("tags", tags);
        req.getRequestDispatcher("/WEB-INF/views/tags/tagList.jsp").forward(req, resp);
    }

    private void showCreateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/tags/createForm.jsp").forward(req, resp);
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("id"));

        if (tagService.getTagById(id).isEmpty()){
            ResponseHandler.handleError(req, resp, "tags", "Tag not found");
            return;
        }

        Tag tag = tagService.getTagById(id).get();
        req.setAttribute("tag", tag);

        req.getRequestDispatcher("/WEB-INF/views/tags/editForm.jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

        if (!Validator.isValidName(name)){
            ResponseHandler.handleError(req, resp, "tags", "Invalid name");
            return;
        }

        Tag tag = new Tag();
        tag.setName(name);

        if (tagService.createTag(tag).getId() == null)
            ResponseHandler.handleError(req, resp, "tags", "Failed to create tag");
        else
            ResponseHandler.handleSuccess(req, resp, "tags", "Tag created successfully");
    }

    private void editTag(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("id"));
        String name = req.getParameter("name");

        if (!Validator.isValidName(name)){
            ResponseHandler.handleError(req, resp, "tags", "Invalid name");
            return;
        }

        if (tagService.getTagById(id).isEmpty()){
            ResponseHandler.handleError(req, resp, "tags", "Tag not found");
            return;
        }

        Optional<Tag> opTag = tagService.getTagById(id);
        if(opTag.isEmpty()){
            ResponseHandler.handleError(req, resp, "tags", "Tag not found");
            return;
        }

        Tag tag = opTag.get();
        tag.setName(name);

        tagService.updateTag(tag);

        ResponseHandler.handleSuccess(req, resp, "tags", "Tag updated successfully");
    }

    private void deleteTag(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("id"));
        if (tagService.getTagById(id).isEmpty()){
            ResponseHandler.handleError(req, resp, "tags", "Tag not found");
            return;
        }

        Optional<Tag> opTag = tagService.getTagById(id);
        if(opTag.isEmpty()){
            ResponseHandler.handleError(req, resp, "tags", "Tag not found");
            return;
        }
        Tag tag = opTag.get();

        tagService.deleteTag(tag);

        ResponseHandler.handleSuccess(req, resp, "tags", "Tag deleted successfully");
    }

    @Override
    public void destroy() {
        PersistenceManager.close();
    }

}
