---------------------------------------------------
-- Création du trigger pour créer automatiquement une règle de jeu par défaut lors de l'insertion d'un type de question
---------------------------------------------------

CREATE OR REPLACE FUNCTION auto_create_default_game_rule()
    RETURNS TRIGGER AS
$$
DECLARE
    ref_difficulty_id INTEGER;
BEGIN
    -- Récupérer l'ID du niveau de difficulté de référence
    SELECT id
    INTO ref_difficulty_id
    FROM difficulty_levels
    WHERE is_reference = TRUE;

    IF ref_difficulty_id IS NULL THEN
        RAISE EXCEPTION 'Aucun niveau de difficulté de référence trouvé.';
    END IF;

    -- Créer une règle game_rules avec des paramètres par défaut
    INSERT INTO game_rules (question_type_code,
                            difficulty_level_id,
                            answer_options_count,
                            points_per_good_answer,
                            points_penalty_per_wrong_answer,
                            timer_seconds,
                            points_timer_multiplier,
                            points_penalty_multiplier,
                            combo_2_bonus,
                            combo_3_bonus,
                            combo_4_bonus,
                            combo_5_bonus,
                            created_at)
    VALUES (NEW.code,
            ref_difficulty_id,
            0,
            5,
            1,
            30,
            1.2,
            1.0,
            2,
            3,
            5,
            8,
            CURRENT_TIMESTAMP);

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


----------------------------------------------------

-- Exécution de la fonction après l'insertion d'un type de question
CREATE TRIGGER trg_create_game_rule_on_question_type_insert
    AFTER INSERT
    ON question_types
    FOR EACH ROW
EXECUTE FUNCTION auto_create_default_game_rule();
