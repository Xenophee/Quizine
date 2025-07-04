package com.dassonville.api.model;

import com.dassonville.api.constant.FieldConstraint;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "quizzes")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = FieldConstraint.Quiz.TITLE_MAX, nullable = false, unique = true)
    private String title;

    @Column(length = FieldConstraint.Quiz.DESCRIPTION_MAX, nullable = false)
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


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "theme_id", referencedColumnName = "id", nullable = false)
    private Theme theme;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "quiz_type_code", referencedColumnName = "code", nullable = false)
    private QuizType quizType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mastery_level_id", referencedColumnName = "id", nullable = false)
    private MasteryLevel masteryLevel;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "quiz_questions",
            joinColumns = @JoinColumn(name = "quiz_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "question_id", referencedColumnName = "id", nullable = false),
            uniqueConstraints = @UniqueConstraint(columnNames = {"quiz_id", "question_id"})
    )
    private List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "quiz", fetch = FetchType.LAZY)
    private List<QuizSession> quizSessions = new ArrayList<>();



    public Quiz(long id) {
        this.id = id;
    }

    public void setVisible(boolean visible) {
        this.disabledAt = visible ? null : LocalDateTime.now();
    }

    public boolean isVisible() {
        return this.disabledAt == null;
    }
}
