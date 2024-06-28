package com.epicode.capstone.travel;

import com.epicode.capstone.category.Category;
import com.epicode.capstone.continent.Continent;
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
    private Continent continent;
    private boolean passportIsRequired;
    private List<String> whatsIncluded;
    private Map<Integer, String> itinerary;
    private List<String> photos;
    //private String photo;
    private Category category;
    private boolean isSoldOut = false;
}
