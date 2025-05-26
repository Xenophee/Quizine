package com.dassonville.api.projection;

import java.time.LocalDateTime;

public interface PublicQuizProjection {
    Long getId();
    String getTitle();
    LocalDateTime getCreatedAt();
    int getNumberOfQuestions();
    String getCategoryName();
    String getThemeName();
}
