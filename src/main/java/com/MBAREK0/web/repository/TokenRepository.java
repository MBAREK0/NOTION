package com.MBAREK0.web.repository;

import com.MBAREK0.web.entity.Token;
import jakarta.persistence.EntityManager;

public interface TokenRepository   {

    Token saveToken(Token token);

    Token deleteToken(Token token);
}
