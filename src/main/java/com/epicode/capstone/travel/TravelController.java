package com.epicode.capstone.travel;

import com.epicode.capstone.category.Category;
import com.epicode.capstone.continent.Continent;
import com.epicode.capstone.security.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/travels")
@RequiredArgsConstructor
public class TravelController {

    private final TravelService travelService;

    //GET
    @GetMapping
    public ResponseEntity<List<CompleteResponse>> getAllTravels() {
        return ResponseEntity.ok(travelService.getAllTravels());
    }

    //GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<CompleteResponse> getTravelById(@PathVariable Long id) {
        try {
            CompleteResponse travel = travelService.getTravelById(id);
            return ResponseEntity.ok(travel);
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    //GET BY CONTINENT
    @GetMapping("/continent/{continentId}")
    public ResponseEntity<List<Travel>> findTravelsByContinent(@PathVariable Long continentId) {
        try {
            Continent continent = new Continent();
            continent.setId(continentId);
            List<Travel> travels = travelService.findTravelsByContinent(continent);
            return ResponseEntity.ok(travels);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    //GET BY CATEGORY
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Travel>> findTravelsByCategory(@PathVariable Long categoryId) {
        try {
            Category category = new Category();
            category.setId(categoryId);
            List<Travel> travels = travelService.findTravelsByCategory(category);
            return ResponseEntity.ok(travels);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    //GET BY IS SOLDOUT
    @GetMapping("/soldout/{isSoldOut}")
    public ResponseEntity<List<Travel>> findTravelsBySoldOut(@PathVariable Boolean isSoldOut) {
        try {
            List<Travel> travels = travelService.findTravelsByIsSoldOut(isSoldOut);
            return ResponseEntity.ok(travels);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    //GET BY PASSPORT IS REQUIRED
    @GetMapping("/passport/{passportIsRequired}")
    public ResponseEntity<List<Travel>> findTravelsByPassport(@PathVariable Boolean passportIsRequired) {
        try {
            List<Travel> travels = travelService.findTravelsByPassportIsRequired(passportIsRequired);
            return ResponseEntity.ok(travels);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    //GET BY MONTH
    @GetMapping("/month/{month}")
    public ResponseEntity<List<Travel>> findTravelsByMonth(@PathVariable Integer month) {
        try {
            List<Travel> travels = travelService.findTravelsByMonth(month);
            return ResponseEntity.ok(travels);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    //POST
    @PostMapping("/create")
    public ResponseEntity<Response> createTravel(@Validated @RequestBody Request request) {
        try {
            Response travel = travelService.createTravel(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(travel);
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    //POST TO PURCHASE A TRAVEL
    @PostMapping("/purchase/{travelId}")
    public ResponseEntity<String> purchaseTravel(@PathVariable Long travelId, @RequestBody Long user) {
        try {
            String result = travelService.purchaseTravel(travelId, user);
            return ResponseEntity.ok(result);
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    //POST TO ADD TRAVEL TO WISHLIST
    @PostMapping("/wishlist/{travelId}")
    public ResponseEntity<String> addTravelToWishlist(@PathVariable Long travelId, @RequestBody Long userId) {
        try {
            String result = travelService.addTravelToWishlist(travelId, userId);
            return ResponseEntity.ok(result);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    //PUT
    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateTravel(@PathVariable Long id, @Validated @RequestBody Request request) {
        try {
            Response travel = travelService.updateTravel(id, request);
            return ResponseEntity.ok(travel);
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    //DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTravel(@PathVariable Long id) {
        try {
            String result = travelService.deleteTravel(id);
            return ResponseEntity.ok(result);
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }
}

