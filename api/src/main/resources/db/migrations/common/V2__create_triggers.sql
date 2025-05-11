

---------------------------------------------------
-- Création du trigger pour s'assurer qu'il n'y a qu'un seul niveau de difficulté de référence
---------------------------------------------------

CREATE FUNCTION ensure_single_reference_level()
    RETURNS TRIGGER AS $$
BEGIN
    IF NEW.is_reference THEN
        IF EXISTS (
            SELECT 1 FROM difficulty_levels WHERE is_reference = true AND id <> NEW.id
        ) THEN
            RAISE EXCEPTION 'Il ne peut y avoir qu''un seul niveau de difficulté comme référence.';
        END IF;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_single_reference_level
    BEFORE INSERT OR UPDATE ON difficulty_levels
    FOR EACH ROW
EXECUTE FUNCTION ensure_single_reference_level();
