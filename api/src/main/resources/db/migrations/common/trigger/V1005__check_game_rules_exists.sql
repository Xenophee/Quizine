---------------------------------------------------
-- Création du trigger pour vérifier l'existence d'une règle de jeu avant d'insérer une relation entre type de question et niveau de difficulté
---------------------------------------------------

CREATE OR REPLACE FUNCTION check_game_rule_exists()
    RETURNS trigger AS
$$
BEGIN
    IF NOT EXISTS (SELECT 1
                   FROM game_rules
                   WHERE difficulty_level_id = NEW.difficulty_level_id
                     AND question_type_code = NEW.question_type_code) THEN
        RAISE EXCEPTION 'Impossible d''insérer la relation entre le type de question et niveau de difficulté : aucune règle de jeu ne correspond à cette combinaison.';
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;




CREATE TRIGGER trg_check_game_rule_exists
    BEFORE INSERT
    ON question_type_difficulty
    FOR EACH ROW
EXECUTE FUNCTION check_game_rule_exists();