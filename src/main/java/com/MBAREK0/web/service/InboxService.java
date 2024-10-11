package com.MBAREK0.web.service;

import com.MBAREK0.web.repository.InboxRepository;
import com.MBAREK0.web.repository.TagRepository;
import com.MBAREK0.web.repository.implementation.InboxRepositoryImpl;
import com.MBAREK0.web.repository.implementation.TagRepositoryImpl;
import jakarta.persistence.EntityManager;

public class InboxService {

    private InboxRepository inboxRepository;

    public InboxService(EntityManager entityManager) {
        this.inboxRepository = new InboxRepositoryImpl(entityManager);
    }

}
