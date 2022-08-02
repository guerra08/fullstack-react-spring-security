package com.example.demo.runner;

import com.example.demo.contract.RegistrationRequest;
import com.example.demo.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitialDataLoader implements ApplicationRunner {

    private final UserServiceImpl userServiceImpl;

    @Override
    public void run(ApplicationArguments args) {
        userServiceImpl.createUser(
            new RegistrationRequest("Bruno Guerra", "bruno@email.com", "123456"));
    }
}
