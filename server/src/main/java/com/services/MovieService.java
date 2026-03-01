package com.services;

import com.dtos.MovieDto;
import java.util.List;

public interface MovieService {
    MovieDto saveMovie(MovieDto movieDto);
    MovieDto getMovieById(int id);
    MovieDto getMovieByTitle(String title);
    List<MovieDto> getAllMovies();
    MovieDto updateMovie(int id, MovieDto movieDto);
    boolean deleteMovie(int id);
}