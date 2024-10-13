package com.MBAREK0.web.service;

import com.MBAREK0.web.entity.Token;
import com.MBAREK0.web.entity.User;
import com.MBAREK0.web.entity.UserRole;
import com.MBAREK0.web.repository.TokenRepository;
import com.MBAREK0.web.repository.implementation.TokenRepositoryImpl;
import jakarta.persistence.EntityManager;

import java.util.Optional;

public class TokenService {

    private TokenRepository tokenRepository;

    public TokenService(EntityManager entityManager) {
        this.tokenRepository = new TokenRepositoryImpl(entityManager);
    }

    public Token saveTokenForUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        if (user.getRole().equals(UserRole.user)) {
            Token token = new Token(user);
            return tokenRepository.saveToken(token);
        }else {
            throw new IllegalArgumentException("Only users can have tokens");
        }

    }

    public Optional<Token> getTokenByUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        return tokenRepository.getTokenByUser(user);
    }

    public Token deleteToken(Token token) {
        return tokenRepository.deleteToken(token);
    }
}
