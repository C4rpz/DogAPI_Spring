package com.example.DogAPI_Spring.controller;

import com.example.DogAPI_Spring.dto.DogDTO;
import com.example.DogAPI_Spring.entity.Dog;
import com.example.DogAPI_Spring.service.DogService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/dogs")
public class DogAPIController {

    private final DogService dogService;
    private final RestTemplate restTemplate;

    public DogAPIController(DogService dogService) {
        this.dogService = dogService;
        this.restTemplate = new RestTemplate();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("This is ok, you can get in");
    }

    @PreAuthorize("hasRole('API_CALL')")
    @GetMapping("/apicall/getAllBreeds")
    public ResponseEntity<Map<String, Object>> getAllBreeds() {
        return restTemplate.getForEntity("https://dog.ceo/api/breeds/list/all", Map.class);
    }

    @PreAuthorize("hasRole('API_CALL')")
    @GetMapping("/apicall/getRandomBreed")
    public ResponseEntity<String> getRandomBreed() {
        Map response = restTemplate.getForObject("https://dog.ceo/api/breed/mix/images/random", Map.class);
        return response != null && response.containsKey("message") ?
                ResponseEntity.ok(response.get("message").toString()) :
                ResponseEntity.badRequest().body("Erreur : impossible de récupérer l'image");
    }

    @PreAuthorize("hasRole('SCRAPER')")
    @GetMapping("/scrapDog")
    public ResponseEntity<String> scrapDog() {
        Map response = restTemplate.getForObject("https://dog.ceo/api/breeds/list/all", Map.class);
        if (response == null || !response.containsKey("message")) {
            return ResponseEntity.badRequest().body("Erreur lors de la récupération des races");
        }

        Map<String, List<String>> breeds = (Map<String, List<String>>) response.get("message");
        breeds.forEach((breed, subBreeds) -> {
            subBreeds.forEach(sub -> saveBreedIfNotExists(breed + " " + sub));
            if (subBreeds.isEmpty()) saveBreedIfNotExists(breed);
        });

        return ResponseEntity.ok("Scraping successful: " + breeds.toString());
    }

    private void saveBreedIfNotExists(String breedName) {
        if (!dogService.existsByBreed(breedName)) {
            dogService.createDog(new Dog(breedName));
        }
    }

    @PreAuthorize("hasRole('CRUD')")
    @PostMapping("/crud/createDog")
    public ResponseEntity<String> createDog(@RequestBody DogDTO dogDTO) {
        dogService.createDog(new Dog(dogDTO.getBreed(), dogDTO.getImg_url()));
        return ResponseEntity.ok("Dog created successfully");
    }

    @PreAuthorize("hasRole('CRUD')")
    @GetMapping("/crud/getDogById/{id}")
    public ResponseEntity<Dog> getDogById(@PathVariable Long id) {
        return ResponseEntity.of(Optional.ofNullable(dogService.getDogById(id)));
    }

    @PreAuthorize("hasRole('CRUD')")
    @GetMapping("/crud/getAllDogs")
    public ResponseEntity<Iterable<Dog>> getAllDogs() {
        return ResponseEntity.ok(dogService.getAllDogs());
    }

    @PreAuthorize("hasRole('CRUD')")
    @PutMapping("/crud/updateDog/{id}")
    public ResponseEntity<String> updateDog(@PathVariable Long id, @RequestBody DogDTO dogDTO) {
        dogService.updateDog(id, new Dog(dogDTO.getBreed(), dogDTO.getImg_url()));
        return ResponseEntity.ok("Dog updated successfully");
    }

    @PreAuthorize("hasRole('CRUD')")
    @DeleteMapping("/crud/deleteDog/{id}")
    public ResponseEntity<String> deleteDog(@PathVariable Long id) {
        dogService.deleteDog(id);
        return ResponseEntity.ok("Dog deleted successfully");
    }
}
