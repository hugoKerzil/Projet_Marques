package com.dtos;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import lombok.Data;
import java.util.List;

@Data
public class MovieDto {

    private int id;

    @NotBlank(message = "Le titre est obligatoire")
    private String title;

    @NotBlank(message = "L'année de réalisation est obligatoire")
    private String yearCompletion;

    @NotBlank(message = "Le nom du réalisateur")
    private String director;

    private List<String> actors;

    private List<String> genres;

    @Min(value = 0, message = "L'âge minimum ne peut pas être inférieur à 0 an")
    private Long minimumAge;

    private List<String> posters;

    private boolean isOpenForRent;
}
