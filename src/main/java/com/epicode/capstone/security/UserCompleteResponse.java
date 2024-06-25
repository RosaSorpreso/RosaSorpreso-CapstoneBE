package com.epicode.capstone.security;

import com.epicode.capstone.travel.Response;
import lombok.Data;

import java.util.List;

@Data
public class UserCompleteResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private List<Response> travelsPurchased;
    private List<Response> wishlist;
}
