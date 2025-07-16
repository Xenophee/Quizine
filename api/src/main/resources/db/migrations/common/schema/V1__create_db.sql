---------------------------------------------------
-- Créer la table des utilisateurs
---------------------------------------------------
CREATE TABLE users
(
    id             UUID                  DEFAULT gen_random_uuid() PRIMARY KEY,
    username       VARCHAR(50)  NOT NULL UNIQUE,
    email          VARCHAR(150) NOT NULL UNIQUE,
    password       VARCHAR(255) NOT NULL,
    public_profile BOOLEAN      NOT NULL DEFAULT TRUE,
    registered_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    validated_at   TIMESTAMP,
    updated_at     TIMESTAMP,
    connected_at   TIMESTAMP,
    deleted_at     TIMESTAMP
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
CREATE TABLE user_roles
(
    user_id UUID    NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    role_id INTEGER NOT NULL REFERENCES roles (id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, role_id)
);

---------------------------------------------------
-- Créer la table des thèmes
---------------------------------------------------
CREATE TABLE themes
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(50)  NOT NULL UNIQUE,
    description VARCHAR(250) NOT NULL,
    is_default  BOOLEAN      NOT NULL DEFAULT FALSE, -- Indique si c'est le thème par défaut
    created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP,
    disabled_at TIMESTAMP
);

CREATE UNIQUE INDEX uniq_themes_is_default
    ON themes (is_default)
    WHERE is_default = TRUE;

---------------------------------------------------
-- Créer la table des catégories
---------------------------------------------------
CREATE TABLE categories
(
    id          SERIAL PRIMARY KEY,
    theme_id    INTEGER      NOT NULL REFERENCES themes (id) ON DELETE CASCADE,
    name        VARCHAR(50)  NOT NULL UNIQUE,
    description VARCHAR(250) NOT NULL,
    created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP,
    disabled_at TIMESTAMP
);


---------------------------------------------------
-- Créer la table des types de quiz
---------------------------------------------------
CREATE TABLE quiz_types
(
    code        VARCHAR(50) PRIMARY KEY,
    name        VARCHAR(50)  NOT NULL UNIQUE,
    description VARCHAR(250) NOT NULL,
    created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP,
    disabled_at TIMESTAMP
);


---------------------------------------------------
-- Créer la table des types de questions
---------------------------------------------------
CREATE TABLE question_types
(
    code        VARCHAR(50) PRIMARY KEY,
    name        VARCHAR(50)  NOT NULL UNIQUE,
    description VARCHAR(250) NOT NULL, -- à destination de l'admin
    instruction VARCHAR(250) NOT NULL, -- affichée à l'utilisateur
    created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP
);


---------------------------------------------------
-- Créer la table d'association entre types de quiz et types de questions
---------------------------------------------------
CREATE TABLE quiz_type_questions
(
    quiz_type_code     VARCHAR(50) NOT NULL REFERENCES quiz_types (code) ON DELETE CASCADE,
    question_type_code VARCHAR(50) NOT NULL REFERENCES question_types (code) ON DELETE CASCADE,
    PRIMARY KEY (quiz_type_code, question_type_code)
);


---------------------------------------------------
-- Créer la table des niveaux de maîtrise (informations sur la difficulté du quiz)
---------------------------------------------------
CREATE TABLE mastery_levels
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(50)  NOT NULL UNIQUE,
    description VARCHAR(200) NOT NULL,
    rank        INTEGER      NOT NULL UNIQUE,
    created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP,
    disabled_at TIMESTAMP
);


---------------------------------------------------
-- Créer la table des quiz
---------------------------------------------------
CREATE TABLE quizzes
(
    id               SERIAL PRIMARY KEY,
    quiz_type_code   VARCHAR(50)  NOT NULL REFERENCES quiz_types (code) ON DELETE RESTRICT,
    theme_id         INTEGER      NOT NULL REFERENCES themes (id) ON DELETE RESTRICT,
    category_id      INTEGER      REFERENCES categories (id) ON DELETE SET NULL,
    mastery_level_id INTEGER      NOT NULL REFERENCES mastery_levels (id) ON DELETE RESTRICT,
    title            VARCHAR(100) NOT NULL UNIQUE,
    description      VARCHAR(250) NOT NULL,
    created_at       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP,
    disabled_at      TIMESTAMP
);

