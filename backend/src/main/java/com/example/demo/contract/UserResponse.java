package com.example.demo.contract;

import com.example.demo.entity.User;

public record UserResponse(String name) {

    public static UserResponse fromEntity(User user){
        return new UserResponse(user.getName());
    }

}
