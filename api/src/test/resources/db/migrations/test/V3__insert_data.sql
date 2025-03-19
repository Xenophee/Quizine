

-- Insertion de données dans la table "users"
INSERT INTO users (username, email, password)
VALUES
    ('alice', 'alice@example.com', 'password123'),
    ('bob', 'bob@example.com', 'password456');


INSERT INTO themes (name, disabled_at)
VALUES
    ('Art', CURRENT_DATE),
    ('Droit & Politique', CURRENT_DATE),
    ('Histoire & Géographie', CURRENT_DATE),
    ('Littérature & Langue française', NULL),
    ('Sciences humaines', NULL),
    ('Sciences & Techniques', NULL);



