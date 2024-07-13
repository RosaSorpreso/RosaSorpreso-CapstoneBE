package com.epicode.capstone.travel;

import com.epicode.capstone.category.Category;
import com.epicode.capstone.continent.Continent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public ResponseEntity<List<Response>> findTravelsByContinent(@PathVariable Long continentId) {
        try {
            Continent continent = new Continent();
            continent.setId(continentId);
            List<Travel> travels = travelService.findTravelsByContinent(continent);
            List<Response> responses = TravelMapper.INSTANCE.travelsToResponses(travels);
            return ResponseEntity.ok(responses);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    //GET BY CATEGORY
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Response>> findTravelsByCategory(@PathVariable Long categoryId) {
        try {
            Category category = new Category();
            category.setId(categoryId);
            List<Travel> travels = travelService.findTravelsByCategory(category);
            List<Response> responses = TravelMapper.INSTANCE.travelsToResponses(travels);
            return ResponseEntity.ok(responses);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    //GET BY IS SOLDOUT
    @GetMapping("/soldout/{isSoldOut}")
    public ResponseEntity<List<Response>> findTravelsBySoldOut(@PathVariable Boolean isSoldOut) {
        try {
            List<Travel> travels = travelService.findTravelsByIsSoldOut(isSoldOut);
            List<Response> responses = TravelMapper.INSTANCE.travelsToResponses(travels);
            return ResponseEntity.ok(responses);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    //GET BY PASSPORT IS REQUIRED
    @GetMapping("/passport/{passportIsRequired}")
    public ResponseEntity<List<Response>> findTravelsByPassport(@PathVariable Boolean passportIsRequired) {
        try {
            List<Travel> travels = travelService.findTravelsByPassportIsRequired(passportIsRequired);
            List<Response> responses = TravelMapper.INSTANCE.travelsToResponses(travels);
            return ResponseEntity.ok(responses);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    //GET BY MONTH
    @GetMapping("/month/{month}")
    public ResponseEntity<List<Response>> findTravelsByMonth(@PathVariable Integer month) {
        try {
            List<Travel> travels = travelService.findTravelsByMonth(month);
            List<Response> responses = TravelMapper.INSTANCE.travelsToResponses(travels);
            return ResponseEntity.ok(responses);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    //POST
    @PostMapping(value = "/create", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Response> createTravel(@RequestPart("travel") String travelJson, @RequestPart("file") MultipartFile[] files) throws IOException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            Request request = objectMapper.readValue(travelJson, Request.class);
            Response travel = travelService.createTravel(request, files);
            return ResponseEntity.status(HttpStatus.CREATED).body(travel);
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
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

    //POST TO REMOVE TRAVEL FROM WISHLIST
    @PostMapping("/wishlist/remove/{travelId}")
    public ResponseEntity<String> removeTravelFromWishlist(@PathVariable Long travelId, @RequestBody Long userId) {
        try {
            String result = travelService.removeTravelFromWishlist(travelId, userId);
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

