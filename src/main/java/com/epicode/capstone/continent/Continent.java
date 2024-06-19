package com.epicode.capstone.continent;

import com.epicode.capstone.travel.Travel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Continent {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank
    private String name;

    @ManyToMany(mappedBy = "continents")
    private List<Travel> travels;
}
