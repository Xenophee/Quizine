package com.dassonville.api.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "game_rules",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = {"question_type_code", "difficulty_level_id"}),
        @UniqueConstraint(columnNames = {"tag", "question_type_code", "difficulty_level_id"})
})
@Getter
@Setter
public class GameRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String tag; // Null s'il s'agit d'une règle générique

    @Column(name = "is_reference", nullable = false)
    private Boolean isReference = false;

    @Column(name = "answer_options_count", nullable = false)
    private Byte answerOptionsCount;

    @Column(name = "points_per_good_answer", nullable = false)
    private Short pointsPerGoodAnswer;

    @Column(name = "points_penalty_per_wrong_answer", nullable = false)
    private Short pointsPenaltyPerWrongAnswer;

    @Column(name = "timer_seconds", nullable = false)
    private Short timerSeconds;

    @Column(name = "points_timer_multiplier", nullable = false, precision = 4)
    private Float pointsTimerMultiplier;

    @Column(name = "points_penalty_multiplier", nullable = false, precision = 4)
    private Float pointsPenaltyMultiplier;

    @Column(name = "combo_2_bonus", nullable = false)
    private Short combo2Bonus;

    @Column(name = "combo_3_bonus", nullable = false)
    private Short combo3Bonus;

    @Column(name = "combo_4_bonus", nullable = false)
    private Short combo4Bonus;

    @Column(name = "combo_5_bonus", nullable = false)
    private Short combo5Bonus;

    @Column(name = "starts_at")
    private LocalDateTime startsAt; // Date de début de validité de la règle

    @Column(name = "ends_at")
    private LocalDateTime endsAt; // Date de fin de validité de la règle

    @Column(nullable = false)
    private Byte priority;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "disabled_at")
    private LocalDateTime disabledAt;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_type_code", nullable = false)
    private QuestionType questionType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "difficulty_level_id", nullable = false)
    private DifficultyLevel difficultyLevel;
}
