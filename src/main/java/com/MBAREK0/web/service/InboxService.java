package com.MBAREK0.web.service;

import com.MBAREK0.web.entity.Inbox;
import com.MBAREK0.web.repository.InboxRepository;
import com.MBAREK0.web.repository.implementation.InboxRepositoryImpl;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class InboxService {


    private InboxRepository inboxRepository;

    public InboxService(EntityManager entityManager) {
        this.inboxRepository = new InboxRepositoryImpl(entityManager);
    }

    public List<Inbox> getAllInboxByUserId(Long userId) {
        return inboxRepository.getAllInboxByUserId(userId);
    }

    public Inbox createInbox(Inbox inbox) {
        return inboxRepository.createInbox(inbox);
    }

    public Optional<Inbox> getInboxById(Long id) {
        return inboxRepository.getInboxById(id);
    }

    public Inbox deleteInbox(Long inboxId) {
        Optional<Inbox> inbox = this.getInboxById(inboxId);
        if (inbox.isEmpty()) {
            return null;
        }

        return inboxRepository.deleteInbox(inbox.get());
    }
}
