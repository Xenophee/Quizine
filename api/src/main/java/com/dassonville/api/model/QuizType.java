package com.dassonville.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "quiz_types")
@Getter
@Setter
public class QuizType {

    @Id
    @Column(length = 50, nullable = false, unique = true, updatable = false)
    private String code;

    @Column(length = 50, nullable = false, unique = true)
    private String name;

    @Column(length = 250, nullable = false)
    private String description;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "disabled_at")
    @CreationTimestamp
    private LocalDateTime disabledAt;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "quiz_type_questions",
            joinColumns = @JoinColumn(name = "quiz_type_code", referencedColumnName = "code", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "question_type_code", referencedColumnName = "code", nullable = false),
            uniqueConstraints = @UniqueConstraint(columnNames = {"quiz_type_code", "question_type_code"})
    )
    private List<QuestionType> questionTypes = new ArrayList<>();

    @OneToMany(mappedBy = "quizType", fetch = FetchType.LAZY)
    private List<Quiz> quizzes = new ArrayList<>();

    @OneToMany(mappedBy = "quizType", fetch = FetchType.LAZY)
    private List<QuizSession> quizSessions = new ArrayList<>();


    public void setVisible(boolean visible) {
        this.disabledAt = visible ? null : LocalDateTime.now();
    }

    public boolean isVisible() {
        return this.disabledAt == null;
    }

}
