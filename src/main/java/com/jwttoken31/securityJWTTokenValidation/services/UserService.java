package com.jwttoken31.securityJWTTokenValidation.services;

import com.jwttoken31.securityJWTTokenValidation.entities.User;
import com.jwttoken31.securityJWTTokenValidation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
   // private List<User> store=new ArrayList<>();

   /* private UserService(){
        store.add(new User(UUID.randomUUID().toString(),"venky","venky@gmail.com"));
        store.add(new User(UUID.randomUUID().toString(),"manju","manju@gmail.com"));
        store.add(new User(UUID.randomUUID().toString(),"prabhakar","prabhakar@gmail.com"));
        store.add(new User(UUID.randomUUID().toString(),"swapnil","swapnil@gmail.com"));

    }*/

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public User creatUser(User user){
        user.setUserId(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
