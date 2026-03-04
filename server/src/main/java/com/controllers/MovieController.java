package com.controllers;

import com.dtos.MovieDto;
import com.services.MovieService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) { this.movieService = movieService; }

    /**
     * <p>Get all movies in the system</p>
     * @return List<MovieDto>
     */
    @GetMapping
    public List<MovieDto> getMovies() { return movieService.getAllMovies(); }

    /**
     * <p>Method to get the Movie based on the ID</p>
     * @return MovieDto
     */
    @GetMapping("/{id}")
    public MovieDto getMovie(@PathVariable int id) { return movieService.getMovieById(id); }

    /**
     * <p>Create a new Movie in the system</p>
     * @return MovieDto
     */
    @PostMapping
    public MovieDto saveMovie(final @RequestBody MovieDto movieDto) { return movieService.saveMovie(movieDto); }

    /**
     * <p>Update a Movie by it's id</p>
     * @return MovieDto
     */
    @PutMapping("/{id}")
    public MovieDto updateMovie(@PathVariable int id, @RequestBody MovieDto movieDto) { return movieService.updateMovie(id, movieDto); }

    /**
     * <p>Delete a Movie by it's id</p>
     * @return Boolean
     */
    @DeleteMapping("/{id}")
    public Boolean deleteMovie(@PathVariable int id) { return movieService.deleteMovie(id); }
}
