package com.MBAREK0.web.repository.implementation;

import com.MBAREK0.web.entity.Token;
import com.MBAREK0.web.repository.TokenRepository;
import jakarta.persistence.EntityManager;

public class TokenRepositoryImpl implements TokenRepository {

    private EntityManager entityManager;

    public  TokenRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Token saveToken(Token token) {
        entityManager.getTransaction().begin();
        entityManager.persist(token);
        entityManager.getTransaction().commit();
        return token;
    }

    @Override
    public Token deleteToken(Token token) {
        entityManager.getTransaction().begin();
        entityManager.remove(token);
        entityManager.getTransaction().commit();
        return token;
    }
}
