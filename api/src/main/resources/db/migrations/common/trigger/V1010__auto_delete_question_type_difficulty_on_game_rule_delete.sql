---------------------------------------------------
-- Création du trigger pour nettoyer la table question_type_difficulty en cas de besoin lors de la suppression d'une game_rule
---------------------------------------------------


CREATE OR REPLACE FUNCTION auto_delete_question_type_difficulty_on_game_rule_delete()
    RETURNS TRIGGER AS
$$
BEGIN
    -- Vérifie s'il reste une autre game_rule avec le même couple (question_type, difficulty_level)
    IF NOT EXISTS (SELECT 1
                   FROM game_rules
                   WHERE question_type_code = OLD.question_type_code
                         AND difficulty_level_id = OLD.difficulty_level_id
                         AND id <> OLD.id) THEN
        -- Supprime l'association si elle n'est plus utilisée
        DELETE
        FROM question_type_difficulty
        WHERE question_type_code = OLD.question_type_code
            AND difficulty_level_id = OLD.difficulty_level_id;
    END IF;

    RETURN OLD;
END;
$$ LANGUAGE plpgsql;


--------------------------------------------------------
-- Activation lors de la suppression d'une game_rule
CREATE TRIGGER trg_cleanup_question_type_difficulty_on_game_rule_delete
    AFTER DELETE
    ON game_rules
    FOR EACH ROW
EXECUTE FUNCTION auto_delete_question_type_difficulty_on_game_rule_delete();
