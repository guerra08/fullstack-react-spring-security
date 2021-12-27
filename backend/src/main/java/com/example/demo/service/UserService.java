package com.example.demo.service;

import com.example.demo.dto.RegistrationDTO;
import com.example.demo.dto.ResetPasswordDTO;
import com.example.demo.entity.PasswordToken;
import com.example.demo.entity.User;
import com.example.demo.exception.InvalidPasswordTokenException;
import com.example.demo.exception.NonMatchingPasswordException;
import com.example.demo.exception.PasswordTokenNotFoundException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    PasswordTokenService passwordTokenService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found."));
    }

    public User createUser(RegistrationDTO data){
        User user = new User();
        user.setEmail(data.getEmail());
        user.setPassword(passwordEncoder.encode(data.getPassword()));
        user.setName(data.getName());
        return userRepository.save(user);
    }

    public User findByEmail(String email) throws UserNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    public void updateUserPassword(ResetPasswordDTO data) throws PasswordTokenNotFoundException, InvalidPasswordTokenException, NonMatchingPasswordException {
        if(!data.getUpdatedPassword().equals(data.getUpdatedPasswordConfirmation()))
            throw new NonMatchingPasswordException();
        PasswordToken pt = passwordTokenService.getPasswordTokenByToken(data.getToken());
        if(!pt.isTokenValid()){ throw new InvalidPasswordTokenException(); }
        User toUpdate = pt.getUser();
        toUpdate.setPassword(passwordEncoder.encode(data.getUpdatedPasswordConfirmation()));
        userRepository.save(toUpdate);
    }
}
