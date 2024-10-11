package com.MBAREK0.web.repository.implementation;

import com.MBAREK0.web.repository.InboxRepository;
import jakarta.persistence.EntityManager;

public class InboxRepositoryImpl  implements InboxRepository {

    private EntityManager entityManager;

    public InboxRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
