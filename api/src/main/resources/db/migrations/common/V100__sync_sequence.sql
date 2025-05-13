

-- Met à jour automatiquement la séquence de chaque table en fonction de la valeur max actuelle
DO $$
    BEGIN
        -- Pour quizzes
        PERFORM setval('quizzes_id_seq', COALESCE((SELECT MAX(id) FROM quizzes), 0) + 1, false);

        -- Pour questions
        PERFORM setval('questions_id_seq', COALESCE((SELECT MAX(id) FROM questions), 0) + 1, false);

        -- Pour answers
        PERFORM setval('answers_id_seq', COALESCE((SELECT MAX(id) FROM answers), 0) + 1, false);
    END $$;
