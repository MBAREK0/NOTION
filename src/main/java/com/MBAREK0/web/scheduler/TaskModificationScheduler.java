package com.MBAREK0.web.scheduler;

import com.MBAREK0.web.config.PersistenceManager;
import com.MBAREK0.web.entity.RequestStatus;
import com.MBAREK0.web.entity.TaskModificationRequest;
import com.MBAREK0.web.entity.User;
import com.MBAREK0.web.repository.UserRepository;
import com.MBAREK0.web.repository.implementation.UserRepositoryImpl;
import com.MBAREK0.web.service.TaskService;
import com.MBAREK0.web.service.UserService;
import jakarta.ejb.Schedule;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.List;

@Startup // Ensures this bean is created at application startup
@Singleton
public class TaskModificationScheduler {

    private TaskService taskService;
    private UserService userService;


    public TaskModificationScheduler() {

        // Create repository and service, inject dependencies

        // Initialize the TaskService
        EntityManager entityManager = PersistenceManager.getEntityManager();

        UserRepository userRepository = new UserRepositoryImpl(entityManager);

        this.taskService = new TaskService(entityManager);
        this.userService = new UserService(userRepository);
    }

    @Schedule(hour = "0,12", minute = "0", persistent = false) // Runs every day at 00:00 and 12:00
    public void checkPendingRequests() {
        List<TaskModificationRequest> pendingRequests = taskService.getPendingRequestsOlderThan12Hours();

        for (TaskModificationRequest request : pendingRequests) {
            User user = request.getUser();
            user.setEligibleForDoubleTokens(user.getEligibleForDoubleTokens() + 1);
            userService.updateUser(user);
            request.setStatus(RequestStatus.expired);
            taskService.updateTaskModificationRequest(request);
        }
    }
}
