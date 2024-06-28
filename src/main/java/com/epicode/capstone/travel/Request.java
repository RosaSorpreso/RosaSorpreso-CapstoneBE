package com.epicode.capstone.travel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Request {
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer availableSeats;
    private Double price;
    private String place;
    private Long idContinent;
    private boolean passportIsRequired;
    private List<String> whatsIncluded;
    private Map<Integer, String> itinerary;
    private Long idCategories;
}
