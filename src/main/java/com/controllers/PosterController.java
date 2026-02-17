package com.controllers;

import com.dtos.MovieDto;
import com.dtos.PosterDto;
import com.services.impl.MovieServiceImpl;
import com.services.impl.PosterServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posters")
public class PosterController {

    private final PosterServiceImpl posterService;

    public PosterController(PosterServiceImpl posterService) { this.posterService = posterService; }

    /**
     * Method to get the Poster based on the ID
     */
    @GetMapping("/{id}")
    public PosterDto getPoster(@PathVariable Long id) { return posterService.getPosterById(id); }

    /**
     * Create a new Poster in the system
     */
    @PostMapping
    public PosterDto savePoster(final @RequestBody PosterDto posterDto) { return posterService.savePoster(posterDto); }

}
