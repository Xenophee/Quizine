package com.dassonville.api.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "difficulty_levels")
@Getter
@Setter
public class DifficultyLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    @NotEmpty(message = "Veuillez saisir un nom de difficulté.")
    @Size(max = 50, message = "Le nom de la difficulté ne doit pas dépasser 50 caractères.")
    private String name;

    @Column(nullable = false)
    @Min(value = 2, message = "Le nombre de réponses minimum doit être supérieur à 1.")
    private byte maxResponses;

    private short timer_seconds;

    @Column(nullable = false)
    @Min(value = 1, message = "Le nombre de points doit être supérieur à 0.")
    private int pointsPerQuestion;
}
