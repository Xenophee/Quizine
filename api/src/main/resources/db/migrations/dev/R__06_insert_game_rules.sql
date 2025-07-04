ALTER TABLE game_rules DISABLE TRIGGER trg_sync_question_type_difficulty_on_game_rule_insert;

---------------------------------------------------
-- Insertion des règles de jeu
---------------------------------------------------

INSERT INTO game_rules (id, answer_options_count, points_per_good_answer, points_penalty_per_wrong_answer,
                        timer_seconds, points_timer_multiplier, points_penalty_multiplier,
                        combo_2_bonus, combo_3_bonus, combo_4_bonus, combo_5_bonus,
                        question_type_code, difficulty_level_id)
VALUES (1, 2, 5, 2, 30,
        1.2, 1.1, 1, 2, 3, 4,
        'CLASSIC', 4),

       (4, 4, 8, 4, 45,
        1.5, 1.3, 2, 3, 4, 5,
        'CLASSIC', 1),

       (3, 0, 12, 6, 60,
        1.8, 1.6, 3, 5, 7, 9,
        'CLASSIC', 3),

       (2, 0, 6, 3, 20,
        1.4, 1.2, 2, 3, 4, 5,
        'TRUE_FALSE', 1)

ON CONFLICT (id) DO UPDATE SET
    answer_options_count = EXCLUDED.answer_options_count,
    points_per_good_answer = EXCLUDED.points_per_good_answer,
    points_penalty_per_wrong_answer = EXCLUDED.points_penalty_per_wrong_answer,
    timer_seconds = EXCLUDED.timer_seconds,
    points_timer_multiplier = EXCLUDED.points_timer_multiplier,
    points_penalty_multiplier = EXCLUDED.points_penalty_multiplier,
    combo_2_bonus = EXCLUDED.combo_2_bonus,
    combo_3_bonus = EXCLUDED.combo_3_bonus,
    combo_4_bonus = EXCLUDED.combo_4_bonus,
    combo_5_bonus = EXCLUDED.combo_5_bonus,
    question_type_code = EXCLUDED.question_type_code,
    difficulty_level_id = EXCLUDED.difficulty_level_id;


---------------------------------------------------
-- Insertion des relations entre question types et niveaux de difficulté
---------------------------------------------------

INSERT INTO question_type_difficulty (question_type_code, difficulty_level_id)
VALUES ('CLASSIC', 1), -- Type de question Classique avec niveau de difficulté Moyen
       ('CLASSIC', 3), -- Type de question Classique avec niveau de difficulté Difficile
       ('CLASSIC', 4), -- Type de question Classique avec niveau de difficulté Facile
       ('TRUE_FALSE', 1) -- Type de question Vrai/Faux avec niveau de difficulté Moyen

ON CONFLICT (question_type_code, difficulty_level_id) DO NOTHING;


---------------------------------------------------
-- Mise à jour des séquences pour les règles de jeu
---------------------------------------------------
SELECT setval('game_rules_id_seq', (SELECT MAX(id) FROM game_rules));

ALTER TABLE game_rules ENABLE TRIGGER trg_sync_question_type_difficulty_on_game_rule_insert;