package com.dassonville.quizine.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "quizzes")
@Getter
@Setter
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @NotEmpty(message = "Veuillez saisir un titre pour le quiz.")
    @Size(max = 100, message = "Le titre du quiz doit contenir entre 4 et 100 caractères.")
    private String title;

    @Column(nullable = false)
    @NotEmpty(message = "Veuillez saisir une description pour l''image du quiz.")
    @Size(max = 150, message = "La description de l''image ne doit pas dépasser 250 caractères.")
    private String alt_image;

    @ManyToOne
    @JoinColumn(name = "id_category", referencedColumnName = "id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "id_theme", referencedColumnName = "id", nullable = false)
    private Theme theme;
}
