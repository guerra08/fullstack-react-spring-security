package com.example.demo.repository;

import com.example.demo.entity.PasswordToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PasswordTokenRepository extends CrudRepository<PasswordToken, Long> {

    Optional<PasswordToken> findPasswordTokenByToken(String token);

}
