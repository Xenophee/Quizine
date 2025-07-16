package com.dassonville.api.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    THEME_NOT_FOUND("THEME_NOT_FOUND", "Le thème est introuvable."),
    THEME_ALREADY_EXISTS("THEME_ALREADY_EXISTS", "Un thème avec le même nom existe déjà."),
    THEME_CONTAINS_QUIZZES("THEME_CONTAINS_QUIZZES", "Le thème contient des quiz et ne peut pas être supprimé."),
    THEME_IS_DEFAULT("THEME_IS_DEFAULT", "Le thème est celui par défaut et ne peut pas être supprimé."),
    THEME_CONTAINS_NO_QUIZZES("THEME_CONTAINS_NO_QUIZZES", "Le thème ne contient aucun quiz actif pour être activé."),

    CATEGORY_NOT_FOUND("CATEGORY_NOT_FOUND", "La catégorie est introuvable."),
    CATEGORY_ALREADY_EXISTS("CATEGORY_ALREADY_EXISTS", "Une catégorie avec le même nom existe déjà."),

    QUIZ_NOT_FOUND("QUIZ_NOT_FOUND", "Le quiz est introuvable."),
    QUIZ_ALREADY_EXISTS("QUIZ_ALREADY_EXISTS", "Un quiz avec le même titre existe déjà."),
    QUIZ_CONTAINS_INVALID_QUESTION_TYPE("QUIZ_CONTAINS_INVALID_QUESTION_TYPE", "Le quiz contient des questions de type non supporté par le nouveau type de quiz."),
    QUIZ_CONTAINS_NOT_ENOUGH_QUESTIONS("QUIZ_CONTAINS_NOT_ENOUGH_QUESTIONS", "Le quiz doit contenir au moins %d questions pour être actif."),

    QUESTION_NOT_FOUND("QUESTION_NOT_FOUND", "La question est introuvable."),
    QUESTION_ALREADY_EXISTS("QUESTION_ALREADY_EXISTS", "Une question avec le même texte existe déjà."),
    QUESTION_AND_QUIZ_MISMATCH("QUESTION_AND_QUIZ_MISMATCH", "La question n'appartient pas au quiz ou n'existe pas."),
    QUESTION_AND_QUESTION_TYPE_MISMATCH("QUESTION_AND_QUESTION_TYPE_MISMATCH", "Les informations fournies ne correspondent pas au schéma attendu pour la question."),
    QUESTION_TYPE_NOT_SUPPORTED("QUESTION_TYPE_NOT_SUPPORTED", "Le type du quiz n'autorise pas ce type de question."),

    ANSWER_NOT_FOUND("ANSWER_NOT_FOUND", "La réponse est introuvable."),
    ANSWER_ALREADY_EXISTS("ANSWER_ALREADY_EXISTS", "Une réponse avec le même texte existe déjà."),
    ANSWERS_AND_QUESTION_MISMATCH("ANSWER_AND_QUESTION_MISMATCH", "Une ou plusieurs réponses n'appartiennent pas à la question ou n'existent pas."),
    MINIMUM_ACTIVE_ANSWERS_NOT_REACHED("MINIMUM_ACTIVE_ANSWERS_NOT_REACHED", "Cette question doit avoir au moins %d réponses actives."),
    AT_LEAST_ONE_CORRECT_ACTIVE_ANSWER_IS_MANDATORY("AT_LEAST_ONE_CORRECT_ACTIVE_ANSWER_IS_MANDATORY", "Une réponse correcte et active est requise pour cette question."),
    ANSWER_TYPE_NOT_SUPPORTED("ANSWER_TYPE_NOT_SUPPORTED", "Ce type d'enregistrement n'est pas supporté pour cette question."),

    DIFFICULTY_NOT_FOUND("DIFFICULTY_NOT_FOUND", "Le niveau de difficulté est introuvable."),
    MASTERY_LEVEL_NOT_FOUND("MASTERY_LEVEL_NOT_FOUND", "Le niveau de maîtrise est introuvable."),

    TYPE_NOT_SUPPORTED("TYPE_NOT_SUPPORTED", "Ce type n'est pas supporté."),
    CHECK_ANSWER_TYPE_NOT_SUPPORTED("CHECK_ANSWER_NOT_SUPPORTED", "La vérification de la réponse ne prend pas en charge ce type de question."),

    QUIZ_MISCONFIGURED("QUIZ_MISCONFIGURED", "Le quiz ne réunit pas les conditions nécessaires pour proposer des niveaux de difficulté."),
    QUIZ_TYPE_MISMATCH("QUIZ_TYPE_MISMATCH", "Le type de quiz ne correspond pas au type de la requête."),


    ALREADY_EXIST("ALREADY_EXIST", "Une entité avec les mêmes informations existe déjà."),
    NOT_FOUND("NOT_FOUND", "L'entité demandée est introuvable."),
    ACTION_NOT_ALLOWED("ACTION_NOT_ALLOWED", "L'action demandée n'est pas autorisée pour cette entité."),
    INVALID_ARGUMENT("INVALID_ARGUMENT", "Un ou plusieurs arguments sont invalides."),
    INVALID_STATE("INVALID_STATE", "L'état de l'entité ne permet pas de réaliser l'action demandée."),
    MISCONFIGURED("MISCONFIGURED", "L'entité n'est pas correctement configurée pour réaliser l'action demandée."),

    VALIDATION_ERROR("VALIDATION_ERROR", "Une ou plusieurs erreurs de validation ont été détectées."),
    DESERIALIZATION_ERROR("DESERIALIZATION_ERROR", "Une erreur de désérialisation s'est produite lors du traitement de la requête. Veuillez vérifier le format des données envoyées."),
    INTERNAL_ERROR("INTERNAL_ERROR", "Une erreur interne empêche de traiter la requête."),
    UNEXPECTED_ERROR("UNEXPECTED_ERROR", "Une erreur inattendue s'est produite.");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
