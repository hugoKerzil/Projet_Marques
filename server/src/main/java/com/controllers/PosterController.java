package com.controllers;

import com.dtos.PosterDto;
import com.services.PosterService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PosterController {

    private final PosterService posterService;

    public PosterController(PosterService posterService) { this.posterService = posterService; }

    /**
     * <p>Get all posters from a movie based on the ID</p>
     * @return List<PosterDto>
     */
    @GetMapping("/movie/{movieId}/posters")
    public List<PosterDto> getPostersMovie(@PathVariable Long movieId) { return posterService.getAllPostersMovie(movieId); }

    /**
     * Method to get the Poster based on the ID
     */
    @GetMapping("/posters/{id}")
    public PosterDto getPoster(@PathVariable Long id) { return posterService.getPosterById(id); }

    /**
     * Create a new Poster in the system
     */
    @PostMapping("/posters")
    public PosterDto savePoster(final @RequestBody PosterDto posterDto) { return posterService.savePoster(posterDto); }

    /**
     * Update a Poster by it's id
     */
    @PutMapping("/posters/{id}")
    public PosterDto updatePoster(@PathVariable Long id, @RequestBody PosterDto posterDto) { return posterService.updatePoster(id, posterDto); }

    /**
     * Delete a Poster by it's id
     */
    @DeleteMapping("/posters/{id}")
    public Boolean deletePoster(@PathVariable Long id) { return posterService.deletePoster(id); }

}