---------------------------------------------------
-- Créer la table des questions
---------------------------------------------------
CREATE TABLE questions
(
    id                   SERIAL PRIMARY KEY,
    question_type_code   VARCHAR(50) NOT NULL REFERENCES question_types (code) ON DELETE RESTRICT,
    text                 TEXT        NOT NULL,
    answer_explanation   TEXT        NOT NULL,
    answer_if_true_false BOOLEAN              DEFAULT NULL, -- Uniquement pour les questions de type Vrai/Faux
    created_at           TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at           TIMESTAMP,
    disabled_at          TIMESTAMP
);


---------------------------------------------------
-- Créer la table d'association entre quiz et questions
---------------------------------------------------
CREATE TABLE quiz_questions
(
    quiz_id     INTEGER NOT NULL REFERENCES quizzes (id) ON DELETE CASCADE,
    question_id INTEGER NOT NULL REFERENCES questions (id) ON DELETE CASCADE,
    PRIMARY KEY (quiz_id, question_id)
);


---------------------------------------------------
-- Créer la table des réponses aux questions classiques
---------------------------------------------------
CREATE TABLE classic_answers
(
    id          SERIAL PRIMARY KEY,
    question_id INTEGER   NOT NULL REFERENCES questions (id) ON DELETE CASCADE,
    text        TEXT      NOT NULL,
    is_correct  BOOLEAN   NOT NULL DEFAULT FALSE,
    created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP,
    disabled_at TIMESTAMP
);


---------------------------------------------------
-- Créer la table des niveaux de difficulté
---------------------------------------------------
CREATE TABLE difficulty_levels
(
    id           SERIAL PRIMARY KEY,
    name         VARCHAR(50)  NOT NULL UNIQUE,                                                                 -- Nom thématique de la difficulté
    is_reference BOOLEAN      NOT NULL DEFAULT FALSE,                                                          -- Critère pour empêcher la désactivation
    label        VARCHAR(10)  NOT NULL CHECK (label IN ('FACILE', 'MOYEN', 'DIFFICILE', 'EXPERT', 'SPÉCIAL')), -- Label pour la compréhension UX
    description  VARCHAR(250) NOT NULL,
    rank         SMALLINT CHECK (
        (label = 'FACILE' AND rank = 1) OR
        (label = 'MOYEN' AND rank = 2) OR
        (label = 'DIFFICILE' AND rank = 3) OR
        (label = 'EXPERT' AND rank = 4) OR
        (label = 'SPÉCIAL' AND rank IS NULL)
        ),                                                                                                     -- Rang de difficulté, avec une correspondance stricte entre label et rang
    starts_at    DATE,                                                                                         -- Date de début de validité du niveau de difficulté 'SPÉCIAL'
    ends_at      DATE,                                                                                         -- Date de fin de validité du niveau de difficulté 'SPÉCIAL'
    is_recurring BOOLEAN      NOT NULL DEFAULT FALSE,
    created_at   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP,
    disabled_at  TIMESTAMP,
    CHECK (starts_at IS NULL OR ends_at IS NULL OR starts_at < ends_at),                                       -- Vérifie que les dates de validité sont cohérentes
    CHECK (label = 'SPÉCIAL' OR (starts_at IS NULL AND ends_at IS NULL)),                                      -- Vérifie que les niveaux de difficulté autres que 'SPÉCIAL' n'ont pas de dates de validité
    CHECK (label <> 'SPÉCIAL' OR (starts_at IS NOT NULL AND ends_at IS NOT NULL)),
    CHECK (label = 'SPÉCIAL' OR is_recurring = FALSE)
);

-- Contraintes supplémentaires via index partiels
CREATE UNIQUE INDEX uniq_difficulty_rank -- Assure que chaque rang est unique lorsqu'il est défini
    ON difficulty_levels (rank)
    WHERE rank IS NOT NULL;

CREATE UNIQUE INDEX uniq_difficulty_label -- Assure que chaque label est unique, sauf pour 'SPECIAL'
    ON difficulty_levels (label)
    WHERE label <> 'SPÉCIAL';


---------------------------------------------------
-- Créer la table d'association entre types de questions et niveaux de difficulté
---------------------------------------------------
CREATE TABLE question_type_difficulty
(
    difficulty_level_id INTEGER     NOT NULL REFERENCES difficulty_levels (id) ON DELETE CASCADE,
    question_type_code  VARCHAR(50) NOT NULL REFERENCES question_types (code) ON DELETE CASCADE,
    PRIMARY KEY (difficulty_level_id, question_type_code)
);


