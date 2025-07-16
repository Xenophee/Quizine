package com.dassonville.api.util;

import com.dassonville.api.constant.FieldConstraint;
import com.dassonville.api.constant.RequestActionType;
import com.dassonville.api.constant.Type;
import com.dassonville.api.dto.request.ClassicAnswerUpsertDTO;
import com.dassonville.api.dto.request.ClassicQuestionInsertDTO;
import com.dassonville.api.dto.request.ClassicQuestionUpdateDTO;
import com.dassonville.api.dto.request.QuizUpsertDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.List;
import java.util.stream.Stream;

public class TestValidationProvider {

    public static Stream<Arguments> invalidThemeUpsertCases() {
        return Stream.of(
                // Cas vide → NotBlank attendu
                Arguments.of(
                        "", "",
                        FieldConstraint.Theme.NAME_NOT_BLANK,
                        FieldConstraint.Theme.DESCRIPTION_NOT_BLANK
                ),
                // Cas non vide mais trop court → Size attendu
                Arguments.of(
                        "A", "B",
                        FieldConstraint.Theme.NAME_SIZE,
                        FieldConstraint.Theme.DESCRIPTION_SIZE
                )
                // Cas mixte → NotBlank et Size attendus
                , Arguments.of(
                        "A", "",
                        FieldConstraint.Theme.NAME_SIZE,
                        FieldConstraint.Theme.DESCRIPTION_NOT_BLANK
                )
        );
    }

    public static Stream<Arguments> invalidCategoryUpsertCases() {
        return Stream.of(
                // Cas vide → NotBlank attendu
                Arguments.of(
                        "", "",
                        FieldConstraint.Category.NAME_NOT_BLANK,
                        FieldConstraint.Category.DESCRIPTION_NOT_BLANK
                ),
                // Cas non vide mais trop court → Size attendu
                Arguments.of(
                        "A", "B",
                        FieldConstraint.Category.NAME_SIZE,
                        FieldConstraint.Category.DESCRIPTION_SIZE
                )
                // Cas mixte → NotBlank et Size attendus
                , Arguments.of(
                        "A", "",
                        FieldConstraint.Category.NAME_SIZE,
                        FieldConstraint.Category.DESCRIPTION_NOT_BLANK
                )
        );
    }

    public static Stream<Arguments> invalidQuizUpsertCases() {
        return Stream.of(
                // Cas vide → NotBlank attendu
                Arguments.of(
                        new QuizUpsertDTO(null, "", "", null, null, null),
                        new String[]{
                                FieldConstraint.Quiz.TYPE_NOT_NULL,
                                FieldConstraint.Quiz.TITLE_NOT_BLANK,
                                FieldConstraint.Quiz.DESCRIPTION_NOT_BLANK,
                                FieldConstraint.Quiz.MASTERY_LEVEL_NOT_NULL,
                                FieldConstraint.Quiz.THEME_NOT_NULL
                        }
                ),
                // Cas non vide mais trop court → Size attendu
                Arguments.of(
                        new QuizUpsertDTO(Type.CLASSIC, "B", "C", 1L, 2L, 3L),
                        new String[]{
                                FieldConstraint.Quiz.TITLE_SIZE,
                                FieldConstraint.Quiz.DESCRIPTION_SIZE,
                                FieldConstraint.Quiz.CATEGORY_NOT_BELONG_TO_THEME
                        }
                ),
                // Cas mixte → NotBlank et Size attendus
                Arguments.of(
                        new QuizUpsertDTO(Type.CLASSIC, "", "C", 1L, 2L, 3L),
                        new String[]{
                                FieldConstraint.Quiz.TITLE_NOT_BLANK,
                                FieldConstraint.Quiz.DESCRIPTION_SIZE,
                                FieldConstraint.Quiz.CATEGORY_NOT_BELONG_TO_THEME
                        }
                )
        );
    }

