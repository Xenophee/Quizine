CREATE TABLE app_settings
(
    key   TEXT PRIMARY KEY,
    value TEXT NOT NULL
);

-- Ajout de la constante pour le seuil de questions actives
INSERT INTO app_settings (key, value)
VALUES ('MIN_ACTIVE_QUESTIONS_PER_QUIZ', '10');
