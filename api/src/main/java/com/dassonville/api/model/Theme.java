package com.dassonville.api.model;


import com.dassonville.api.constant.FieldConstraint;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "themes")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Theme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = FieldConstraint.Theme.NAME_MAX, nullable = false, unique = true)
    private String name;

    @Column(length = FieldConstraint.Theme.DESCRIPTION_MAX, nullable = false)
    private String description;

    @Column(name = "is_default", nullable = false)
    private Boolean isDefault = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "disabled_at")
    @CreationTimestamp
    private LocalDateTime disabledAt;



    @OneToMany(mappedBy = "theme", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Category> categories;

    @OneToMany(mappedBy = "theme", fetch = FetchType.LAZY)
    private List<Quiz> quizzes;

    @OneToMany(mappedBy = "theme", fetch = FetchType.LAZY)
    private List<QuizSession> quizSessions;


    public Theme(long id) {
        this.id = id;
    }

    public void setVisible(boolean visible) {
        this.disabledAt = visible ? null : LocalDateTime.now();
    }

    public boolean isVisible() {
        return this.disabledAt == null;
    }
}
