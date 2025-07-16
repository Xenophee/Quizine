---------------------------------------------------
-- Création du trigger pour désactiver automatiquement un thème si tous les quiz associés sont désactivés
---------------------------------------------------

CREATE OR REPLACE FUNCTION auto_disable_theme_if_no_active_quizzes()
    RETURNS TRIGGER AS
$$
BEGIN
    -- Vérifie si le thème a encore des quiz actifs
    IF NOT EXISTS (SELECT 1
                   FROM quizzes
                   WHERE theme_id = OLD.theme_id
                     AND disabled_at IS NULL) THEN
        -- Met à jour la table theme si tous les quiz sont désactivés
        UPDATE themes
        SET disabled_at = CURRENT_TIMESTAMP,
            updated_at  = CURRENT_TIMESTAMP
        WHERE id = OLD.theme_id
          AND disabled_at IS NULL;
    END IF;

    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

---------------------------------------------------

-- Exécution de la fonction après la suppression d'un quiz
CREATE TRIGGER trg_check_theme_on_quiz_removed
    AFTER DELETE
    ON quizzes
    FOR EACH ROW
EXECUTE FUNCTION auto_disable_theme_if_no_active_quizzes();


-- Exécution de la fonction après la désactivation d'un quiz
CREATE TRIGGER trg_check_theme_on_quiz_disabled
    AFTER UPDATE OF disabled_at
    ON quizzes
    FOR EACH ROW
    WHEN (OLD.disabled_at IS NULL AND NEW.disabled_at IS NOT NULL) -- Uniquement si on vient de désactiver le quiz
EXECUTE FUNCTION auto_disable_theme_if_no_active_quizzes();
