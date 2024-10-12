package com.MBAREK0.web.service;

import com.MBAREK0.web.entity.TaskHistory;
import com.MBAREK0.web.repository.TaskHistoryRepository;
import com.MBAREK0.web.repository.implementation.TaskHistoryRepositoryImpl;
import jakarta.persistence.EntityManager;

import java.util.Optional;

public class TaskHistoryService {
    private TaskHistoryRepository taskHistoryRepository;

    public TaskHistoryService(EntityManager entityManager) {
        this.taskHistoryRepository = new TaskHistoryRepositoryImpl(entityManager);
    }



}
