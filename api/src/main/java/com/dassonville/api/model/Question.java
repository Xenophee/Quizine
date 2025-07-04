package com.dassonville.api.model;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "questions")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String text;

    @Column(name = "answer_explanation", nullable = false)
    private String answerExplanation;

    @Column(name = "answer_if_true_false")
    private Boolean answerIfTrueFalse = null;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "disabled_at")
    private LocalDateTime disabledAt;


    @ManyToMany(mappedBy = "questions", fetch = FetchType.LAZY)
    private List<Quiz> quizzes = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_type_code", referencedColumnName = "code", nullable = false)
    private QuestionType questionType;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ClassicAnswer> classicAnswers = new ArrayList<>();

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
    private List<QuizSessionQuestion> quizSessionQuestions = new ArrayList<>();



    public Question(long id) {
        this.id = id;
    }

    public void setVisible(boolean visible) {
        this.disabledAt = visible ? null : LocalDateTime.now();
    }

    public boolean isVisible() {
        return this.disabledAt == null;
    }
}
