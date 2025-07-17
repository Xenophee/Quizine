---------------------------------------------------
-- Création du trigger pour désactiver automatiquement un quiz si le nombre de questions actives est inférieur au minimum requis
---------------------------------------------------

CREATE OR REPLACE FUNCTION auto_disable_quiz_if_too_few_active_questions_on_update()
    RETURNS TRIGGER AS
$$
DECLARE
    quiz_id_to_check INTEGER;
BEGIN
    -- Si on vient de désactiver une question
    IF OLD.disabled_at IS NULL AND NEW.disabled_at IS NOT NULL THEN
        FOR quiz_id_to_check IN
            SELECT quiz_id
            FROM quiz_questions
            WHERE question_id = OLD.id
            LOOP
                -- Si ce quiz a désormais moins du minimum requis en questions actives
                IF (
                       SELECT COUNT(*)
                       FROM quiz_questions qq
                                JOIN questions q ON qq.question_id = q.id
                       WHERE qq.quiz_id = quiz_id_to_check
                         AND q.disabled_at IS NULL
                   ) < (SELECT value::INTEGER FROM app_settings WHERE key = 'MIN_ACTIVE_QUESTIONS_PER_QUIZ') THEN
                    UPDATE quizzes
                    SET disabled_at = CURRENT_TIMESTAMP,
                        updated_at  = CURRENT_TIMESTAMP
                    WHERE id = quiz_id_to_check
                      AND disabled_at IS NULL;
                END IF;
            END LOOP;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

--------------------------------------------------------
-- Activation du trigger après la mise à jour de la visibilité d'une question
CREATE TRIGGER trg_auto_disable_quiz_on_question_disable
    AFTER UPDATE ON questions
    FOR EACH ROW
    WHEN (OLD.disabled_at IS DISTINCT FROM NEW.disabled_at)
EXECUTE FUNCTION auto_disable_quiz_if_too_few_active_questions_on_update();
