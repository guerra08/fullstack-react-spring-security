package com.example.demo.dto;

import lombok.Data;

@Data
public class ResetPasswordDTO {

    private String token;
    private String updatedPassword;
    private String updatedPasswordConfirmation;

}
