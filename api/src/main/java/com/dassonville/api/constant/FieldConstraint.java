package com.dassonville.api.constant;

public class FieldConstraint {

    public static class Category {
        public static final String NAME_NOT_BLANK = "Veuillez renseigner un nom de catégorie.";
        public static final byte NAME_MIN = 2;
        public static final byte NAME_MAX = 50;
        public static final String NAME_SIZE = "Le nom de la catégorie doit faire entre " + NAME_MIN + " et " + NAME_MAX + " caractères.";

        public static final String DESCRIPTION_NOT_BLANK = "Veuillez renseigner une description pour la catégorie.";
        public static final byte DESCRIPTION_MIN = 10;
        public static final short DESCRIPTION_MAX = 250;
        public static final String DESCRIPTION_SIZE = "La description de la catégorie doit faire entre " + DESCRIPTION_MIN + " et " + DESCRIPTION_MAX + " caractères.";

        private Category() {}
    }

    public static class ClassicAnswer {
        public static final String TEXT_NOT_BLANK = "Veuillez renseigner une réponse.";
        public static final byte TEXT_MIN = 2;
        public static final String TEXT_SIZE = "La réponse doit contenir au moins " + TEXT_MIN + " caractères.";

        public static final String IS_CORRECT_NOT_NULL = "Veuillez renseigner si la réponse est correcte ou non.";

        private ClassicAnswer() {}
    }

    public static class DifficultyLevel {
        public static final String NAME_NOT_BLANK = "Veuillez renseigner un nom de niveau de difficulté.";
        public static final byte NAME_MIN = 2;
        public static final byte NAME_MAX = 50;
        public static final String NAME_SIZE = "Le nom du niveau de difficulté doit faire entre " + NAME_MIN + " et " + NAME_MAX + " caractères.";

        public static final String DESCRIPTION_NOT_BLANK = "Veuillez renseigner une description pour le niveau de difficulté.";
        public static final byte DESCRIPTION_MIN = 10;
        public static final short DESCRIPTION_MAX = 250;
        public static final String DESCRIPTION_SIZE = "La description du niveau de difficulté doit faire entre " + DESCRIPTION_MIN + " et " + DESCRIPTION_MAX + " caractères.";

        private DifficultyLevel() {}
    }

    public static class MasteryLevel {
        public static final String NAME_NOT_BLANK = "Veuillez renseigner un nom pour le niveau de maîtrise.";
        public static final byte NAME_MIN = 2;
        public static final byte NAME_MAX = 50;
        public static final String NAME_SIZE = "Le nom du niveau de maîtrise doit faire entre " + NAME_MIN + " et " + NAME_MAX + " caractères.";

        public static final String DESCRIPTION_NOT_BLANK = "Veuillez renseigner une description pour le niveau de maîtrise.";
        public static final byte DESCRIPTION_MIN = 10;
        public static final short DESCRIPTION_MAX = 200;
        public static final String DESCRIPTION_SIZE = "La description du niveau de maîtrise doit faire entre " + DESCRIPTION_MIN + " et " + DESCRIPTION_MAX + " caractères.";

        private MasteryLevel() {}
    }

    public static class Question {
        public static final String TEXT_NOT_BLANK = "Veuillez renseigner le contenu de la question.";
        public static final byte TEXT_MIN = 10;
        public static final String TEXT_SIZE = "La question doit contenir au moins " + TEXT_MIN + " caractères.";

        public static final String ANSWER_EXPLANATION_NOT_BLANK = "Veuillez renseigner une explication pour la réponse.";
        public static final byte ANSWER_EXPLANATION_MIN = 10;
        public static final String ANSWER_EXPLANATION_SIZE = "L'explication de la réponse doit contenir au moins " + ANSWER_EXPLANATION_MIN + " caractères.";

        public static final String TYPE_NOT_NULL = "Veuillez renseigner un type de question.";
        public static final String ANSWER_IF_TRUE_FALSE_NOT_NULL = "Veuillez renseigner si l'affirmation est vraie ou fausse.";

        public static final String AT_LEAST_ONE_CORRECT_ANSWER_REQUIRED = "Veuillez fournir au moins une réponse correcte.";
        public static final String ONLY_UNIQUE_ANSWERS_ALLOWED = "Chaque réponse doit être unique.";

        private Question() {}
    }

    public static class Quiz {
        public static final String TITLE_NOT_BLANK = "Veuillez renseigner un titre pour le quiz.";
        public static final byte TITLE_MIN = 2;
        public static final byte TITLE_MAX = 100;
        public static final String TITLE_SIZE = "Le titre du quiz doit faire entre " + TITLE_MIN + " et " + TITLE_MAX + " caractères.";

        public static final String DESCRIPTION_NOT_BLANK = "Veuillez renseigner une description pour le quiz.";
        public static final byte DESCRIPTION_MIN = 10;
        public static final short DESCRIPTION_MAX = 250;
        public static final String DESCRIPTION_SIZE = "La description du quiz doit faire entre " + DESCRIPTION_MIN + " et " + DESCRIPTION_MAX + " caractères.";

        public static final String MASTERY_LEVEL_NOT_NULL = "Veuillez renseigner un niveau de maîtrise pour le quiz.";
        public static final String TYPE_NOT_NULL = "Veuillez renseigner un type de quiz.";
        public static final String THEME_NOT_NULL = "Veuillez renseigner un thème pour le quiz.";
        public static final String THEME_NOT_FOUND = "Le thème spécifié est introuvable.";
        public static final String CATEGORY_NOT_BELONG_TO_THEME = "La catégorie spécifiée n'appartient pas au thème.";

        private Quiz() {}
    }

    public static class Theme {
        public static final String NAME_NOT_BLANK = "Veuillez renseigner un nom pour le thème.";
        public static final byte NAME_MIN = 2;
        public static final byte NAME_MAX = 50;
        public static final String NAME_SIZE = "Le nom du thème doit faire entre " + NAME_MIN + " et " + NAME_MAX + " caractères.";

        public static final String DESCRIPTION_NOT_BLANK = "Veuillez renseigner une description pour le thème.";
        public static final byte DESCRIPTION_MIN = 10;
        public static final short DESCRIPTION_MAX = 250;
        public static final String DESCRIPTION_SIZE = "La description du thème doit faire entre " + DESCRIPTION_MIN + " et " + DESCRIPTION_MAX + " caractères.";

        private Theme() {}
    }

    public static class CheckAnswer {
        public static final String QUIZ_ID_NOT_NULL = "Veuillez renseigner l'ID du quiz.";
        public static final String QUESTION_ID_NOT_NULL = "Veuillez renseigner l'ID de la question.";
        public static final String DIFFICULTY_ID_NOT_NULL = "Veuillez renseigner l'ID du niveau de difficulté.";
        public static final String TIMER_ENABLED_NOT_NULL = "Veuillez indiquer si le timer est activé ou non.";
        public static final String PENALTIES_ENABLED_NOT_NULL = "Veuillez indiquer si les pénalités sont activées ou non.";
        public static final String ANSWERS_NOT_NULL = "Veuillez renseigner la ou les réponses du joueur.";

        public static final String TYPE_NOT_NULL = "Veuillez renseigner le type de réponse utilisé.";

        private CheckAnswer() {}
    }
}
