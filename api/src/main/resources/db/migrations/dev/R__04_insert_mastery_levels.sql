---------------------------------------------------
-- Insertion des niveaux de maîtrise
---------------------------------------------------

INSERT INTO mastery_levels (id, name, description, rank, created_at, updated_at, disabled_at)
VALUES (1, 'ACCESSIBLE', 'Connaissances de base, accessibles à tous, sans prérequis.',
        1, CURRENT_TIMESTAMP, null, null),

       (2, 'EXPERT',
        'Connaissances techniques ou spécialisées, habituellement réservées aux professionnels du domaine.',
        3, CURRENT_TIMESTAMP, null, null),

       (3, 'AVERTI',
        'Sujets destinés aux personnes ayant une certaine familiarité ou un intérêt particulier pour le domaine.',
        2, CURRENT_TIMESTAMP, null, null)

ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    description = EXCLUDED.description,
    rank = EXCLUDED.rank,
    created_at = EXCLUDED.created_at,
    updated_at = EXCLUDED.updated_at,
    disabled_at = EXCLUDED.disabled_at;


---------------------------------------------------
-- Mise à jour de la séquence pour les niveaux de maîtrise
---------------------------------------------------
SELECT setval('mastery_levels_id_seq', (SELECT MAX(id) FROM mastery_levels));