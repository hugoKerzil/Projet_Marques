package com.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "paiement")
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String type;

    private String statut;

    private double montant;

    /**
     * Relation avec la table Location.
     * Un paiement appartient à une seule location.
     */
    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;
}