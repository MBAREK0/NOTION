package com.MBAREK0.web.objCreator;

import com.MBAREK0.web.entity.*;
import com.MBAREK0.web.service.TagService;
import com.MBAREK0.web.service.UserService;
import com.MBAREK0.web.util.ResponseHandler;
import com.MBAREK0.web.validation.validator.UserValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class CreateObj {

    public static Boolean createUserObj(User user, HttpServletRequest request, HttpServletResponse response) throws IOException {
        user.setUsername(request.getParameter("username"));
        user.setPassword(request.getParameter("password"));
        user.setFirstName(request.getParameter("firstName"));
        user.setLastName(request.getParameter("lastName"));
        user.setEmail(request.getParameter("email"));
        if (request.getParameter("role") != null && UserValidator.isValidRole(request.getParameter("role")))
            user.setRole(UserRole.valueOf(request.getParameter("role")));
        else{
            ResponseHandler.handleError(request, response,"users", "Invalid user data.");
            return false;
        }
        return true;
    }

    public static Task createTaskObj( UserService userService, TagService tagService, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();

        String title = req.getParameter("title");


        String description = req.getParameter("description");

        String startDate = req.getParameter("startDate");

        String endDate = req.getParameter("endDate");

        User manager = (User) session.getAttribute("user");


        User user = manager;

        if (req.getParameter("userId") != null) {
            Long userId = Long.parseLong(req.getParameter("userId"));

            Optional<User> opUser = userService.getUserById(userId);

            if (opUser.isEmpty()) {
                return null;
            }
            user = opUser.get();
        }




        String[] tags = req.getParameterValues("tags");
        List<String> tagsList = null;
        if (tags != null) {
            tagsList = Arrays.asList(tags);
            // Convert to ArrayList
            tagsList = new ArrayList<>(tagsList);

        } else {
            return null;
        }

        List<Tag> tagObjList = tagService.getTagsByIds(tagsList);
        if (tagObjList.isEmpty()) {
            return null;
        }


        Task task = new Task(title, description, TaskStatus.pending, LocalDate.parse(startDate), LocalDate.parse(endDate), user , manager);
        task.setTags(new HashSet<>(tagObjList));
        if(manager.getRole().equals(UserRole.user) && user.getRole().equals(UserRole.user)){
            task.setUser(manager);
            task.setManager(manager);
        }

        return task;
    }

    public static Task updateTaskObj(Task task, UserService userService, TagService tagService, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        HttpSession session = req.getSession();

        String title = req.getParameter("title");

        String description = req.getParameter("description");

        String startDate = req.getParameter("startDate");

        String endDate = req.getParameter("endDate");


        TaskStatus status= TaskStatus.valueOf(req.getParameter("status"));


        User user = null ;

        if (req.getParameter("userId") != null) {
            Long userId = Long.parseLong(req.getParameter("userId"));
            Optional<User> opUser = userService.getUserById(userId);

            if (opUser.isEmpty()) {
                return null;
            }
            user = opUser.get();
        }


        String[] tags = req.getParameterValues("tags");
        List<String> tagsList = null;
        if (tags != null) {
            tagsList = Arrays.asList(tags);
            tagsList = new ArrayList<>(tagsList);
        } else {
            return null;
        }

        List<Tag> tagObjList = tagService.getTagsByIds(tagsList);
        if (tagObjList.isEmpty()) {
            return null;
        }


        task.setTitle(title);
        task.setDescription(description);
        task.setStatus(status);
        task.setStartDate(LocalDate.parse(startDate));
        task.setEndDate(LocalDate.parse(endDate));
        task.setUser(user);
        task.setTags(new HashSet<>(tagObjList));
        task.setUpdatedAt(LocalDate.now().atStartOfDay());
        task.setId(Long.parseLong(req.getParameter("id")));

        User authUser = (User) session.getAttribute("user");


        return task;
    }


}
