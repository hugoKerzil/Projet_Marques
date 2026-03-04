package com.mappers;

import com.dtos.MovieDto;
import com.entities.Movie;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper {

    /**
     * <p>Method for converting a movie entity into a DTO</p>
     * @return MovieDto
     */
    public MovieDto toDto(Movie movie) {
        if (movie == null) {
            return null;
        }
        MovieDto movieDto = new MovieDto();
        movieDto.setId(movie.getId());
        movieDto.setTitle(movie.getTitle());
        movieDto.setYearCompletion(movie.getYearCompletion());
        movieDto.setDirector(movie.getDirector());
        movieDto.setActors(movie.getActors());
        movieDto.setGenres(movie.getGenres());
        movieDto.setMinimumAge(movie.getMinimumAge());
        movieDto.setPosters(movie.getPosters());
        movieDto.setIsOpenForRent(movie.isOpenForRent());
        return movieDto;
    }

    /**
     * <p>Method for converting a DTO object into a movie entity</p>
     * @return Movie
     */
    public static Movie toEntity(MovieDto movieDto) {
        if (movieDto == null) {
            return null;
        }

        Movie movie = new Movie();
        if (movieDto.getId() != -1) {
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
