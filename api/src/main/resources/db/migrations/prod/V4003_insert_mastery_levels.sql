---------------------------------------------------
-- Insertion des niveaux de maîtrise
---------------------------------------------------

INSERT INTO mastery_levels (id, name, description, rank)
VALUES (1, 'ACCESSIBLE', 'Connaissances de base, accessibles à tous, sans prérequis.',
        1),

       (2, 'EXPERT',
        'Connaissances techniques ou spécialisées, habituellement réservées aux professionnels du domaine.',
        3),

       (3, 'AVERTI',
        'Sujets destinés aux personnes ayant une certaine familiarité ou un intérêt particulier pour le domaine.',
        2);