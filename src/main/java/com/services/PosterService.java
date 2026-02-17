package com.services;

import com.controllers.PosterController;
import com.dtos.PosterDto;

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

}