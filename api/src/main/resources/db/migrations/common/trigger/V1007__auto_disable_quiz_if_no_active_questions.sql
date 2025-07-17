---------------------------------------------------
-- Création du trigger pour désactiver automatiquement un quiz si toutes les questions sont désactivées
---------------------------------------------------
CREATE OR REPLACE FUNCTION auto_disable_quiz_if_too_few_active_questions_on_link_delete()
    RETURNS TRIGGER AS
$$
BEGIN
    -- Vérifie combien il reste de questions actives dans le quiz
    IF (SELECT COUNT(*)
        FROM quiz_questions qq
                 JOIN questions q ON qq.question_id = q.id
        WHERE qq.quiz_id = OLD.quiz_id
          AND q.disabled_at IS NULL) <
       (SELECT value::INTEGER FROM app_settings WHERE key = 'MIN_ACTIVE_QUESTIONS_PER_QUIZ') THEN
        -- Désactive le quiz
        UPDATE quizzes
        SET disabled_at = CURRENT_TIMESTAMP,
            updated_at  = CURRENT_TIMESTAMP
        WHERE id = OLD.quiz_id
          AND disabled_at IS NULL;
    END IF;

    RETURN NULL;
END;
$$ LANGUAGE plpgsql;


---------------------------------------------------
CREATE TRIGGER trg_auto_disable_quiz_on_link_delete
    AFTER DELETE
    ON quiz_questions
    FOR EACH ROW
EXECUTE FUNCTION auto_disable_quiz_if_too_few_active_questions_on_link_delete();

