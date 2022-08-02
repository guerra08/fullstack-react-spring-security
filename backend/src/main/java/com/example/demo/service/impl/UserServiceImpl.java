package com.example.demo.service.impl;

import com.example.demo.contract.RegistrationRequest;
import com.example.demo.contract.ResetPasswordRequest;
import com.example.demo.entity.PasswordToken;
import com.example.demo.entity.User;
import com.example.demo.exception.InvalidPasswordTokenException;
import com.example.demo.exception.NonMatchingPasswordException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordTokenServiceImpl passwordTokenServiceImpl;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found."));
    }

    @Override
    public User createUser(RegistrationRequest data){
        User user = new User();
        user.setEmail(data.email());
        user.setPassword(passwordEncoder.encode(data.password()));
        user.setName(data.name());
        return userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public void updateUserPassword(ResetPasswordRequest data) {
        if(!data.updatedPassword().equals(data.updatedPasswordConfirmation()))
            throw new NonMatchingPasswordException();
        PasswordToken pt = passwordTokenServiceImpl.getPasswordTokenByTokenString(data.token());
        if(!pt.isTokenValid()){ throw new InvalidPasswordTokenException(); }
        User toUpdate = pt.getUser();
        toUpdate.setPassword(passwordEncoder.encode(data.updatedPasswordConfirmation()));
        userRepository.save(toUpdate);
    }
}
