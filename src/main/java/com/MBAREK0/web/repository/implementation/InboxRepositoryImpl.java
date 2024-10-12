package com.MBAREK0.web.repository.implementation;

import com.MBAREK0.web.entity.Inbox;
import com.MBAREK0.web.repository.InboxRepository;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class InboxRepositoryImpl implements InboxRepository {

    private EntityManager entityManager;

    public InboxRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Inbox> getInboxById(Long id) {
        Inbox inbox = entityManager.find(Inbox.class, id);
        return inbox != null ? Optional.of(inbox) : Optional.empty();
    }



    @Override
    public List<Inbox> getAllInboxByUserId(Long userId) {
        return entityManager.createQuery("SELECT i FROM Inbox i WHERE i.manager.id = :userId", Inbox.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public Inbox createInbox(Inbox inbox) {
        entityManager.getTransaction().begin();
        entityManager.persist(inbox);
        entityManager.getTransaction().commit();
        return inbox;
    }

    @Override
    public Inbox deleteInbox(Inbox inbox) {
        entityManager.getTransaction().begin();
        entityManager.remove(inbox);
        entityManager.getTransaction().commit();
        return inbox;
    }
}
