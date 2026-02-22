package com.mappers;

import com.dtos.MovieDto;
import com.entities.Movie;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper {

    public MovieDto toDto(Movie movie) {
        if (movie == null) {
            return null;
        }
        MovieDto movieDto = new MovieDto();
        movieDto.setId(movie.getId());
        movieDto.setTitle(movie.getTitle());
        movieDto.setAnneeRealisation(movie.getAnneeRealisation());
        movieDto.setRealisateur(movie.getRealisateur());
        movieDto.setActeurs(movie.getActeurs());
        movieDto.setGenres(movie.getGenres());
        movieDto.setAgeMinimum(movie.getAgeMinimum());
        movieDto.setAffiches(movie.getAffiches());
        movieDto.setEstOuvertALaLocation(movie.isEstOuvertALaLocation());
        return movieDto;
    }

    public Movie toEntity(MovieDto movieDto) {
        if (movieDto == null) {
            return null;
        }

        Movie movie = new Movie();
        if (movieDto.getId() != null) {
            movie.setId(movieDto.getId());
        }
        movie.setTitle(movieDto.getTitle());
        movie.setAnneeRealisation(movieDto.getAnneeRealisation());
        movie.setRealisateur(movieDto.getRealisateur());
        movie.setActeurs(movieDto.getActeurs());
        movie.setGenres(movieDto.getGenres());
        movie.setAgeMinimum(movieDto.getAgeMinimum());
        movie.setAffiches(movieDto.getAffiches());
        movie.setEstOuvertALaLocation(movieDto.isEstOuvertALaLocation());
        return movie;
    }

}
