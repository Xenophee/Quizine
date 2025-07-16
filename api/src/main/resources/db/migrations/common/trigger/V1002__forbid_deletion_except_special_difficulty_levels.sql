---------------------------------------------------
-- Création du trigger pour interdire la suppression des niveaux de difficulté sauf pour le niveau 'SPÉCIAL'
------------------------------------------------

CREATE OR REPLACE FUNCTION forbid_deletion_except_special_difficulty_levels()
    RETURNS TRIGGER AS
$$
BEGIN
    IF OLD.label <> 'SPÉCIAL' THEN
        RAISE EXCEPTION 'Seuls les niveaux de difficulté avec le label ''SPÉCIAL'' peuvent être supprimés.';
    END IF;
    RETURN OLD;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER trg_forbid_delete_difficulty
    BEFORE DELETE
    ON difficulty_levels
    FOR EACH ROW
EXECUTE FUNCTION forbid_deletion_except_special_difficulty_levels();