package com.example.demo.contract;

public record ResetPasswordRequest(String token, String updatedPassword, String updatedPasswordConfirmation) { }
