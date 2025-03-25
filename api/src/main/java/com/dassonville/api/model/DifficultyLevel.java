package com.dassonville.api.model;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;


@Entity
@Table(name = "difficulty_levels")
@Data
public class DifficultyLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "max_responses", nullable = false)
    private byte maxResponses;

    @Column(name = "timer_seconds")
    private short timerSeconds;

    @Column(name = "points_per_question", nullable = false)
    private int pointsPerQuestion;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDate createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDate updatedAt;

    @Column(name = "disabled_at")
    @CreationTimestamp
    private LocalDate disabledAt;
}
