---------------------------------------------------
-- Création du trigger pour s'assurer que la catégorie correspond au thème lors de l'insertion ou de la mise à jour d'un quiz
---------------------------------------------------
CREATE OR REPLACE FUNCTION check_category_theme()
    RETURNS TRIGGER AS
$$
BEGIN
    IF NEW.category_id IS NOT NULL THEN
        IF NOT EXISTS (SELECT 1
                       FROM categories
                       WHERE id = NEW.category_id
                         AND theme_id = NEW.theme_id) THEN
            RAISE EXCEPTION 'La catégorie (%) ne correspond pas au thème (%)', NEW.category_id, NEW.theme_id;
        END IF;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER check_category_theme
    BEFORE INSERT OR UPDATE
    ON quizzes
    FOR EACH ROW
EXECUTE FUNCTION check_category_theme();