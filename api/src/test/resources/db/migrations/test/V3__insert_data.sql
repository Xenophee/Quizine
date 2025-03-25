

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

INSERT INTO categories (name, disabled_at, id_theme)
VALUES
    ('Peinture', CURRENT_DATE, 1),
    ('Cinéma', NULL, 1),
    ('Droit civil', CURRENT_DATE, 2),
    ('Droit constitutionnel', NULL, 2),
    ('Relations internationales', CURRENT_DATE, 2),
    ('Histoire médiévale', CURRENT_DATE, 3),
    ('Géographie française', NULL, 3),
    ('Littérature du XIXe', NULL, 4),
    ('Philosophie', NULL, 5),
    ('Sociologie', NULL, 5),
    ('Informatique', NULL, 6),
    ('Biologie', CURRENT_DATE, 6);

INSERT INTO difficulty_levels (name, max_responses, timer_seconds, points_per_question, disabled_at)
VALUES
    ('Facile', 2, 0, 5, null),
    ('Intermédiaire', 4, 0, 10, null),
    ('Difficile', 6, 0, 15, CURRENT_DATE),
    ('Expert', 0, 15, 20, CURRENT_DATE);


