


---------------------------------------------------
-- Création du trigger pour s'assurer que la catégorie correspond au thème lors de l'insertion ou de la mise à jour d'un quiz
---------------------------------------------------
CREATE OR REPLACE FUNCTION validate_category_theme()
    RETURNS TRIGGER AS $$
BEGIN
    IF NEW.category_id IS NOT NULL THEN
        IF NOT EXISTS (
            SELECT 1
            FROM categories
            WHERE id = NEW.category_id AND theme_id = NEW.theme_id
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



---------------------------------------------------
-- Création du trigger pour interdire la suppression des niveaux de difficulté sauf pour le niveau 'SPÉCIAL'
------------------------------------------------

CREATE OR REPLACE FUNCTION forbid_deletion_except_special_difficulty_levels()
    RETURNS TRIGGER AS $$
BEGIN
    IF OLD.label <> 'SPÉCIAL' THEN
        RAISE EXCEPTION 'Seuls les niveaux de difficulté avec le label ''SPÉCIAL'' peuvent être supprimés.';
    END IF;
    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_forbid_delete_difficulty
    BEFORE DELETE ON difficulty_levels
    FOR EACH ROW
EXECUTE FUNCTION forbid_deletion_except_special_difficulty_levels();



---------------------------------------------------
-- Création du trigger pour interdire la suppression des règles de jeu de référence
---------------------------------------------------

CREATE OR REPLACE FUNCTION forbid_deletion_on_reference_rules()
    RETURNS TRIGGER AS $$
BEGIN
    IF OLD.is_reference = true THEN
        RAISE EXCEPTION 'Les règles de jeu de référence ne peuvent pas être supprimées.';
    END IF;
    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_forbid_delete_reference_rule
    BEFORE DELETE ON game_rules
    FOR EACH ROW
EXECUTE FUNCTION forbid_deletion_on_reference_rules();


---------------------------------------------------
-- Création du trigger pour interdire la désactivation des règles de jeu de référence
---------------------------------------------------

CREATE OR REPLACE FUNCTION forbid_disabling_reference_rules()
    RETURNS TRIGGER AS $$
BEGIN
    IF NEW.is_reference = true AND NEW.disabled_at IS NOT NULL THEN
        RAISE EXCEPTION 'Impossible de désactiver une règle de jeu de référence.';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_forbid_disable_reference_rule
    BEFORE INSERT OR UPDATE ON game_rules
    FOR EACH ROW
EXECUTE FUNCTION forbid_disabling_reference_rules();


---------------------------------------------------
-- Création du trigger pour vérifier l'existence d'une règle de jeu avant d'insérer une relation entre type de question et niveau de difficulté
---------------------------------------------------

CREATE OR REPLACE FUNCTION check_game_rule_exists()
    RETURNS trigger AS $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM game_rules
        WHERE difficulty_level_id = NEW.difficulty_level_id
          AND question_type_code = NEW.question_type_code
          AND disabled_at IS NULL
    ) THEN
        RAISE EXCEPTION 'Impossible d''insérer la relation entre le type de question et niveau de difficulté : aucune règle de jeu active ne correspond à cette combinaison.';
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_check_game_rule_exists
    BEFORE INSERT ON question_type_difficulty
    FOR EACH ROW
EXECUTE FUNCTION check_game_rule_exists();
