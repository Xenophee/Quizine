
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