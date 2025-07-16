INSERT INTO quizzes (id, title, description, category_id, theme_id, quiz_type_code, mastery_level_id)
VALUES (19, 'Vrac encore',
        'Évaluez vos connaissances en vrac',
        null, 8, 'MIXTE', 2);


-- Insertion des relations entre quiz et questions
INSERT INTO quiz_questions (quiz_id, question_id)
VALUES (19, 272),
       (19, 16),
       (19, 80),
       (19, 120),
       (19, 230),
       (19, 65),
       (19, 100),
       (19, 264),
       (19, 291),
       (19, 300);