

---------------------------------------------------
-- Création de la fonction pour mettre à jour la date de modification
---------------------------------------------------
CREATE OR REPLACE FUNCTION update_updated_at_column()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

---------------------------------------------------
-- Création du trigger pour mettre à jour la date de modification
---------------------------------------------------
CREATE TRIGGER update_users_updated_at
    BEFORE UPDATE ON users
    FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();