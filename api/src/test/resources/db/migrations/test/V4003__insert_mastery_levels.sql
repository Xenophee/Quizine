---------------------------------------------------
-- Insertion des niveaux de maîtrise
---------------------------------------------------

INSERT INTO mastery_levels (id, name, description, rank, created_at, updated_at, disabled_at)
VALUES (1, 'ACCESSIBLE', 'Connaissances de base, accessibles à tous, sans prérequis.',
        1, CURRENT_TIMESTAMP, null, null),

       (2, 'EXPERT',
        'Connaissances techniques ou spécialisées, habituellement réservées aux professionnels du domaine.',
        3, CURRENT_TIMESTAMP, null, CURRENT_TIMESTAMP),

       (3, 'AVERTI',
        'Sujets destinés aux personnes ayant une certaine familiarité ou un intérêt particulier pour le domaine.',
        2, CURRENT_TIMESTAMP, null, null);