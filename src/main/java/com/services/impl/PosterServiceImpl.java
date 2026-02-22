package com.services.impl;

import com.dtos.PosterDto;
import com.mappers.PosterMapper;
import com.repositories.PosterRepository;
import com.services.PosterService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implémentation des opérations métier pour la gestion des posters.
 * Cette classe suit le principe de Single Responsibility (SOLID).
 */
@Service("posterService")
@Transactional
public class PosterServiceImpl implements PosterService {

    private final PosterRepository posterRepository;
    private final PosterMapper posterMapper;

    /**
     * Constructeur avec injection des dépendances
     * L'injection par constructeur est préférée à @Autowired car :
     * - Elle rend les dépendances obligatoires
     * - Elle facilite les tests unitaires
     * - Elle permet l'immutabilité
     */
    public PosterServiceImpl(PosterRepository posterRepository, PosterMapper posterMapper) {
        this.posterRepository = posterRepository;
        this.posterMapper = posterMapper;
    }

    /**
     * {@inheritDoc}
     * Utilisation de l'API Stream pour une transformation fonctionnelle des données
     */
    public List<PosterDto> getAllPostersMovie(Long movieId) {
        return posterRepository.findByMovieId(movieId).stream()
                .map(posterMapper::toDto)
                .toList();
    }

    /**
     * {@inheritDoc}
     * Utilisation de la méthode orElseThrow pour une gestion élégante des cas d'erreur
     */
    public PosterDto getPosterById(Long posterId) {
        var poster = posterRepository.findById(posterId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Le poster avec l'ID %d n'existe pas", posterId)));
        return posterMapper.toDto(poster);
    }

    /**
     * {@inheritDoc}
     * Cette méthode est transactionnelle par défaut grâce à @Transactional sur la classe
     */
    public PosterDto savePoster(PosterDto posterDto) {
        var poster = posterMapper.toEntity(posterDto);
        var savedPoster = posterRepository.save(poster);
        return posterMapper.toDto(savedPoster);
    }

    public PosterDto updatePoster(Long posterId, PosterDto posterDto) {
        if (!posterRepository.existsById(posterId)) throw new EntityNotFoundException("Inexistant");
        var poster = posterMapper.toEntity(posterDto);
        poster.setId(posterId); // On force l'ID pour faire un update et non un insert
        return posterMapper.toDto(posterRepository.save(poster));
    }

    /**
     * {@inheritDoc}
     * La méthode deleteById ne lève pas d'exception si l'entité n'existe pas
     */
    public boolean deletePoster(Long posterId) {
        if (posterRepository.existsById(posterId)) {
            posterRepository.deleteById(posterId);
            return true;
        }
        return false;
    }

}
