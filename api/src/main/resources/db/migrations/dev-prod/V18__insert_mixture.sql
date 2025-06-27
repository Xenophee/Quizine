INSERT INTO quizzes (id, title, description, category_id, theme_id, quiz_type_code, mastery_level_id)
VALUES (15, 'Vrac',
        'Évaluez vos connaissances en vrac',
        null, 8, 'MIXTE', 2);


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
       (15, 211);