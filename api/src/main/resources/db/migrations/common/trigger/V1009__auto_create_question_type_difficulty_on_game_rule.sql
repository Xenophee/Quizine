---------------------------------------------------
-- Création du trigger pour synchroniser question_type_difficulty lors de l'insertion dans game_rules
---------------------------------------------------

CREATE OR REPLACE FUNCTION auto_create_question_type_difficulty_on_game_rule()
    RETURNS TRIGGER AS $$
BEGIN
    -- Vérifie si l'association existe déjà
    IF NOT EXISTS (
        SELECT 1 FROM question_type_difficulty
        WHERE question_type_code = NEW.question_type_code
          AND difficulty_level_id = NEW.difficulty_level_id
    ) THEN
        -- Crée l'association
        INSERT INTO question_type_difficulty (
            difficulty_level_id,
            question_type_code
        ) VALUES (
                     NEW.difficulty_level_id,
                     NEW.question_type_code
                 );
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

------------------------------------------------------

-- Trigger pour synchroniser question_type_difficulty lors de l'insertion dans game_rules
CREATE TRIGGER trg_sync_question_type_difficulty_on_game_rule_insert
    AFTER INSERT ON game_rules
    FOR EACH ROW
EXECUTE FUNCTION auto_create_question_type_difficulty_on_game_rule();