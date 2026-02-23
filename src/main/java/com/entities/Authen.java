package com.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "compte")
@Data
public class Authen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String pseudo;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;
}
