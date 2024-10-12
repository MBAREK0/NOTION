package com.MBAREK0.web.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class PersistenceManager {

    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");

    private PersistenceManager() {}

    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public static void close() {
        if (entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}
