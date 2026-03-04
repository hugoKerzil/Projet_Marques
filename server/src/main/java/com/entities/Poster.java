package com.entities;

import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Document(collection = "poster")
public class Poster {

    @Id
    private Long id;

    private Long movieId;

    private String url;

    private String description;
}