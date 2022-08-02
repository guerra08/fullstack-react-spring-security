package com.example.demo.service;

import com.example.demo.contract.RegistrationRequest;
import com.example.demo.contract.ResetPasswordRequest;
import com.example.demo.entity.User;

public interface UserService {

    User createUser(RegistrationRequest data);
    User findByEmail(String email);
    void updateUserPassword(ResetPasswordRequest data);
}
