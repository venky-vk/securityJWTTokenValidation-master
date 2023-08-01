package com.jwttoken31.securityJWTTokenValidation.repository;

import com.jwttoken31.securityJWTTokenValidation.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    public Optional<User> findByName(String name);

}
