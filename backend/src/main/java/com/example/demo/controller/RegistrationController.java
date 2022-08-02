package com.example.demo.controller;

import com.example.demo.contract.RegistrationRequest;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> postRegisterUser(@RequestBody RegistrationRequest payload){
        try {
            userService.createUser(payload);
            return ResponseEntity.status(HttpStatus.CREATED).body("User created.");
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }

    }

}
