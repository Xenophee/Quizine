CREATE SCHEMA IF NOT EXISTS dev;
SET
search_path TO dev;

---------------------------------------------------
-- Créer la table des utilisateurs
---------------------------------------------------
CREATE TABLE users
(
    id            UUID                  DEFAULT gen_random_uuid() PRIMARY KEY,
    username      VARCHAR(50)  NOT NULL UNIQUE,
    email         VARCHAR(150) NOT NULL UNIQUE,
    password      VARCHAR(255) NOT NULL,
    registered_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    validated_at  TIMESTAMP,
    updated_at    TIMESTAMP,
    connected_at  TIMESTAMP,
    deleted_at    TIMESTAMP,
    public        BOOLEAN      NOT NULL DEFAULT TRUE
);

---------------------------------------------------
-- Créer la table des rôles
---------------------------------------------------
CREATE TABLE roles
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

---------------------------------------------------
-- Créer la table d'association entre utilisateurs et rôles
---------------------------------------------------
CREATE TABLE users_roles
(
    id_user INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    id_role INTEGER NOT NULL REFERENCES roles(id) ON DELETE CASCADE,
    PRIMARY KEY (id_user, id_role)
);

---------------------------------------------------
-- Créer la table des thèmes
---------------------------------------------------
CREATE TABLE themes
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(50) NOT NULL,
    description VARCHAR(250),
);

---------------------------------------------------
-- Créer la table des catégories
---------------------------------------------------
CREATE TABLE categories
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(70) NOT NULL,
    description VARCHAR(250),
    id_theme    INTEGER     NOT NULL REFERENCES themes (id) ON DELETE CASCADE
);

---------------------------------------------------
-- Créer la table des niveaux de difficulté
---------------------------------------------------
CREATE TABLE difficulty_levels
(
    id                  SERIAL PRIMARY KEY,
    name                VARCHAR(50) NOT NULL,
    max_responses       SMALLINT    NOT NULL,
    has_timer           BOOLEAN     NOT NULL,
    points_per_question SMALLINT    NOT NULL
);

---------------------------------------------------
-- Créer la table des quiz
---------------------------------------------------
CREATE TABLE quiz
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(50)  NOT NULL,
    alt         VARCHAR(150) NOT NULL,
    id_category INTEGER      NOT NULL REFERENCES categories (id) ON DELETE CASCADE,
    id_theme    INTEGER      NOT NULL REFERENCES themes (id) ON DELETE CASCADE
);

---------------------------------------------------
-- Créer la table des questions
---------------------------------------------------
CREATE TABLE questions
(
    id    SERIAL PRIMARY KEY,
    title VARCHAR(300) NOT NULL
);

---------------------------------------------------
-- Créer la table des réponses
---------------------------------------------------
CREATE TABLE answers
(
    id          SERIAL PRIMARY KEY,
    title       VARCHAR(150) NOT NULL,
    is_correct  BOOLEAN      NOT NULL,
    id_question INTEGER      NOT NULL,
    FOREIGN KEY (id_question) REFERENCES questions (id)
);

---------------------------------------------------
-- Créer la table qui lie les quiz aux questions
---------------------------------------------------
CREATE TABLE quiz_questions
(
    id_quiz     INTEGER REFERENCES quiz (id),
    id_question INTEGER REFERENCES questions (id),
    PRIMARY KEY (id_quiz, id_question)
);

---------------------------------------------------
-- Créer la table des tentatives
---------------------------------------------------
CREATE TABLE attempts
(
    id                  SERIAL PRIMARY KEY,
    attempts_date       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status              VARCHAR(20)        DEFAULT 'in_progress',
    score               INTEGER   NOT NULL DEFAULT 0,
    id_quiz             INTEGER   NOT NULL REFERENCES quiz (id) ON DELETE CASCADE,
    id_difficulty_level INTEGER   NOT NULL REFERENCES difficulty_levels (id) ON DELETE CASCADE,
    id_user             INTEGER   NOT NULL REFERENCES users (id) ON DELETE SET NULL
);

---------------------------------------------------
-- Créer la table des réponses aux tentatives
---------------------------------------------------
CREATE TABLE attempts_responses
(
    id            SERIAL PRIMARY KEY,
    response_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    id_attempt    INTEGER NOT NULL REFERENCES attempts (id) ON DELETE CASCADE,
    id_question   INTEGER NOT NULL REFERENCES questions (id) ON DELETE CASCADE,
    id_answer     INTEGER REFERENCES answers (id), -- Null si aucune réponse
    UNIQUE (attempt_id, question_id, answer_id)
);


---------------------------------------------------
-- Indexes
---------------------------------------------------

-- Pour accélérer les recherches de catégories par thème
CREATE INDEX idx_categories_theme ON categories (id_theme);

-- Pour les requêtes de filtrage ou de jointure des quiz par catégorie et thème
CREATE INDEX idx_quiz_category ON quiz (id_category);
CREATE INDEX idx_quiz_theme ON quiz (id_theme);

-- Pour accélérer les jointures entre questions et leurs réponses
CREATE INDEX idx_answers_question ON answers (id_question);

-- Pour les requêtes cherchant les questions associées à un quiz
CREATE INDEX idx_quiz_questions_quiz ON quiz_questions (id_quiz);
CREATE INDEX idx_quiz_questions_question ON quiz_questions (id_question);

-- Pour les requêtes cherchant les réponses d'une question pour une tentative
CREATE INDEX idx_attempt_responses_attempt ON attempts_responses (id_attempt);
CREATE INDEX idx_attempt_responses_question ON attempts_responses (id_question);
CREATE INDEX idx_attempt_responses_answer ON attempts_responses (id_answer);

-- Pour récupérer rapidement les tentatives d'un utilisateur
CREATE INDEX idx_attempts_user ON attempts (id_user);

-- Pour récupérer les tentatives sur un quiz spécifique
CREATE INDEX idx_attempts_quiz ON attempts (id_quiz);

