package com.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private String yearCompletion;

    private String director;

    @ElementCollection
    private List<String> actors;

    @ElementCollection
    private List<String> genres;

    private Long minimumAge;

    @ElementCollection
    private List<String> posters;

    private boolean isOpenForRent;
}