package com.dassonville.api.projection;


import java.time.LocalDateTime;
import java.util.List;

public interface QuestionForPlayProjection {
    List<QuizShortProjection> getQuizzes();
    QuestionType getQuestionType();
    Long getId();
    String getText();
    List<AnswersForPlay> getClassicAnswers();

    interface QuestionType {
        String getId();
        String getName();
        String getInstruction();
    }

    interface AnswersForPlay {
        Long getId();
        String getText();
        boolean getIsCorrect();
    }

    interface QuizShortProjection {
        LocalDateTime getCreatedAt();
        MasteryLevel getMasteryLevel();
        ThemeInfo getTheme();
        CategoryInfo getCategory();

        interface MasteryLevel {
            String getName();
        }

        interface ThemeInfo {
            String getName();
        }

        interface CategoryInfo {
            String getName();
        }
    }
}
