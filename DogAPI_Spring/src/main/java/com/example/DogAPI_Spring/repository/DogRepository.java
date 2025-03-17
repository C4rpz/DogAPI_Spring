package com.example.DogAPI_Spring.repo;

import com.example.DogAPI_Spring.entity.Dog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DogRepository extends JpaRepository<Dog, Long> {
    boolean existsByBreed(String breed);
}