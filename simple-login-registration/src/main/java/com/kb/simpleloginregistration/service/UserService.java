package com.kb.simpleloginregistration.service;

import com.kb.simpleloginregistration.data.model.User;

import com.kb.simpleloginregistration.service.exceptions.RegisterException;
import com.kb.simpleloginregistration.web.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;


public interface UserService extends UserDetailsService {
    User save(User user) ;
    Optional<User> findByEmail(String email);
    User register(UserRegistrationDto registrationDto) throws RegisterException;
}
