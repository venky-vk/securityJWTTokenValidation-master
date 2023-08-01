package com.jwttoken31.securityJWTTokenValidation.controller;

import com.jwttoken31.securityJWTTokenValidation.entities.User;
import com.jwttoken31.securityJWTTokenValidation.model.JwtResponse;
import com.jwttoken31.securityJWTTokenValidation.model.Jwtrequest;
import com.jwttoken31.securityJWTTokenValidation.repository.UserRepository;
import com.jwttoken31.securityJWTTokenValidation.security.JWTHelper;
import com.jwttoken31.securityJWTTokenValidation.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JWTHelper jwtHelper;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody Jwtrequest request){

        doAuthenticate(request.getUserName(), request.getPassword());
        System.out.println("request.getUserName()"+request.getUserName());
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUserName());

        String token = jwtHelper.generateToken(userDetails);

        JwtResponse response= JwtResponse.builder().jwtToken(token)
                .username(userDetails.getUsername())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    private void doAuthenticate(String userName, String password){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName,password);
        try{
            manager.authenticate(authenticationToken);
        }catch (BadCredentialsException e){
            throw new BadCredentialsException("Invalid UserName or Password !!");
        }
    }


    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler(){
        return "Credentials Invalid !!";
    }

    @PostMapping("/createUser")
    public User createUser(@RequestBody User user){
        return userService.creatUser(user);
    }
}
