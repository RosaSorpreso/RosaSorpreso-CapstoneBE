package com.epicode.capstone.continent;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/continents")
@RequiredArgsConstructor
public class ContinentController {

    private final ContinentService continentService;

    //GET
    @GetMapping
    public ResponseEntity<List<Continent>> getAllContinents() {
        List<Continent> continents = continentService.getAllContinents();
        return ResponseEntity.ok(continents);
    }

    //GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Continent> getContinentById(@PathVariable Long id) {
        try {
            Continent continent = continentService.getContinentById(id);
            return ResponseEntity.ok(continent);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
