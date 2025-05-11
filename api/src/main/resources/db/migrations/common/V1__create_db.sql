---------------------------------------------------
-- Créer la table des utilisateurs
---------------------------------------------------
CREATE TABLE users
(
    id             UUID                  DEFAULT gen_random_uuid() PRIMARY KEY,
    username       VARCHAR(50)  NOT NULL UNIQUE,
    email          VARCHAR(150) NOT NULL UNIQUE,
    password       VARCHAR(255) NOT NULL,
    registered_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    validated_at   TIMESTAMP,
    updated_at     TIMESTAMP,
    connected_at   TIMESTAMP,
    deleted_at     TIMESTAMP,
    public_profile BOOLEAN      NOT NULL DEFAULT TRUE
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
    id_user UUID    NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    id_role INTEGER NOT NULL REFERENCES roles (id) ON DELETE CASCADE,
    PRIMARY KEY (id_user, id_role)
);

---------------------------------------------------
-- Créer la table des thèmes
---------------------------------------------------
CREATE TABLE themes
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(250),
    created_at  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP,
    disabled_at TIMESTAMP
);

---------------------------------------------------
-- Créer la table des catégories
---------------------------------------------------
CREATE TABLE categories
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(250),
    created_at  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP,
    disabled_at TIMESTAMP,
    id_theme    INTEGER     NOT NULL REFERENCES themes (id) ON DELETE CASCADE
);

---------------------------------------------------
-- Créer la table des niveaux de difficulté
---------------------------------------------------
CREATE TABLE difficulty_levels
(
    id                  SERIAL PRIMARY KEY,
    name                VARCHAR(50) NOT NULL UNIQUE,
    max_responses       SMALLINT    NOT NULL,
    timer_seconds       SMALLINT    NOT NULL DEFAULT 0,
    points_per_question SMALLINT    NOT NULL,
    is_reference        BOOLEAN     NOT NULL DEFAULT FALSE,
    created_at          TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP,
    disabled_at         TIMESTAMP
);

---------------------------------------------------
-- Créer la table des quiz
---------------------------------------------------
CREATE TABLE quizzes
(
    id          SERIAL PRIMARY KEY,
    title       VARCHAR(100) NOT NULL UNIQUE,
    is_vip_only BOOLEAN      NOT NULL DEFAULT FALSE,
    created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP,
    disabled_at TIMESTAMP,
    id_category INTEGER      REFERENCES categories (id) ON DELETE SET NULL,
    id_theme    INTEGER      NOT NULL REFERENCES themes (id) ON DELETE RESTRICT
);

---------------------------------------------------
-- Créer la table des questions
---------------------------------------------------
CREATE TABLE questions
(
    id          SERIAL PRIMARY KEY,
    text        VARCHAR(300) NOT NULL,
    created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP,
    disabled_at TIMESTAMP,
    id_quiz     INTEGER      NOT NULL REFERENCES quizzes (id) ON DELETE CASCADE
);

---------------------------------------------------
-- Créer la table des réponses
---------------------------------------------------
CREATE TABLE answers
(
    id          SERIAL PRIMARY KEY,
    text        VARCHAR(150) NOT NULL,
    is_correct  BOOLEAN      NOT NULL DEFAULT FALSE,
    created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP,
    disabled_at TIMESTAMP,
    id_question INTEGER      NOT NULL REFERENCES questions (id) ON DELETE CASCADE
);


---------------------------------------------------
-- Créer la table des tentatives
---------------------------------------------------
CREATE TABLE attempts
(
    id                  SERIAL PRIMARY KEY,
    attempts_date       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    score               INTEGER   NOT NULL DEFAULT 0,
    is_finished         BOOLEAN   NOT NULL DEFAULT FALSE,
    id_quiz             INTEGER   NOT NULL REFERENCES quizzes (id) ON DELETE CASCADE,
    id_difficulty_level INTEGER   NOT NULL REFERENCES difficulty_levels (id) ON DELETE CASCADE,
    id_user             UUID      NOT NULL REFERENCES users (id) ON DELETE SET NULL
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
    UNIQUE (id_attempt, id_question, id_answer)
);


---------------------------------------------------
-- Indexes
---------------------------------------------------

-- Pour accélérer les recherches de catégories par thème
CREATE INDEX idx_categories_theme ON categories (id_theme);

-- Pour les requêtes de filtrage ou de jointure des quiz par catégorie et thème
CREATE INDEX idx_quiz_category ON quizzes (id_category);
CREATE INDEX idx_quiz_theme ON quizzes (id_theme);

-- Pour accélérer les jointures entre questions et leurs réponses
CREATE INDEX idx_answers_question ON answers (id_question);

-- Pour les requêtes cherchant les réponses d'une question pour une tentative
CREATE INDEX idx_attempt_responses_attempt ON attempts_responses (id_attempt);
CREATE INDEX idx_attempt_responses_question ON attempts_responses (id_question);
CREATE INDEX idx_attempt_responses_answer ON attempts_responses (id_answer);

-- Pour récupérer rapidement les tentatives d'un utilisateur
CREATE INDEX idx_attempts_user ON attempts (id_user);

-- Pour récupérer les tentatives sur un quiz spécifique
CREATE INDEX idx_attempts_quiz ON attempts (id_quiz);

