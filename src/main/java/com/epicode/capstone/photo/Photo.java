package com.epicode.capstone.photo;

import com.epicode.capstone.travel.Travel;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String url;

    @ManyToOne
    @JoinColumn(name = "travel_id")
    private Travel travel;
}
