package com.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PosterDto {

    private Long id;

    @NotNull(message = "L'identifiant du film est obligatoire")
    private Long movieId;

    @NotBlank(message = "L'URL est obligatoire")
    private String url;

    private String description;

}
