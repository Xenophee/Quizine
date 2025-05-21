package com.dassonville.api.constant;

public class ApiRoutes {

    public static final String API = "/api";
    public static final String API_ADMIN = API + "/admin";
    public static final String ID = "/{id}";
    public static final String VISIBILITY = ID + "/visibility";
    public static final String DETAILS = "/details";
    public static final String SUMMARY = "/summary";
    public static final String REORDER = "/reorder";

    private ApiRoutes() {}

    public static class Themes {
        public static final String STRING = "/themes";
        public static final String BASE = API + STRING;
        public static final String BY_ID = BASE + ID;
        public static final String ADMIN_THEMES = API_ADMIN + STRING;
        public static final String ADMIN_BY_ID = ADMIN_THEMES + ID;

        public static final String ADMIN_VISIBILITY_PATCH = ADMIN_THEMES + VISIBILITY;

        private Themes() {}
    }

    public static class Categories {
        public static final String STRING = "/categories";
        public static final String BASE = API + STRING;
        public static final String BY_ID = BASE + ID;
        public static final String ADMIN_CATEGORIES = API_ADMIN + STRING;
        public static final String ADMIN_BY_ID = ADMIN_CATEGORIES + ID;

        public static final String ADMIN_QUESTIONS_POST = Themes.ADMIN_BY_ID + STRING;
        public static final String ADMIN_VISIBILITY_PATCH = ADMIN_CATEGORIES + VISIBILITY;

        private Categories() {}
    }

    public static class DifficultyLevels {
        public static final String STRING = "/difficulty-levels";
        public static final String BASE = API + STRING;
        public static final String BY_ID = BASE + ID;
        public static final String ADMIN_DIFFICULTY_LEVELS = API_ADMIN + STRING;
        public static final String ADMIN_BY_ID = ADMIN_DIFFICULTY_LEVELS + ID;

        public static final String ADMIN_VISIBILITY_PATCH = ADMIN_DIFFICULTY_LEVELS + VISIBILITY;

        private DifficultyLevels() {}
    }

    public static class Quizzes {
        public static final String STRING = "/quizzes";
        public static final String BASE = API + STRING;
        public static final String BY_ID = BASE + ID;
        public static final String ADMIN_QUIZZES = API_ADMIN + STRING;
        public static final String ADMIN_BY_ID = ADMIN_QUIZZES + ID;

        public static final String ADMIN_VISIBILITY_PATCH = ADMIN_QUIZZES + VISIBILITY;

        private Quizzes() {}
    }

    public static class Questions {
        public static final String STRING = "/questions";
        public static final String BASE = API + STRING;
        public static final String BY_ID = BASE + ID;
        public static final String ADMIN_QUESTIONS = API_ADMIN + STRING;
        public static final String ADMIN_BY_ID = ADMIN_QUESTIONS + ID;

        public static final String ADMIN_QUESTIONS_POST = Quizzes.ADMIN_BY_ID + STRING;
        public static final String ADMIN_VISIBILITY_PATCH = ADMIN_QUESTIONS + VISIBILITY;

        private Questions() {}
    }

    public static class Answers {
        public static final String STRING = "/answers";
        public static final String BASE = API + STRING;
        public static final String BY_ID = BASE + ID;
        public static final String ADMIN_ANSWERS = API_ADMIN + STRING;
        public static final String ADMIN_BY_ID = ADMIN_ANSWERS + ID;

        public static final String ADMIN_ANSWERS_POST = Questions.ADMIN_BY_ID + STRING;
        public static final String ADMIN_VISIBILITY_PATCH = ADMIN_ANSWERS + VISIBILITY;

        private Answers() {}
    }
}
