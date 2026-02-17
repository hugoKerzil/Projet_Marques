package com.services;

import com.entities.Movie;
import com.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie createMovie(Movie movie) {
        movie.setEstOuvertALaLocation(false);
        return movieRepository.save(movie);
    }

    public void toggleLocationStatus(int movieId, boolean status) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Film non trouvé"));
        movie.setEstOuvertALaLocation(status);
        movieRepository.save(movie);
    }

}
