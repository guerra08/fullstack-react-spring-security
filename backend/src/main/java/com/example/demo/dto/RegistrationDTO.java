package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistrationDTO {

    private String email;
    private String password;
    private String name;

}
