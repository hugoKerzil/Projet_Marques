package com.entities;

import lombok.Data;

@Data
public class Poster {

    private Long id;

    private Long movieId;

    private String url;

    private String description;

}
