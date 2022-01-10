package com.example.demo.utils;

import com.example.demo.dto.RegistrationDTO;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class InitialDataLoader implements ApplicationRunner {

    private final UserService userService;

    public InitialDataLoader(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        userService.createUser(
            RegistrationDTO.builder()
                .name("Bruno Guerra")
                .email("bruno@email.com")
                .password("123456")
                .build()
        );
    }
}
