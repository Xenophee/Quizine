package com.dassonville.api.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    THEME_NOT_FOUND("THEME_NOT_FOUND", "Le thème avec l'id %s est introuvable."),
    THEME_ALREADY_EXISTS("THEME_ALREADY_EXISTS", "Un thème avec le nom « %s » existe déjà."),
    THEME_CONTAINS_QUIZZES("THEME_CONTAINS_QUIZZES", "Le thème avec l'id %d contient des quiz et ne peut pas être supprimé."),
    THEME_CONTAINS_NO_QUIZZES("THEME_CONTAINS_NO_QUIZZES", "Le thème avec l'id %d ne contient aucun quiz actif."),

    CATEGORY_NOT_FOUND("CATEGORY_NOT_FOUND", "La catégorie avec l'id %d est introuvable."),
    CATEGORY_ALREADY_EXISTS("CATEGORY_ALREADY_EXISTS", "Une catégorie avec le nom %s existe déjà."),

    QUIZ_NOT_FOUND("QUIZ_NOT_FOUND", "Le quiz avec l'id %d est introuvable."),
    QUIZ_ALREADY_EXISTS("QUIZ_ALREADY_EXISTS", "Un quiz avec le titre « %s » existe déjà."),
    QUIZ_CONTAINS_NOT_ENOUGH_QUESTIONS("QUIZ_CONTAINS_NOT_ENOUGH_QUESTIONS", "Le quiz avec l'id %d doit contenir au moins %d questions pour être actif."),

    QUESTION_NOT_FOUND("QUESTION_NOT_FOUND", "La question avec l'id %d est introuvable."),
    QUESTION_ALREADY_EXISTS("QUESTION_ALREADY_EXISTS", "Une question avec le texte « %s » existe déjà."),
    QUESTION_AND_QUIZ_MISMATCH("QUESTION_AND_QUIZ_MISMATCH", "La question avec l'id %d n'appartient pas au quiz avec l'id %d ou n'existe pas."),

    ANSWER_NOT_FOUND("ANSWER_NOT_FOUND", "La réponse avec l'id %d est introuvable."),
    ANSWER_ALREADY_EXISTS("ANSWER_ALREADY_EXISTS", "Une réponse avec le texte « %s » existe déjà."),
    ANSWERS_AND_QUESTION_MISMATCH("ANSWER_AND_QUESTION_MISMATCH", "Une ou plusieurs réponses avec les ids %d n'appartiennent pas à la question avec l'id %d ou n'existent pas."),
    MINIMUM_ACTIVE_ANSWERS_NOT_REACHED("MINIMUM_ACTIVE_ANSWERS_NOT_REACHED", "La question avec l'id %d doit avoir au moins %d réponses actives."),
    ATLEAST_ONE_CORRECT_ACTIVE_ANSWER_IS_MANDATORY("ATLEAST_ONE_CORRECT_ACTIVE_ANSWER_IS_MANDATORY", "La question avec l'id %d doit avoir au moins une réponse correcte et active."),

    DIFFICULTY_NOT_FOUND("DIFFICULTY_NOT_FOUND", "La difficulté avec l'id %d est introuvable."),
    DIFFICULTIES_NOT_FOUND("DIFFICULTIES_NOT_FOUND", "Certains IDs fournis ne correspondent pas à des niveaux de difficulté existants : %s."),
    DIFFICULTY_ALREADY_EXISTS("DIFFICULTY_ALREADY_EXISTS", "Une difficulté avec le nom « %s » existe déjà."),
    DIFFICULTY_IS_REFERENCE("DIFFICULTY_IS_REFERENCE", "La difficulté avec l'id %d est une référence et ne peut pas être supprimée."),

    INTERNAL_ERROR("INTERNAL_ERROR", "Une erreur interne empêche de traiter la requête."),
    UNEXPECTED_ERROR("UNEXPECTED_ERROR", "Une erreur inattendue s'est produite.");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
