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
@Table(name = "mastery_levels")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MasteryLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = FieldConstraint.MasteryLevel.NAME_MAX, nullable = false, unique = true)
    private String name;

    @Column(length = FieldConstraint.MasteryLevel.DESCRIPTION_MAX, nullable = false)
    private String description;

    @Column(nullable = false, unique = true)
    private Byte rank;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "disabled_at")
    private LocalDateTime disabledAt;


    @OneToMany(mappedBy = "masteryLevel", fetch = FetchType.LAZY)
    private List<Quiz> quizzes = new ArrayList<>();

    @OneToMany(mappedBy = "masteryLevel", fetch = FetchType.LAZY)
    private List<QuizSession> quizSessions = new ArrayList<>();


    public MasteryLevel(long id) {
        this.id = id;
    }
}
