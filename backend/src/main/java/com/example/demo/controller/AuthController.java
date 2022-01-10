package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.entity.PasswordToken;
import com.example.demo.entity.User;
import com.example.demo.exception.InvalidPasswordTokenException;
import com.example.demo.exception.NonMatchingPasswordException;
import com.example.demo.exception.PasswordTokenNotFoundException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.service.PasswordTokenService;
import com.example.demo.service.TokenService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final TokenService tokenService;
    private final PasswordTokenService passwordTokenService;

    public AuthController(AuthenticationManager authenticationManager, UserService userService, TokenService tokenService, PasswordTokenService passwordTokenService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.tokenService = tokenService;
        this.passwordTokenService = passwordTokenService;
    }

    @PostMapping
    public ResponseEntity<?> auth(@RequestBody @Validated LoginDTO payload){
        UsernamePasswordAuthenticationToken upt =
            new UsernamePasswordAuthenticationToken(payload.getEmail(), payload.getPassword());
        Authentication auth = authenticationManager.authenticate(upt);
        User user = (User) auth.getPrincipal();
        String token = tokenService.generateToken(auth);
        return ResponseEntity.ok(
            AuthDTO.builder()
                .token(TokenDTO.builder().token(token).type("Bearer").build())
                .user(UserDTO.buildFromEntity(user))
                .build()
        );
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> postForgotPassword(@RequestBody ForgotPasswordDTO forgotPasswordDto) throws UserNotFoundException {
        User user = userService.findByEmail(forgotPasswordDto.getEmail());
        PasswordToken pt = passwordTokenService.createAndSavePasswordToken(user);
        if(pt != null) return ResponseEntity.ok(pt.getToken());
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/reset-password/validate")
    public ResponseEntity<?> getValidatePasswordToken(@RequestParam String token) throws PasswordTokenNotFoundException {
        if (passwordTokenService.isTokenValid(token))
            return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> postResetPassword(@RequestBody ResetPasswordDTO data) throws NonMatchingPasswordException, InvalidPasswordTokenException, PasswordTokenNotFoundException {
        userService.updateUserPassword(data);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler({ NonMatchingPasswordException.class, InvalidPasswordTokenException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleExceptionWithBadRequest() {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler({ UserNotFoundException.class, PasswordTokenNotFoundException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handleExceptionWithNotFound() {
        return ResponseEntity.notFound().build();
    }

}
