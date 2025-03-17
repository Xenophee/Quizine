

-- Insertion de données dans la table "users"
INSERT INTO users (username, email, password)
VALUES
    ('alice', 'alice@example.com', 'password123'),
    ('bob', 'bob@example.com', 'password456');


INSERT INTO themes (name, published_at, disabled_at)
VALUES
    ('Art', CURRENT_TIMESTAMP, NULL),
    ('Droit & Politique', CURRENT_TIMESTAMP, (CURRENT_TIMESTAMP + INTERVAL '2 hours')),
    ('Histoire & Géographie', CURRENT_TIMESTAMP, NULL),
    ('Littérature & Langue française', NULL, NULL),
    ('Sciences humaines', NULL, NULL),
    ('Sciences & Techniques', NULL, NULL);



