package com.services;

import com.dtos.MovieDto;
import java.util.List;

public interface MovieService {
    MovieDto saveMovie(MovieDto movieDto);
    MovieDto getMovieById(Integer id);
    MovieDto getMovieByTitle(String title);
    List<MovieDto> getAllMovies();
    MovieDto updateMovie(Integer id, MovieDto movieDto);
    boolean deleteMovie(Integer id);
}