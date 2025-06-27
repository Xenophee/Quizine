package com.dassonville.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "question_types")
@Getter
@Setter
@NoArgsConstructor
public class QuestionType {

    @Id
    @Column(length = 50, nullable = false, unique = true, updatable = false)
    private String code;

    @Column(length = 50, nullable = false, unique = true)
    private String name;

    @Column(length = 250, nullable = false)
    private String description;

    @Column(length = 250, nullable = false)
    private String instruction;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;


    @ManyToMany(mappedBy = "questionTypes", fetch = FetchType.LAZY)
    private List<QuizType> quizTypes = new ArrayList<>();

    @ManyToMany(mappedBy = "questionTypes", fetch = FetchType.LAZY)
    private List<DifficultyLevel> difficultyLevels = new ArrayList<>();

    @OneToMany(mappedBy = "questionType", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<GameRule> gameRules = new ArrayList<>();

    @OneToMany(mappedBy = "questionType", fetch = FetchType.LAZY)
    private List<Question> questions = new ArrayList<>();

    public QuestionType(String questionTypeId) {
        this.code = questionTypeId;
    }
}
