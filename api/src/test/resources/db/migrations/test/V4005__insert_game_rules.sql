---------------------------------------------------
-- Insertion des règles de jeu
---------------------------------------------------

INSERT INTO game_rules (id, answer_options_count, points_per_good_answer,
                        points_penalty_per_wrong_answer,
                        timer_seconds, points_timer_multiplier, points_penalty_multiplier,
                        combo_2_bonus, combo_3_bonus, combo_4_bonus, combo_5_bonus,
                        question_type_code, difficulty_level_id)
VALUES (1, 2, 5, 2, 30,
        1.2, 1.1, 1, 2, 3, 4,
        'CLASSIC', 4), -- FACILE

       (4, 4, 8, 4, 45,
        1.5, 1.3, 2, 3, 4, 5,
        'CLASSIC', 1), -- MOYEN

       (3, 0, 12, 6, 60,
        1.8, 1.6, 3, 5, 7, 9,
        'CLASSIC', 3), -- DIFFICILE

       (2, 0, 6, 3, 20,
        1.4, 1.2, 2, 3, 4, 5,
        'TRUE_FALSE', 1), -- MOYEN

       (5, 0, 0, 20, 30,
        1.5, 1.3, 2, 3, 4, 5,
        'TRUE_FALSE', 5), -- SPÉCIAL (Quizine troll)

       (6, 0, 15, 1, 90,
        2.0, 1.8, 4, 6, 8, 10,
        'CLASSIC', 6); -- SPÉCIAL (Quizine pour les nuls)
