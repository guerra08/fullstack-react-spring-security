package com.example.demo.dto;

import com.example.demo.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {

    private String name;

    public static UserDTO buildFromEntity(User user){
        return UserDTO.builder().name(user.getName()).build();
    }

}
