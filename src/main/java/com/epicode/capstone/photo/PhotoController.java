package com.epicode.capstone.photo;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/photos")
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoService photoService;

    //GET
    @GetMapping
    public ResponseEntity<List<Photo>> getAllPhotos() {
        return ResponseEntity.ok(photoService.getAllPhotos());
    }

    //GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Response> getPhotoById(@PathVariable Long id) {
        try {
            Response photo = photoService.getPhotoById(id);
            return ResponseEntity.ok(photo);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //POST
    @PostMapping
    public ResponseEntity<Response> savePhoto(@Validated @RequestBody Request request) {
        try {
            Response photo = photoService.savePhoto(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(photo);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //DELETE
    @DeleteMapping
    public ResponseEntity<String> deletePhotoById(@PathVariable Long id) {
        try {
            String result = photoService.deletePhoto(id);
            return ResponseEntity.ok(result);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
