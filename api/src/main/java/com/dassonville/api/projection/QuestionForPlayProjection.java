package com.dassonville.api.projection;


import java.util.List;

public interface QuestionForPlayProjection {
    Long getId();
    String getText();
    List<AnswersForPlay> getAnswers();

    interface AnswersForPlay {
        Long getId();
        String getText();
        boolean getIsCorrect();
    }
}
