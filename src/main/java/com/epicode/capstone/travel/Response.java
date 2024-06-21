package com.epicode.capstone.travel;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
public class Response {
    private Long id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer availableSeats;
    private Double price;
    private String place;
    private com.epicode.capstone.continent.Response continent;
    private boolean passportIsRequired;
    private List<String> whatsIncluded;
    private Map<Integer, String> itinerary;
    //private com.epicode.capstone.photo.Response photos;
    private com.epicode.capstone.category.Response category;
    private boolean isSoldOut = false;
}
