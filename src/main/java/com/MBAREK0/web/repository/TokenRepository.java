package com.MBAREK0.web.repository;

import com.MBAREK0.web.entity.Token;
import com.MBAREK0.web.entity.User;
import jakarta.persistence.EntityManager;

import java.util.Optional;

public interface TokenRepository   {

    Token saveToken(Token token);
    Optional<Token> getTokenByUser(User user);
    Token deleteToken(Token token);
}
