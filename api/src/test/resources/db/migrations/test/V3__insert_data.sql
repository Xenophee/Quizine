

-- Insertion de données dans la table "users"
INSERT INTO users (username, email, password)
VALUES
    ('alice', 'alice@example.com', 'password123'),
    ('bob', 'bob@example.com', 'password456');


INSERT INTO themes (name, disabled_at)
VALUES
    ('Sciences humaines', NULL),
    ('Droit & Politique', CURRENT_TIMESTAMP),
    ('Art', CURRENT_TIMESTAMP),
    ('Littérature', NULL),
    ('Histoire & Géographie', CURRENT_TIMESTAMP),
    ('Sciences & Techniques', NULL);

INSERT INTO categories (name, disabled_at, id_theme)
VALUES
    ('Peinture', CURRENT_TIMESTAMP, 3),
    ('Cinéma', NULL, 3),
    ('Droit civil', CURRENT_TIMESTAMP, 2),
    ('Droit constitutionnel', NULL, 2),
    ('Relations internationales', CURRENT_TIMESTAMP, 2),
    ('Histoire médiévale', CURRENT_TIMESTAMP, 5),
    ('Géographie française', NULL, 5),
    ('Courant littéraire', NULL, 4),
    ('Philosophie', NULL, 1),
    ('Sociologie', NULL, 1),
    ('Informatique', NULL, 6),
    ('Biologie', CURRENT_TIMESTAMP, 6),
    ('Période littéraire', NULL, 4),
    ('Mythologie & Religion', NULL, 1);

INSERT INTO difficulty_levels (name, max_answers, timer_seconds, points_per_question, is_reference, display_order, disabled_at, created_at)
VALUES
    ('Expert', 0, 30, 20, false, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP - INTERVAL '3 MONTH'),
    ('Intermédiaire', 4, 0, 10, true, 2, null, CURRENT_TIMESTAMP - INTERVAL '3 MONTH'),
    ('Facile', 2, 0, 5, false, 1, null, CURRENT_TIMESTAMP),
    ('Difficile', 0, 0, 15, false, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Hardcore', 0, 5, 2, false, 5, null, CURRENT_TIMESTAMP);


