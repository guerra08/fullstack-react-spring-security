package com.example.demo.controller;

import com.example.demo.dto.RegistrationDTO;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<?> postRegisterUser(@RequestBody RegistrationDTO payload){
        try {
            userService.createUser(payload);
            return ResponseEntity.ok("User created with success.");
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }

    }

}
