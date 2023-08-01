package com.jwttoken31.securityJWTTokenValidation.services;

import com.jwttoken31.securityJWTTokenValidation.entities.User;
import com.jwttoken31.securityJWTTokenValidation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //load user from database
        System.out.println("UserName : {}"+username);
        return userRepository.findByName(username).orElseThrow(()-> new RuntimeException("User not found !!"));
    }
}
