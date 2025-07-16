---------------------------------------------------
-- Insertion des thèmes
---------------------------------------------------

INSERT INTO themes (id, name, description, is_default, created_at, disabled_at)
VALUES (1, 'Histoire & Géographie', 'Étude des événements passés et de la géographie mondiale.',
        false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

       (2, 'Art', 'Exploration des différentes formes d’art, de la peinture à la musique.',
        false, CURRENT_TIMESTAMP, null),

       (3, 'Droit & Politique', 'Analyse des systèmes juridiques et politiques à travers le monde.',
        false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

       (4, 'Sciences humaines', 'Étude des comportements humains de la culture et de la société.',
        false, CURRENT_TIMESTAMP, null),

       (5, 'Littérature', 'Étude des œuvres littéraires et des mouvements littéraires.',
        false, CURRENT_TIMESTAMP, null),

       (6, 'Sciences & Techniques', 'Découverte des avancées scientifiques et technologiques.',
        false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

       (7, 'Informatique', 'Exploration des concepts et technologies informatiques.',
        false, CURRENT_TIMESTAMP, null),

       (8, 'Divers', 'Thèmes variés et éclectiques.',
        true, CURRENT_TIMESTAMP, null);