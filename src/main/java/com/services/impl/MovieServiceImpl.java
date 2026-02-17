package com.services.impl;

import com.dtos.MovieDto;
import com.repositories.MovieRepository;
import com.services.MovieService;
import com.mappers.MovieMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service("movieService")
@Transactional
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    public MovieServiceImpl(MovieRepository movieRepository, MovieMapper movieMapper) {
        this.movieRepository = movieRepository;
        this.movieMapper = movieMapper;
    }

    @Override
    public MovieDto saveMovie(MovieDto movieDto) {
        var movie = movieMapper.toEntity(movieDto);
        movie.setEstOuvertALaLocation(false); // [cite: 25]
        return movieMapper.toDto(movieRepository.save(movie));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovieDto> getAllMovies() {
        return movieRepository.findAll().stream()
                .map(movieMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public MovieDto getMovieById(Integer id) {
        return movieRepository.findById(id)
                .map(movieMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Film non trouvé"));
    }

    @Override
    public MovieDto updateMovie(Integer id, MovieDto movieDto) {
        if (!movieRepository.existsById(id)) throw new EntityNotFoundException("Inexistant");
        var movie = movieMapper.toEntity(movieDto);
        movie.setId(id); // On force l'ID pour faire un update et non un insert
        return movieMapper.toDto(movieRepository.save(movie));
    }

    @Override
    public boolean deleteMovie(Integer id) {
        movieRepository.deleteById(id);
        return true;
    }

    @Override
    public MovieDto getMovieByTitle(String title) {
        return movieMapper.toDto(movieRepository.findByTitle(title));
    }
}