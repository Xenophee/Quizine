---------------------------------------------------
-- Réinitialisation des séquences
---------------------------------------------------

DO $$
    DECLARE
        seq RECORD;
    BEGIN
        FOR seq IN
            SELECT relname AS sequence_name
            FROM pg_class
            WHERE relkind = 'S'
            LOOP
                EXECUTE format(
                        'SELECT setval(''%s'', COALESCE((SELECT MAX(id) FROM %s), 0) + 1, false)',
                        seq.sequence_name,
                        regexp_replace(seq.sequence_name, '_id_seq$', '')
                        );
            END LOOP;
    END $$;



---------------------------------------------------
-- Réactivation des triggers
---------------------------------------------------

ALTER TABLE question_types ENABLE TRIGGER trg_create_game_rule_on_question_type_insert;