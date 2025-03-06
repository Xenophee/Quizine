package com.dassonville.api.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "questions")
@Getter
@Setter
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message = "Veuillez saisir le contenu de la question.")
    @Size(max = 300, message = "Le contenu de la question ne doit pas dépasser 300 caractères.")
    private String text;

    @ManyToOne
    @JoinColumn(name = "id_quiz", referencedColumnName = "id", nullable = false)
    private Quiz quiz;
}
