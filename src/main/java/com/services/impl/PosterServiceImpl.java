package com.services.impl;

import com.dtos.PosterDto;
import com.mappers.PosterMapper;
import com.repositories.PosterRepository;
import com.services.PosterService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
