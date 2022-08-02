package com.example.demo.service;

import org.springframework.security.core.Authentication;

public interface TokenService {

    String generateToken(Authentication auth);
    boolean isTokenValid(String token);
    Long getTokenId(String token);

}
