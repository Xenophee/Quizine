package com.dassonville.quizine.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "answers")
@Getter
@Setter
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @NotEmpty(message = "Veuillez saisir un texte pour la réponse.")
    @Size(max = 150, message = "Le texte de la réponse ne doit pas dépasser 150 caractères.")
    private String text;

    private Boolean is_correct;

    @ManyToOne
    @JoinColumn(name = "id_question", referencedColumnName = "id", nullable = false)
    private Question question;
}
