package com.dassonville.api.model;

import com.dassonville.api.util.JsonbConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "quiz_session_questions")
@Getter
@Setter
public class QuizSessionQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question_text", nullable = false)
    private String questionText;

    @Column(name = "question_type_name", length = 50, updatable = false, nullable = false)
    private String questionTypeName;

    @Column(name = "possible_answers", columnDefinition = "jsonb", nullable = false)
    @Convert(converter = JsonbConverter.class)
    private List<String> possibleAnswers;

    @Column(name = "user_answers", columnDefinition = "jsonb")
    @Convert(converter = JsonbConverter.class)
    private List<String> userAnswers;

    @Column(name = "is_correct", nullable = false)
    private Boolean isCorrect;

    @Column(name = "question_started_at", updatable = false, nullable = false)
    @CreationTimestamp
    private LocalDateTime questionStartedAt;

    @Column(name = "answer_received_at", insertable = false)
    private LocalDateTime answerReceivedAt;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "quiz_session_id", referencedColumnName = "id", nullable = false)
    private QuizSession quizSession;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", referencedColumnName = "id")
    private Question question;
}
