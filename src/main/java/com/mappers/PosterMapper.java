package com.mappers;

import com.dtos.PosterDto;
import com.entities.Poster;
import org.springframework.stereotype.Component;

@Component
public class PosterMapper {

    public PosterDto toDto(Poster poster) {
        if (poster == null) {
            return null;
        }
        PosterDto dto = new PosterDto();
        dto.setId(poster.getId());
        dto.setMovieId(poster.getMovieId());
        dto.setUrl(poster.getUrl());
        dto.setDescription(poster.getDescription());
        return dto;
    }

    public Poster toEntity(PosterDto dto) {
        if (dto == null) {
            return null;
        }
        Poster poster = new Poster();
        if (dto.getId() != null) {
            poster.setId(dto.getId());
        }
        poster.setMovieId(dto.getMovieId());
        poster.setUrl(dto.getUrl());
        poster.setDescription(dto.getDescription());
        return poster;
    }

}
