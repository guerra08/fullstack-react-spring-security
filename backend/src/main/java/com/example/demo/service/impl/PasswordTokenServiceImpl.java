package com.example.demo.service.impl;

import com.example.demo.entity.PasswordToken;
import com.example.demo.entity.User;
import com.example.demo.exception.PasswordTokenNotFoundException;
import com.example.demo.repository.PasswordTokenRepository;
import com.example.demo.service.PasswordTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordTokenServiceImpl implements PasswordTokenService {

    private final PasswordTokenRepository passwordTokenRepository;

    @Override
    public PasswordToken createPasswordTokenForUser(User user) {
        if(user != null){
            PasswordToken pt = PasswordToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString().replace("-", ""))
                .expirationDate(Instant.now().plus(1, ChronoUnit.DAYS)).build();
            return passwordTokenRepository.save(pt);
        }
        return null;
    }

    @Override
    public boolean isTokenValid(String token) throws PasswordTokenNotFoundException {
        return passwordTokenRepository.findPasswordTokenByToken(token)
            .map(pt -> !pt.isUsed() && pt.getExpirationDate().isAfter(Instant.now()))
            .orElseThrow(PasswordTokenNotFoundException::new);
    }

    @Override
    public PasswordToken getPasswordTokenByTokenString(String token) {
        return passwordTokenRepository.findPasswordTokenByToken(token)
            .orElseThrow(PasswordTokenNotFoundException::new);
    }

}
