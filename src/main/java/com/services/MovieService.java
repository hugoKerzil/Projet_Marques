package com.services;

import com.dtos.MovieDto;
import java.util.List;

public interface MovieService {
    MovieDto saveMovie(MovieDto movieDto);
    MovieDto getMovieById(Long id);
    MovieDto getMovieByTitle(String title);
    List<MovieDto> getAllMovies();
    MovieDto updateMovie(Long id, MovieDto movieDto);
    boolean deleteMovie(Long id);
}