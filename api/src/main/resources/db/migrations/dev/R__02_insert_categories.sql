---------------------------------------------------
-- Insertion des catégories
---------------------------------------------------

INSERT INTO categories (id, name, description, theme_id, created_at, updated_at, disabled_at)
VALUES (1, 'Droit privé', 'Étude des relations juridiques entre individus et entités privées.',
        3,
        CURRENT_TIMESTAMP, null, CURRENT_TIMESTAMP),

       (2, 'Cinéma', 'Analyse des œuvres cinématographiques et de leur impact culturel.',
        2,
        CURRENT_TIMESTAMP, null, null),

       (3, 'Relations internationales', 'Examen des interactions entre États et organisations internationales.',
        3,
        CURRENT_TIMESTAMP, null, CURRENT_TIMESTAMP),

       (4, 'Droit public', 'Étude des règles régissant les relations entre les citoyens et l''État.',
        3,
        CURRENT_TIMESTAMP, null, CURRENT_TIMESTAMP),

       (5, 'Géographie française', 'Exploration des caractéristiques géographiques de la France.',
        1,
        CURRENT_TIMESTAMP, null, null),

       (6, 'Courant littéraire', 'Analyse des mouvements littéraires et de leurs œuvres emblématiques.',
        5,
        CURRENT_TIMESTAMP, null, null),

       (7, 'Philosophie', 'Réflexion sur les grandes questions existentielles et éthiques.',
        4,
        CURRENT_TIMESTAMP, null, null),

       (8, 'Mythologie & religions', 'Étude des croyances, mythes et pratiques religieuses à travers le monde.',
        4,
        CURRENT_TIMESTAMP, null, null),

       (9, 'Biologie & santé', 'Analyse des systèmes biologiques et des avancées médicales.',
        6,
        CURRENT_TIMESTAMP, null, CURRENT_TIMESTAMP),

       (10, 'Période littéraire', 'Exploration des époques littéraires et de leurs caractéristiques.',
        5,
        CURRENT_TIMESTAMP, null, null),

       (11, 'Économie', 'Étude des systèmes économiques et des interactions financières.',
        3,
        CURRENT_TIMESTAMP, null, CURRENT_TIMESTAMP)

ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    description = EXCLUDED.description,
    theme_id = EXCLUDED.theme_id,
    created_at = EXCLUDED.created_at,
    updated_at = EXCLUDED.updated_at,
    disabled_at = EXCLUDED.disabled_at;



---------------------------------------------------
-- Mise à jour de la séquence pour les catégories
---------------------------------------------------
SELECT setval('categories_id_seq', (SELECT MAX(id) FROM categories));