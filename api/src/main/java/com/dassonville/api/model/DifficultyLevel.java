package com.dassonville.api.model;


import com.dassonville.api.constant.FieldConstraint;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "difficulty_levels")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DifficultyLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = FieldConstraint.DifficultyLevel.NAME_MAX, nullable = false, unique = true)
    private String name;

    @Column(name = "is_reference", nullable = false)
    private Boolean isReference = false;

    @Column(length = 10, nullable = false)
    private String label;

    @Column(length = FieldConstraint.DifficultyLevel.DESCRIPTION_MAX, nullable = false)
    private String description;

    @Column(name = "rank")
    private Byte rank;

    @Column(name = "starts_at")
    private LocalDate startsAt; // Date de début de validité de la difficulté

    @Column(name = "ends_at")
    private LocalDate endsAt; // Date de fin de validité de la difficulté

    @Column(name = "is_recurring", nullable = false)
    private Boolean isRecurring = false;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "disabled_at")
    private LocalDateTime disabledAt;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "question_type_difficulty",
            joinColumns = @JoinColumn(name = "difficulty_level_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "question_type_code", referencedColumnName = "code", nullable = false),
            uniqueConstraints = @UniqueConstraint(columnNames = {"difficulty_level_id", "question_type_code"})
    )
    private List<QuestionType> questionTypes = new ArrayList<>();

    @OneToMany(mappedBy = "difficultyLevel", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<GameRule> gameRules = new ArrayList<>();

    @OneToMany(mappedBy = "difficultyLevel", fetch =  FetchType.LAZY)
    private List<QuizSession> quizSessions = new ArrayList<>();


    public DifficultyLevel(long id, String name, byte rank) {
        this.id = id;
        this.name = name;
        this.rank = rank;
    }
}
