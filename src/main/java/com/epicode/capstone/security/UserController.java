package com.epicode.capstone.security;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService user;

    @GetMapping
    public ResponseEntity<List<UserCompleteResponse>> getAllUsers() {
        return ResponseEntity.ok(user.getUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserCompleteResponse> getUserById(@PathVariable Long id) {
        try {
            UserCompleteResponse userResponse = user.getUserById(id);
            return ResponseEntity.ok(userResponse);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/register")
    public ResponseEntity<RegisteredUserDTO> register(@RequestBody @Validated RegisterUserModel model, BindingResult validator){
        if (validator.hasErrors()) {
            throw new ApiValidationException(validator.getAllErrors());
        }
        var registeredUser = user.register(
                RegisterUserDTO.builder()
                        .withFirstName(model.firstName())
                        .withLastName(model.lastName())
                        .withUsername(model.username())
                        .withEmail(model.email())
                        .withPassword(model.password())
                        .build());

        return  new ResponseEntity<> (registeredUser, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Validated LoginModel model, BindingResult validator) {
        if (validator.hasErrors()) {
            throw  new ApiValidationException(validator.getAllErrors());
        }
        return new ResponseEntity<>(user.login(model.username(), model.password()).orElseThrow(), HttpStatus.OK);
    }

    //@PostMapping("/registerAdmin")
    //public ResponseEntity<RegisteredUserDTO> registerAdmin(@RequestBody RegisterUserDTO registerUser){
        //return ResponseEntity.ok(user.registerAdmin(registerUser));
    //}

    @PutMapping("/update/{id}")
    public ResponseEntity<RegisteredUserDTO> updateUser(@PathVariable Long id, @RequestBody @Validated RegisterUserDTO updateUserDTO, BindingResult validator){
        if (validator.hasErrors()) {
            throw new ApiValidationException(validator.getAllErrors());
        }
        RegisteredUserDTO updatedUser = user.updateUser(id, updateUserDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            String result = user.deleteUserById(id);
            return ResponseEntity.ok(result);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}

