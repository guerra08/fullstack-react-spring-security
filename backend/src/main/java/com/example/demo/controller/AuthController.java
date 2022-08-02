package com.example.demo.controller;

import com.example.demo.contract.*;
import com.example.demo.entity.PasswordToken;
import com.example.demo.entity.User;
import com.example.demo.exception.InvalidPasswordTokenException;
import com.example.demo.exception.NonMatchingPasswordException;
import com.example.demo.exception.PasswordTokenNotFoundException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.service.PasswordTokenService;
import com.example.demo.service.TokenService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final TokenService tokenService;
    private final PasswordTokenService passwordTokenService;

    @PostMapping
    public ResponseEntity<?> auth(@RequestBody @Validated LoginRequest payload){
        UsernamePasswordAuthenticationToken upt =
            new UsernamePasswordAuthenticationToken(payload.email(), payload.password());
        Authentication auth = authenticationManager.authenticate(upt);
        User user = (User) auth.getPrincipal();
        String token = tokenService.generateToken(auth);
        return ResponseEntity.ok(
            new AuthResponse(new TokenResponse("Bearer", token), UserResponse.fromEntity(user))
        );
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> postForgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        User user = userService.findByEmail(forgotPasswordRequest.email());
        PasswordToken pt = passwordTokenService.createPasswordTokenForUser(user);
        if(pt != null) return ResponseEntity.ok(pt.getToken());
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/reset-password/validate")
    public ResponseEntity<?> getValidatePasswordToken(@RequestParam String token) {
        if (passwordTokenService.isTokenValid(token))
            return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> postResetPassword(@RequestBody ResetPasswordRequest data) {
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
