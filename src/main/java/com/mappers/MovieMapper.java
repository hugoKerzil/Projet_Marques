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
        MovieDto dto = new MovieDto();
        dto.setId(movie.getId());
        dto.setTitle(movie.getTitle());
        dto.setAnneeRealisation(movie.getAnneeRealisation());
        dto.setRealisateur(movie.getRealisateur());
        dto.setActeurs(movie.getActeurs());
        dto.setGenres(movie.getGenres());
        dto.setAgeMinimum(movie.getAgeMinimum());
        dto.setAffiche(movie.getAffiche());
        dto.setEstOuvertALaLocation(movie.isEstOuvertALaLocation());
        return dto;
    }

    public Movie toEntity(MovieDto dto) {
        if (dto == null) {
            return null;
        }
        Movie movie = new Movie();
        if (dto.getId() != null) {
            movie.setId(dto.getId());
        }
        movie.setTitle(dto.getTitle());
        movie.setAnneeRealisation(dto.getAnneeRealisation());
        movie.setRealisateur(dto.getRealisateur());
        movie.setActeurs(dto.getActeurs());
        movie.setGenres(dto.getGenres());
        movie.setAgeMinimum(dto.getAgeMinimum());
        movie.setAffiche(dto.getAffiche());
        movie.setEstOuvertALaLocation(dto.isEstOuvertALaLocation());
        return movie;
    }

}
