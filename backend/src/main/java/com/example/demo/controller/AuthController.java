package com.example.demo.controller;

import com.example.demo.dto.LoginDTO;
import com.example.demo.dto.TokenDTO;
import com.example.demo.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<?> auth(@RequestBody @Validated LoginDTO payload){
        UsernamePasswordAuthenticationToken upt =
            new UsernamePasswordAuthenticationToken(payload.getEmail(), payload.getPassword());
        Authentication auth = authenticationManager.authenticate(upt);
        String token = tokenService.generateToken(auth);
        return ResponseEntity.ok(TokenDTO.builder().token(token).type("Bearer").build());
    }

}
