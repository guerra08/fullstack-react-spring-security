package com.example.demo.service;

import com.example.demo.entity.PasswordToken;
import com.example.demo.entity.User;

public interface PasswordTokenService {

    PasswordToken createPasswordTokenForUser(User u);
    boolean isTokenValid(String token);
    PasswordToken getPasswordTokenByTokenString(String token);

}
