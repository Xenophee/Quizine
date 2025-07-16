---------------------------------------------------
-- Création du trigger pour interdire la suppression du thème par défaut
---------------------------------------------------

CREATE OR REPLACE FUNCTION forbid_deletion_default_theme()
    RETURNS TRIGGER AS
$$
BEGIN
    IF OLD.is_default = TRUE THEN
        RAISE EXCEPTION 'Le thème par défaut ne peut pas être supprimé.';
    END IF;
    RETURN OLD;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER trg_forbid_delete_default_theme
    BEFORE DELETE
    ON themes
    FOR EACH ROW
EXECUTE FUNCTION forbid_deletion_default_theme();