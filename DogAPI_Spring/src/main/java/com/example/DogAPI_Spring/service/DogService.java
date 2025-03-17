package com.example.DogAPI_Spring.service;

import com.example.DogAPI_Spring.entity.Dog;
import com.example.DogAPI_Spring.repo.DogRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DogService {

    private final DogRepository dogRepository;

    public DogService(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }

    public Optional<Dog> getDogById(Long id) {
        return dogRepository.findById(id);
    }

    public Iterable<Dog> getAllDogs() {
        return dogRepository.findAll();
    }

    public void createDog(Dog dog) {
        dogRepository.save(dog);
    }

    public void deleteDog(Long id) {
        if (dogRepository.existsById(id)) {
            dogRepository.deleteById(id);
        }
    }

    public boolean updateDog(Long id, Dog updatedDog) {
        return dogRepository.findById(id).map(existingDog -> {
            existingDog.setBreed(updatedDog.getBreed());
            existingDog.setImg_url(updatedDog.getImg_url());
            dogRepository.save(existingDog);
            return true;
        }).orElse(false);
    }

    public boolean existsByBreed(String breed) {
        return dogRepository.existsByBreed(breed);
    }
}
