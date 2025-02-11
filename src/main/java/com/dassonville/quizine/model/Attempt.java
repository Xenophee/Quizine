package com.dassonville.quizine.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Table(name = "attempts")
@Getter
@Setter
public class Attempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(insertable = false, updatable = false)
    private LocalDateTime attempt_date;

    @Positive(message = "Le score doit être positif.")
    private int score;

    @ManyToOne
    @JoinColumn(name = "id_quiz", referencedColumnName = "id", nullable = false)
    private Quiz quiz;

    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_difficulty_level", referencedColumnName = "id", nullable = false)
    private DifficultyLevel difficulty_level;

    @ManyToOne
    @JoinColumn(name = "id_status", referencedColumnName = "id", nullable = false)
    private Status status;
}
