

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


---------------------------------------------------
-- Création du trigger pour s'assurer que la catégorie correspond au thème lors de l'insertion ou de la mise à jour d'un quiz
---------------------------------------------------
CREATE OR REPLACE FUNCTION validate_category_theme()
    RETURNS TRIGGER AS $$
BEGIN
    IF NEW.id_category IS NOT NULL THEN
        IF NOT EXISTS (
            SELECT 1
            FROM categories
            WHERE id = NEW.id_category AND id_theme = NEW.id_theme
        ) THEN
            RAISE EXCEPTION 'La catégorie (id_category) ne correspond pas au thème (id_theme)';
        END IF;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER check_category_theme
    BEFORE INSERT OR UPDATE ON quizzes
    FOR EACH ROW
EXECUTE FUNCTION validate_category_theme();