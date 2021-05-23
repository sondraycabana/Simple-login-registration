package com.kb.simpleloginregistration.service;

import com.kb.simpleloginregistration.data.model.Role;
import com.kb.simpleloginregistration.data.model.User;
import com.kb.simpleloginregistration.data.repository.UserRepository;

import com.kb.simpleloginregistration.service.exceptions.RegisterException;
import com.kb.simpleloginregistration.web.dto.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Override
    public User register(UserRegistrationDto registrationDto) throws RegisterException {
        checkForDuplicates(registrationDto);
        User user = new User();
                user.setFirstName(registrationDto.getFirstName());
                user.setLastName(registrationDto.getLastName());
                user.setEmail(registrationDto.getEmail());
                user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
                user.setRoles(Arrays.asList(new Role("ROLE_USER")));

        return  save(user);
    }

    private void checkForDuplicates(UserRegistrationDto registrationDto) throws RegisterException {

        Optional<User> optionalUser = userRepository.findByEmail(registrationDto.getEmail());
        if(optionalUser.isPresent()){

            throw new RegisterException("User with this email address exists");
        }
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {

        return userRepository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       Optional<User> user  = userRepository.findByEmail(email);
       if(user == null){
           throw new UsernameNotFoundException("Invalid username or password");
       }
       return new org.springframework.security.core.userdetails.User(
               user.get().getEmail(), user.get().getPassword(),mapRolesToAuthorities(user.get().getRoles()));


    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role>  roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