---------------------------------------------------
-- Créer la table des règles de quiz
---------------------------------------------------
CREATE TABLE game_rules
(
    id                              SERIAL PRIMARY KEY,
    question_type_code              VARCHAR(50)   NOT NULL REFERENCES question_types (code) ON DELETE CASCADE,
    difficulty_level_id             INTEGER       NOT NULL REFERENCES difficulty_levels (id) ON DELETE CASCADE,
    answer_options_count            SMALLINT      NOT NULL CHECK (answer_options_count >= 0),
    points_per_good_answer          SMALLINT      NOT NULL CHECK (points_per_good_answer >= 0),
    points_penalty_per_wrong_answer SMALLINT      NOT NULL CHECK (points_penalty_per_wrong_answer >= 1),
    timer_seconds                   SMALLINT      NOT NULL CHECK (timer_seconds >= 0),
    points_timer_multiplier         NUMERIC(4, 2) NOT NULL CHECK (points_timer_multiplier >= 1.0),
    points_penalty_multiplier       NUMERIC(4, 2) NOT NULL CHECK (points_penalty_multiplier >= 1.0),
    combo_2_bonus                   SMALLINT      NOT NULL CHECK (combo_2_bonus >= 0),
    combo_3_bonus                   SMALLINT      NOT NULL CHECK (combo_3_bonus >= 0),
    combo_4_bonus                   SMALLINT      NOT NULL CHECK (combo_4_bonus >= 0),
    combo_5_bonus                   SMALLINT      NOT NULL CHECK (combo_5_bonus >= 0),
    created_at                      TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                      TIMESTAMP,
    disabled_at                     TIMESTAMP,
    UNIQUE (question_type_code, difficulty_level_id)
);



---------------------------------------------------
-- Créer la table des tentatives
---------------------------------------------------
CREATE TABLE quiz_sessions
(
    id                     UUID                  DEFAULT gen_random_uuid() PRIMARY KEY,
    quiz_id                INTEGER      REFERENCES quizzes (id) ON DELETE SET NULL,
    quiz_type_code         VARCHAR(50)  REFERENCES quiz_types (code) ON DELETE SET NULL,
    mastery_level_id       INTEGER      REFERENCES mastery_levels (id) ON DELETE SET NULL,
    theme_id               INTEGER      REFERENCES themes (id) ON DELETE SET NULL,
    category_id            INTEGER      REFERENCES categories (id) ON DELETE SET NULL,
    difficulty_level_id    INTEGER      REFERENCES difficulty_levels (id) ON DELETE SET NULL,
    user_id                UUID REFERENCES users (id) ON DELETE CASCADE,
    quiz_title             VARCHAR(100) NOT NULL,
    quiz_type_name         VARCHAR(50)  NOT NULL,
    mastery_level_label    VARCHAR(50)  NOT NULL,
    difficulty_level_code  VARCHAR(50)  NOT NULL,
    difficulty_level_label VARCHAR(10)  NOT NULL,
    has_timer_enabled      BOOLEAN      NOT NULL DEFAULT FALSE,
    has_penalties_enabled  BOOLEAN      NOT NULL DEFAULT FALSE,
    theme_name             VARCHAR(50)  NOT NULL,
    category_name          VARCHAR(70)  NOT NULL,
    score                  SMALLINT     NOT NULL DEFAULT 0,
    started_at             TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_finished            BOOLEAN      NOT NULL DEFAULT FALSE
);


---------------------------------------------------
-- Créer la table des réponses aux tentatives
---------------------------------------------------
CREATE TABLE quiz_session_questions
(
    id                  SERIAL PRIMARY KEY,
    quiz_session_id     UUID         NOT NULL REFERENCES quiz_sessions (id) ON DELETE CASCADE,
    question_id         INTEGER      NOT NULL REFERENCES questions (id) ON DELETE SET NULL,
    question_text       VARCHAR(350) NOT NULL,
    question_type_name  VARCHAR(50)  NOT NULL,
    possible_answers    JSONB        NOT NULL,
    user_answers        JSONB,
    is_correct          BOOLEAN      NOT NULL DEFAULT FALSE,
    question_started_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    answer_received_at  TIMESTAMP
);