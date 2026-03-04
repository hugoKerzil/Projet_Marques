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

    /**
     * Builder with dependency injection
     * Injection by constructor is preferred over @Autowired because:
     * - It makes dependencies mandatory
     * - It facilitates unit testing
     * - It enables immutability
     */
    public MovieServiceImpl(MovieRepository movieRepository, MovieMapper movieMapper) {
        this.movieRepository = movieRepository;
        this.movieMapper = movieMapper;
    }

    /**
     * {@inheritDoc}
     * This method is transactional by default thanks to @Transactional on the class.
     */
    @Override
    public MovieDto saveMovie(MovieDto movieDto) {
        var movie = movieMapper.toEntity(movieDto);
        movie.setOpenForRent(false);
        return movieMapper.toDto(movieRepository.save(movie));
    }

    /**
     * {@inheritDoc}
     * Using the Stream API for functional data transformation.
     */
    @Override
    @Transactional(readOnly = true)
    public List<MovieDto> getAllMovies() {
        return movieRepository.findAll().stream()
                .map(movieMapper::toDto)
                .toList();
    }

    /**
     * {@inheritDoc}
     * Using the orElseThrow method for elegant error handling
     */
    @Override
    @Transactional(readOnly = true)
    public MovieDto getMovieById(int id) {
        return movieRepository.findById(id)
                .map(movieMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Film not found"));
    }

    /**
     * {@inheritDoc}
     * Forcing the ID to perform an update rather than an insert.
     */
    @Override
    public MovieDto updateMovie(int id, MovieDto movieDto) {
        if (!movieRepository.existsById(id)) throw new EntityNotFoundException("Absent.");
        var movie = movieMapper.toEntity(movieDto);
        movie.setId(id);
        return movieMapper.toDto(movieRepository.save(movie));
    }

    /**
     * {@inheritDoc}
     * The deleteById method does not throw an exception if the entity does not exist.
     */
    @Override
    public boolean deleteMovie(int id) {
        if (movieRepository.existsById(id)) {
            movieRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public MovieDto getMovieByTitle(String title) {
        return movieMapper.toDto(movieRepository.findByTitle(title));
    }
}