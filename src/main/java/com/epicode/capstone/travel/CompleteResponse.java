package com.epicode.capstone.travel;

import com.epicode.capstone.category.Category;
import com.epicode.capstone.continent.Continent;
import com.epicode.capstone.security.UserResponse;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class CompleteResponse {
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
    private Category category;
    private boolean isSoldOut = false;
    private List<UserResponse> travelers = new ArrayList<>();
}
