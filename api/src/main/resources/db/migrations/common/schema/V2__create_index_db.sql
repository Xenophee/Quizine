---------------------------------------------------
-- Indexes
---------------------------------------------------

-- Pour accélérer les recherches de catégories par thème
CREATE INDEX idx_categories_theme ON categories (theme_id);

-- Pour les requêtes de filtrage ou de jointure des quiz par catégorie et thème
CREATE INDEX idx_quiz_category ON quizzes (category_id);
CREATE INDEX idx_quiz_theme ON quizzes (theme_id);

-- Pour accélérer les jointures entre questions et leurs réponses
CREATE INDEX idx_answers_question ON classic_answers (question_id);



---------------------------------------------------
-- Indexes partiels
---------------------------------------------------

-- Pour les données actives
CREATE INDEX idx_active_quizzes ON quizzes (disabled_at) WHERE disabled_at IS NULL;
CREATE INDEX idx_active_questions ON questions (disabled_at) WHERE disabled_at IS NULL;
CREATE INDEX idx_active_classic_answers ON classic_answers (disabled_at) WHERE disabled_at IS NULL;

CREATE INDEX idx_active_difficulty_levels ON difficulty_levels (disabled_at) WHERE disabled_at IS NULL;
CREATE INDEX idx_active_game_rules ON game_rules (disabled_at) WHERE disabled_at IS NULL;

CREATE INDEX idx_active_themes ON themes (disabled_at) WHERE disabled_at IS NULL;
CREATE INDEX idx_active_categories ON categories (disabled_at) WHERE disabled_at IS NULL;

CREATE INDEX idx_active_quiz_types ON quiz_types (disabled_at) WHERE disabled_at IS NULL;
CREATE INDEX idx_active_mastery_levels ON mastery_levels (disabled_at) WHERE disabled_at IS NULL;