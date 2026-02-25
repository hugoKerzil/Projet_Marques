package com.services;

import com.dtos.PosterDto;

import java.util.List;

public interface PosterService {

    /**
     * Récupère un poster par son identifiant
     * @param posterId l'identifiant du poster recherché
     * @return le poster trouvé
     * @throws jakarta.persistence.EntityNotFoundException si le poster n'existe pas
     */
    PosterDto getPosterById(Long posterId);

    /**
     * Sauvegarde un poster dans le système
     * @param posterDto les données du poster à sauvegarder
     * @return le poster sauvegardé avec son ID généré
     */
    PosterDto savePoster(PosterDto posterDto);

    /**
     * Récupère tous les posters du système
     * @return la liste des posters
     */
    List<PosterDto> getAllPostersMovie(Long movieId);

    /**
     * Modifie un poster du système
     * @param posterId l'identifiant du poster à supprimer
     * @param posterDto les données du poster à modifier
     * @return true si la modification a réussi
     */
    PosterDto updatePoster(Long posterId, PosterDto posterDto);

    /**
     * Supprime un poster du système
     * @param posterId l'identifiant du poster à supprimer
     * @return true si la suppression a réussi
     */
    boolean deletePoster(Long posterId);
}