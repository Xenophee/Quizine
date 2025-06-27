package com.dassonville.api.model;

import com.dassonville.api.constant.FieldConstraint;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "quiz_sessions")
@Getter
@Setter
public class QuizSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(name = "quiz_title", nullable = false, updatable = false, length = FieldConstraint.Quiz.TITLE_MAX)
    private String quizTitle;

    @Column(name = "quiz_type_name", nullable = false, updatable = false, length = 50)
    private String quizTypeName;

    @Column(name = "mastery_level_label", nullable = false, updatable = false, length = FieldConstraint.MasteryLevel.NAME_MAX)
    private String masteryLevelLabel;

    @Column(name = "difficulty_level_code", nullable = false, updatable = false, length = 50)
    private String difficultyLevelCode;

    @Column(name = "difficulty_level_label", nullable = false, updatable = false, length = FieldConstraint.DifficultyLevel.NAME_MAX)
    private String difficultyLevelLabel;

    @Column(name = "has_timer_enabled", nullable = false, updatable = false)
    private Boolean hasTimerEnabled = false;

    @Column(name = "has_penalties_enabled", nullable = false, updatable = false)
    private Boolean hasPenalitiesEnabled = false;

    @Column(name = "theme_name", nullable = false, updatable = false, length = 50)
    private String themeName;

    @Column(name = "category_name", nullable = false, updatable = false, length = FieldConstraint.Category.NAME_MAX)
    private String categoryName;

    @Column(nullable = false)
    private Integer score = 0;

    @Column(name = "started_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime startedAt;

    @Column(name = "is_finished", nullable = false)
    private Boolean isFinished = false;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", referencedColumnName = "id", updatable = false)
    private Quiz quiz;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mastery_level_id", referencedColumnName = "id", updatable = false)
    private MasteryLevel masteryLevel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_type_code", referencedColumnName = "code", updatable = false)
    private QuizType quizType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theme_id", referencedColumnName = "id", updatable = false)
    private Theme theme;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id", updatable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "difficulty_level_id", referencedColumnName = "id", updatable = false)
    private DifficultyLevel difficultyLevel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", updatable = false)
    private User user;

    @OneToMany(mappedBy = "quizSession", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<QuizSessionQuestion> quizSessionQuestions = new ArrayList<>();
}
