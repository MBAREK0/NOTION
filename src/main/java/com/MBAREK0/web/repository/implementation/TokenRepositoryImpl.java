package com.MBAREK0.web.repository.implementation;

import com.MBAREK0.web.entity.Token;
import com.MBAREK0.web.entity.User;
import com.MBAREK0.web.repository.TokenRepository;
import jakarta.persistence.EntityManager;

import java.util.Optional;

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
    public Optional<Token> getTokenByUser(User user) {
        Token token = entityManager.createQuery("SELECT t FROM Token t WHERE t.user = :user", Token.class)
                .setParameter("user", user)
                .getSingleResult();
        return Optional.ofNullable(token);
    }

    @Override
    public Token deleteToken(Token token) {
        entityManager.getTransaction().begin();
        entityManager.remove(token);
        entityManager.getTransaction().commit();
        return token;
    }
}
