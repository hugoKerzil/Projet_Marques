package com.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "movie")
@Data
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    @Column(name = "year_completion")
    private String yearCompletion;

    private String director;

    @ElementCollection
    @CollectionTable(name = "movie_actors", joinColumns = @JoinColumn(name = "movie_id"))
    @Column(name = "actors")
    private List<String> actors;

    @ElementCollection
    @CollectionTable(name = "movie_genres", joinColumns = @JoinColumn(name = "movie_id"))
    @Column(name = "genres")
    private List<String> genres;

    @Column(name = "minimum_age")
    private Long minimumAge;

    @ElementCollection
    @CollectionTable(name = "movie_posters", joinColumns = @JoinColumn(name = "movie_id"))
    @Column(name = "posters")
    private List<String> posters;

    @Column(name = "open_for_rent")
    private boolean openForRent;
}