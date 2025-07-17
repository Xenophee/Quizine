---------------------------------------------------
-- Création du trigger pour forcer le code des types en majuscules
---------------------------------------------------

CREATE OR REPLACE FUNCTION enforce_uppercase_code()
    RETURNS TRIGGER AS
$$
BEGIN
    NEW.code := UPPER(NEW.code);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


--------------------------------------------------------

CREATE TRIGGER trg_uppercase_quiz_type_code
    BEFORE INSERT
    ON quiz_types
    FOR EACH ROW
EXECUTE FUNCTION enforce_uppercase_code();

--------------------------------------------------------

CREATE TRIGGER trg_uppercase_question_type_code
    BEFORE INSERT
    ON question_types
    FOR EACH ROW
EXECUTE FUNCTION enforce_uppercase_code();