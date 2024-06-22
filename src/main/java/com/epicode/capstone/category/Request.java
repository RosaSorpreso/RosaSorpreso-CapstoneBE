package com.epicode.capstone.category;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Request {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
}
