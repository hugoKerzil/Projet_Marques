package com.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Poster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "movie_id")
    private Long movieId;

    private String url;

    private String description;
}
