package com.example.DogAPI_Spring.repository;


import com.example.DogAPI_Spring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByClaimToken(String token);
}
