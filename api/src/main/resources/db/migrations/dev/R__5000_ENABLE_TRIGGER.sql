---------------------------------------------------
-- Réactivation des triggers
---------------------------------------------------

ALTER TABLE question_types ENABLE TRIGGER trg_create_game_rule_on_question_type_insert;