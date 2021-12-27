package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthDTO {

    private TokenDTO token;
    private UserDTO user;

}
