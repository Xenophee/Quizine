---------------------------------------------------
-- Création du trigger pour interdire la suppression des règles de jeu de référence
---------------------------------------------------

CREATE OR REPLACE FUNCTION forbid_deletion_on_reference_rules()
    RETURNS TRIGGER AS
$$
DECLARE
    ref_difficulty_exists BOOLEAN;
BEGIN
    -- Vérifie si la difficulté associée est une difficulté de référence
    SELECT EXISTS (
        SELECT 1
        FROM difficulty_levels
        WHERE difficulty_levels.id = OLD.difficulty_level_id
            AND difficulty_levels.is_reference = TRUE
    ) INTO ref_difficulty_exists;

    IF ref_difficulty_exists THEN
        RAISE EXCEPTION 'Les règles de jeu liées à une difficulté de référence ne peuvent pas être supprimées.';
    END IF;

    RETURN OLD;
END;
$$ LANGUAGE plpgsql;



CREATE TRIGGER trg_forbid_delete_reference_rule
    BEFORE DELETE
    ON game_rules
    FOR EACH ROW
EXECUTE FUNCTION forbid_deletion_on_reference_rules();
