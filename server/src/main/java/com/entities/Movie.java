package com.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identifiant unique

    private String title; // Titre du film

    private String anneeRealisation; // Année de réalisation

    private String realisateur; // Nom du réalisateur

    @ElementCollection
    private List<String> acteurs; // Ensemble d'acteurs

    @ElementCollection
    private List<String> genres; // Genres du film

    private Long ageMinimum; // Âge minimum pour visionner le film

    @ElementCollection
    private List<String> affiches; // Chemin ou URL de l'image de l'affiche

    private boolean estOuvertALaLocation; // État de disponibilité (ouvert/fermé)
}