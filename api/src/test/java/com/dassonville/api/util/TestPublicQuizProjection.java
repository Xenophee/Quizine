package com.dassonville.api.util;

import com.dassonville.api.projection.PublicQuizProjection;

import java.time.LocalDateTime;

public class TestPublicQuizProjection implements PublicQuizProjection {
    public Long getId() { return 1L; }
    public String getTitle() { return "Un titre"; }
    public LocalDateTime getCreatedAt() { return LocalDateTime.now(); }
    public int getNumberOfQuestions() { return 10; }
    public String getQuizTypeName() { return "Type de quiz"; }
    public String getMasteryLevelName() { return "Niveau de maîtrise"; }
    public String getCategoryName() { return "Catégorie"; }
    public String getThemeName() { return "Thème"; }
}
