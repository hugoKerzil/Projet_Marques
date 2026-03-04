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

    private String status;

    private double amount;

    /**
     * Relationship with the Location table.
     * A payment belongs to a single rental.
     */
    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private Rental rental;
}