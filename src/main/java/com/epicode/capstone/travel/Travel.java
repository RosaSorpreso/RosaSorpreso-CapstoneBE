package com.epicode.capstone.travel;

import com.epicode.capstone.category.Category;
import com.epicode.capstone.continent.Continent;
import com.epicode.capstone.security.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Entity
@Data
@Table(name = "travels")
@NoArgsConstructor
public class Travel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    private Integer availableSeats;

    private boolean isSoldOut = false;

    @NotNull
    @DecimalMin("0.0")
    private Double price;

    @NotBlank
    private String place;

    @ManyToOne
    @JoinColumn(name = "continent_id")
    private Continent continent;

    private boolean passportIsRequired;

    @NotNull
    @ElementCollection
    private List<String> whatsIncluded = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "itinerary", joinColumns = @JoinColumn(name = "travel_id"))
    @MapKeyColumn(name = "day")
    @Column(name = "description")
    private Map<Integer, String> itinerary;

    @NotNull
    @ElementCollection
    private List<String> photos = new ArrayList<>();

    @NotNull
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToMany
    @JoinTable(name = "travel_users",
            joinColumns = @JoinColumn(name = "travel_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> travelers = new ArrayList<>();

}
