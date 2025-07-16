---------------------------------------------------
-- Désactivation des triggers pour éviter les problèmes avec la data seed
---------------------------------------------------

ALTER TABLE question_types DISABLE TRIGGER trg_create_game_rule_on_question_type_insert;