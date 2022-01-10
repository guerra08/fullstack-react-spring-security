package com.example.demo.service;

import com.example.demo.entity.PasswordToken;
import com.example.demo.entity.User;
import com.example.demo.exception.PasswordTokenNotFoundException;
import com.example.demo.repository.PasswordTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class PasswordTokenService {

    private final PasswordTokenRepository passwordTokenRepository;

    public PasswordTokenService(PasswordTokenRepository passwordTokenRepository) {
        this.passwordTokenRepository = passwordTokenRepository;
    }

    public PasswordToken createAndSavePasswordToken(User user) {
        if(user != null){
            PasswordToken pt = PasswordToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString().replace("-", ""))
                .expirationDate(Instant.now().plus(1, ChronoUnit.DAYS)).build();
            return passwordTokenRepository.save(pt);
        }
        return null;
    }

    public boolean isTokenValid(String token) throws PasswordTokenNotFoundException {
        return passwordTokenRepository.findPasswordTokenByToken(token)
            .map(pt -> !pt.isUsed() && pt.getExpirationDate().isAfter(Instant.now()))
            .orElseThrow(PasswordTokenNotFoundException::new);
    }

    public PasswordToken getPasswordTokenByToken(String token) throws PasswordTokenNotFoundException {
        return passwordTokenRepository.findPasswordTokenByToken(token)
            .orElseThrow(PasswordTokenNotFoundException::new);
    }

}
