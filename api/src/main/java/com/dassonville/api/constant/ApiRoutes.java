package com.dassonville.api.constant;

public class ApiRoutes {

    public static final String API = "/api";
    public static final String API_ADMIN = API + "/admin";
    public static final String ID = "/{id}";

    private ApiRoutes() {}

    public static class Themes {
        public static final String STRING = "/themes";
        public static final String BASE = API + STRING;
        public static final String BY_ID = BASE + ID;
        public static final String ADMIN_THEMES = API_ADMIN + STRING;
        public static final String ADMIN_BY_ID = ADMIN_THEMES + ID;

        private Themes() {}
    }

    public static class Categories {
        public static final String STRING = "/categories";
        public static final String BASE = API + STRING;
        public static final String BY_ID = BASE + ID;
        public static final String ADMIN_CATEGORIES = API_ADMIN + STRING;
        public static final String ADMIN_BY_ID = ADMIN_CATEGORIES + ID;

        private Categories() {}
    }

    public static class DifficultyLevels {
        public static final String STRING = "/difficulty-levels";
        public static final String BASE = API + STRING;
        public static final String BY_ID = BASE + ID;
        public static final String ADMIN_DIFFICULTY_LEVELS = API_ADMIN + STRING;
        public static final String ADMIN_BY_ID = ADMIN_DIFFICULTY_LEVELS + ID;

        private DifficultyLevels() {}
    }

}
