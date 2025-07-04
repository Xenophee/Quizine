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
@Table(name = "categories")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = FieldConstraint.Category.NAME_MAX, nullable = false, unique = true)
    private String name;

    @Column(length = FieldConstraint.Category.DESCRIPTION_MAX, nullable = false)
    private String description;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "disabled_at")
    private LocalDateTime disabledAt;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "theme_id", referencedColumnName = "id", nullable = false)
    private Theme theme;

    @OneToMany(mappedBy = "category", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.LAZY)
    private List<Quiz> quizzes = new ArrayList<>();

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<QuizSession> quizSessions = new ArrayList<>();



    public Category(long id) {
        this.id = id;
    }

    public void setVisible(boolean visible) {
        this.disabledAt = visible ? null : LocalDateTime.now();
    }

    public boolean isVisible() {
        return this.disabledAt == null;
    }
}
