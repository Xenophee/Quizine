INSERT INTO quizzes (id, title, description, category_id, theme_id, quiz_type_code, mastery_level_id, created_at,
                     updated_at, disabled_at)
VALUES (15, 'Vrac',
        'Évaluez vos connaissances en vrac',
        null, 8, 'MIXTE', 2,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)

ON CONFLICT (id) DO UPDATE SET title            = EXCLUDED.title,
                               description      = EXCLUDED.description,
                               category_id      = EXCLUDED.category_id,
                               theme_id         = EXCLUDED.theme_id,
                               mastery_level_id = EXCLUDED.mastery_level_id,
                               created_at       = EXCLUDED.created_at,
                               updated_at       = EXCLUDED.updated_at,
                               disabled_at      = EXCLUDED.disabled_at;


-- Insertion des relations entre quiz et questions
INSERT INTO quiz_questions (quiz_id, question_id)
VALUES (15, 272),
       (15, 16),
       (15, 80),
       (15, 120),
       (15, 230),
       (15, 65),
       (15, 100),
       (15, 264),
       (15, 22),
       (15, 58),
       (15, 94),
       (15, 256),
       (15, 133),
       (15, 41),
       (15, 79),
       (15, 157),
       (15, 210),
       (15, 243),
       (15, 270),
       (15, 211)
ON CONFLICT (quiz_id, question_id) DO NOTHING;


-- Mise à jour de la séquence pour les quiz
SELECT setval('quizzes_id_seq', (SELECT MAX(id) FROM quizzes));