    public static Stream<Arguments> invalidQuestionInsertCases() {
        return Stream.of(
                // Cas avec des champs null → NotBlank attendu
                Arguments.of(
                        new ClassicQuestionInsertDTO(RequestActionType.CLASSIC_INSERT, null, null, null),
                        new String[]{
                                FieldConstraint.Question.TEXT_NOT_BLANK,
                                FieldConstraint.Question.ANSWER_EXPLANATION_NOT_BLANK,
                                "Veuillez fournir au moins 2 réponses."
                        }
                ),
                // Cas avec des champs vides → NotBlank attendu
                Arguments.of(
                        new ClassicQuestionInsertDTO(RequestActionType.CLASSIC_INSERT, "", "", null),
                        new String[]{
                                FieldConstraint.Question.TEXT_NOT_BLANK,
                                FieldConstraint.Question.ANSWER_EXPLANATION_NOT_BLANK,
                                "Veuillez fournir au moins 2 réponses."
                        }
                ),
                // Cas avec des champs trop courts → Size attendu
                Arguments.of(
                        new ClassicQuestionInsertDTO(RequestActionType.CLASSIC_INSERT, "A", "B", null),
                        new String[]{
                                FieldConstraint.Question.TEXT_SIZE,
                                FieldConstraint.Question.ANSWER_EXPLANATION_SIZE,
                                "Veuillez fournir au moins 2 réponses."
                        }
                ),
                // Cas avec des réponses non valides → ValidMinAnswersPerQuestion attendu
                Arguments.of(
                        new ClassicQuestionInsertDTO(RequestActionType.CLASSIC_INSERT, "Quelle est la capitale de la France ?",
                                "La capitale de la France est Paris.",
                                List.of(
                                        new ClassicAnswerUpsertDTO("", false),
                                        new ClassicAnswerUpsertDTO("Paris", false)
                                )
                        ),
                        new String[]{
                                FieldConstraint.Question.AT_LEAST_ONE_CORRECT_ANSWER_REQUIRED,
                                FieldConstraint.ClassicAnswer.TEXT_NOT_BLANK
                        }
                ),
                // Cas avec des réponses identiques → ValidMinAnswersPerQuestion attendu
                Arguments.of(
                        new ClassicQuestionInsertDTO(RequestActionType.CLASSIC_INSERT, null,
                                null,
                                List.of(
                                        new ClassicAnswerUpsertDTO("Paris", null),
                                        new ClassicAnswerUpsertDTO("Paris", false)
                                )
                        ),
                        new String[]{
                                FieldConstraint.Question.TEXT_NOT_BLANK,
                                FieldConstraint.Question.ANSWER_EXPLANATION_NOT_BLANK,
                                FieldConstraint.ClassicAnswer.IS_CORRECT_NOT_NULL,
                                FieldConstraint.Question.AT_LEAST_ONE_CORRECT_ANSWER_REQUIRED + " " + FieldConstraint.Question.ONLY_UNIQUE_ANSWERS_ALLOWED
                        }
                )
        );
    }

    public static Stream<Arguments> invalidQuestionUpdateCases() {
        return Stream.of(
                // Cas avec des champs null → NotBlank attendu
                Arguments.of(
                        new ClassicQuestionUpdateDTO(RequestActionType.CLASSIC_UPDATE, null, null),
                        new String[]{
                                FieldConstraint.Question.TEXT_NOT_BLANK,
                                FieldConstraint.Question.ANSWER_EXPLANATION_NOT_BLANK
                        }
                ),
                // Cas avec des champs vides → NotBlank attendu
                Arguments.of(
                        new ClassicQuestionUpdateDTO(RequestActionType.CLASSIC_UPDATE, "", ""),
                        new String[]{
                                FieldConstraint.Question.TEXT_NOT_BLANK,
                                FieldConstraint.Question.ANSWER_EXPLANATION_NOT_BLANK
                        }
                ),
                // Cas avec des champs trop courts → Size attendu
                Arguments.of(
                        new ClassicQuestionUpdateDTO(RequestActionType.CLASSIC_UPDATE, "A", "B"),
                        new String[]{
                                FieldConstraint.Question.TEXT_SIZE,
                                FieldConstraint.Question.ANSWER_EXPLANATION_SIZE
                        }
                )
        );
    }
}
