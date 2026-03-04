package com.dtos;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import lombok.Data;
import java.util.List;

@Data
public class MovieDto {

    private int id;

    @NotBlank(message = "The title is required.")
    private String title;

    @NotBlank(message = "The year of completion is required.")
    private String yearCompletion;

    @NotBlank(message = "The director's name is required.")
    private String director;

    private List<String> actors;

    private List<String> genres;

    @Min(value = 0, message = "The minimum age cannot be less than 0 years old.")
    private Long minimumAge;

    private List<String> posters;

    private boolean openForRent;
}
