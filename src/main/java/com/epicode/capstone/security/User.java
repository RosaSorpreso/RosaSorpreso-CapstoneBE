package com.epicode.capstone.security;

import com.epicode.capstone.travel.Travel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(length = 50, nullable = false)
    private String firstName;

    @Column(length = 50, nullable = false)
    private String lastName;

    private String username;

    private String email;

    @Column(length = 125, nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private final List<Roles> roles = new ArrayList<>();

    @ManyToMany(mappedBy = "travelers")
    private List<Travel> travelsPurchased;

    @ManyToMany
    @JoinTable(name = "user_wishlist",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "travel_id"))
    private List<Travel> wishlist;
}