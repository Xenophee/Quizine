

-- Insertion de données dans la table "users"
INSERT INTO users (username, email, password)
VALUES
    ('alice', 'alice@example.com', 'password123'),
    ('bob', 'bob@example.com', 'password456');


INSERT INTO themes (name)
VALUES
    ('Art'),
    ('Droit & Politique'),
    ('Histoire & Géographie'),
    ('Littérature & Langue française'),
    ('Sciences humaines'),
    ('Sciences & Techniques');

INSERT INTO categories (name, id_theme)
VALUES
    ('Cinéma', 1),
    ('Droit privé', 2),
    ('Droit public', 2),
    ('Relations internationales', 2),
    ('Économie', 2),
    ('Géographie française', 3),
    ('Expression & vocabulaire', 4),
    ('Philosophie', 5),
    ('Mythologie & religions', 5),
    ('Biologie & santé', 6);

INSERT INTO difficulty_levels (name, max_responses, timer_seconds, points_per_question)
VALUES
    ('Facile', 2, 0, 5),
    ('Intermédiaire', 4, 0, 10);