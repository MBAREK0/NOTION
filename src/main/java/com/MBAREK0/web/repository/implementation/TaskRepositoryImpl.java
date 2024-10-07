package com.MBAREK0.web.repository.implementation;

import com.MBAREK0.web.entity.Task;
import com.MBAREK0.web.repository.TaskRepository;
import jakarta.persistence.EntityManager;

import java.util.List;

public class TaskRepositoryImpl implements TaskRepository {
    private EntityManager entityManager;

    public TaskRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Task createTask(Task task) {
        entityManager.getTransaction().begin();
        entityManager.merge(task);
        entityManager.getTransaction().commit();

        return task;
    }


    @Override
    public Task getTaskById(Long id) {
        return entityManager.find(Task.class, id);
    }

    public List<Task> getTasksByUserId(Long userId) {
        return entityManager.createQuery("SELECT t FROM Task t WHERE t.user.id = :userId", Task.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public List<Task> getAllTasks() {
        return entityManager.createQuery("SELECT t FROM Task t", Task.class).getResultList();
    }

    @Override
    public Task updateTask(Task task) {
        entityManager.getTransaction().begin();
        entityManager.merge(task);
        entityManager.getTransaction().commit();

        return task;
    }

    @Override
    public Task deleteTask(Long id) {
        Task task = entityManager.find(Task.class, id);
        entityManager.getTransaction().begin();
        entityManager.remove(task);
        entityManager.getTransaction().commit();

        return task;
    }


}
