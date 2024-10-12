package com.MBAREK0.web.repository.implementation;


import com.MBAREK0.web.repository.TaskHistoryRepository;
import jakarta.persistence.EntityManager;

public class TaskHistoryRepositoryImpl implements TaskHistoryRepository {

    private EntityManager entityManager;
    public TaskHistoryRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }



}
