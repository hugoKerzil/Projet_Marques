package com.repositories;

import com.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    // Trouver les films par genre
    List<Movie> findByGenresContaining(String genre);

    // Trouver les films d'un réalisateur précis
    List<Movie> findByRealisateur(String realisateur);

    // Trouver les films où un acteur précis joue
    List<Movie> findByActeursContaining(String acteur);

    // Trouver un film par son titre unique
    Movie findByTitle(String title);
}