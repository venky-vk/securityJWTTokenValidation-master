package com.jwttoken31.securityJWTTokenValidation.controller;

import com.jwttoken31.securityJWTTokenValidation.entities.User;
import com.jwttoken31.securityJWTTokenValidation.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> getUser(){
        System.out.println("getting users");
        return userService.getUsers();
    }

    @GetMapping("/current-user")
    public String loggedInUser(Principal principal){
        return principal.getName();
    }

}
