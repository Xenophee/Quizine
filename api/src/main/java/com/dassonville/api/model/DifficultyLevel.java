package com.dassonville.api.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;


@Entity
@Table(name = "difficulty_levels")
@Getter
@Setter
@NoArgsConstructor
public class DifficultyLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false, unique = true)
    private String name;

    @Column(name = "answer_options_count", nullable = false)
    private Byte answerOptionsCount;

    @Column(name = "timer_seconds")
    private Short timerSeconds;

    @Column(name = "points_per_question", nullable = false)
    private Integer pointsPerQuestion;

    @Column(name = "is_reference", nullable = false)
    private Boolean isReference = false;

    @Column(name = "display_order", nullable = false)
    private Short displayOrder;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "disabled_at")
    @CreationTimestamp
    private LocalDateTime disabledAt;




    public DifficultyLevel(long id, String name, short displayOrder) {
        this.id = id;
        this.name = name;
        this.displayOrder = displayOrder;
    }


    public void setVisible(boolean visible) {
        this.disabledAt = visible ? null : LocalDateTime.now();
    }

    public boolean isVisible() {
        return this.disabledAt == null;
    }
}
