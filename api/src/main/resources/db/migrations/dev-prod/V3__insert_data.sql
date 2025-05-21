


INSERT INTO users (username, email, password)
VALUES
    ('alice', 'alice@example.com', 'password123'),
    ('bob', 'bob@example.com', 'password456');



---------------------------------------------------
-- Insertion des thèmes
---------------------------------------------------

INSERT INTO themes (name)
VALUES
    ('Art'),
    ('Droit & Politique'),
    ('Histoire & Géographie'),
    ('Littérature'),
    ('Sciences humaines'),
    ('Sciences & Techniques');


---------------------------------------------------
-- Insertion des catégories
---------------------------------------------------

INSERT INTO categories (name, id_theme)
VALUES
    ('Cinéma', 1),
    ('Droit privé', 2),
    ('Droit public', 2),
    ('Relations internationales', 2),
    ('Économie', 2),
    ('Géographie française', 3),
    ('Courant littéraire', 4),
    ('Philosophie', 5),
    ('Mythologie & religions', 5),
    ('Biologie & santé', 6),
    ('Période littéraire', 4);


---------------------------------------------------
-- Insertion des niveaux de difficulté
---------------------------------------------------

INSERT INTO difficulty_levels (name, max_answers, timer_seconds, points_per_question, is_reference, display_order, disabled_at)
VALUES
    ('Facile', 2, 0, 5, false, 1, null),
    ('Intermédiaire', 4, 0, 10, true, 2, null),
    ('Difficile', 0, 0, 15, false, 3, CURRENT_TIMESTAMP),
    ('Expert', 0, 30, 20, false, 4, CURRENT_TIMESTAMP);
