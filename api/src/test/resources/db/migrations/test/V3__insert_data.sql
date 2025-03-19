

-- Insertion de données dans la table "users"
INSERT INTO users (username, email, password)
VALUES
    ('alice', 'alice@example.com', 'password123'),
    ('bob', 'bob@example.com', 'password456');


INSERT INTO themes (name, disabled_at)
VALUES
    ('Art', CURRENT_TIMESTAMP),
    ('Droit & Politique', CURRENT_TIMESTAMP),
    ('Histoire & Géographie', CURRENT_TIMESTAMP),
    ('Littérature & Langue française', NULL),
    ('Sciences humaines', NULL),
    ('Sciences & Techniques', NULL);



