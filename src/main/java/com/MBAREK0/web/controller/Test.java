package com.MBAREK0.web.controller;


import com.MBAREK0.web.entity.Tag;
import com.MBAREK0.web.entity.User;
import com.MBAREK0.web.entity.UserOrManager;
import com.MBAREK0.web.service.TagService;
import com.MBAREK0.web.util.PasswordUtil;
import com.MBAREK0.web.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class Test extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TagService tagService = new TagService();
        Tag tag = tagService.getTagById(1).get();

        tag.setName("Test1");
        Tag tag1 = tagService.deleteTag(tag);
        if (tag1 != null) {
            PrintWriter out = response.getWriter();
            out.println("tag1 updateTag successfully");
        } else {
            PrintWriter out = response.getWriter();
            out.println("tag1 updateTag failed");
        }

    }
}
