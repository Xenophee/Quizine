---------------------------------------------------
-- Création du trigger pour interdire la désactivation des règles de jeu de référence
---------------------------------------------------

CREATE OR REPLACE FUNCTION forbid_disabling_reference_rules()
    RETURNS TRIGGER AS
$$
DECLARE
    ref_difficulty_exists BOOLEAN;
BEGIN
    IF NEW.disabled_at IS NOT NULL THEN
        -- Vérifie si la game_rule est liée à une difficulté de référence
        SELECT EXISTS (SELECT 1
                       FROM difficulty_levels
                            JOIN game_rules ON game_rules.difficulty_level_id = difficulty_levels.id
                       WHERE game_rules.id = NEW.id
                            AND difficulty_levels.is_reference = true)
        INTO ref_difficulty_exists;

        IF ref_difficulty_exists THEN
            RAISE EXCEPTION 'Impossible de désactiver une règle de jeu liée à une difficulté de référence.';
        END IF;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;



-- Trigger pour interdire la désactivation des règles de jeu de référence
CREATE TRIGGER trg_forbid_disable_reference_rule
    BEFORE UPDATE OF disabled_at
    ON game_rules
    FOR EACH ROW
EXECUTE FUNCTION forbid_disabling_reference_rules();
