---------------------------------------------------
-- Insertion des niveaux de difficulté
---------------------------------------------------

INSERT INTO difficulty_levels (id, name, is_reference, label, description, rank)
VALUES (4, 'Réchauffe-méninges', false, 'FACILE',
        'Un petit tour de chauffe pour activer tes neurones en douceur.', 1),

       (1, 'Mi-cuisson cognitive', true, 'MOYEN',
        'Tes neurones commencent à frémir, la réflexion s’intensifie.', 2),

       (3, 'Saisie neuronale', false, 'DIFFICILE',
        'Ça crépite ! La matière grise est mise à rude épreuve.', 3),

       (2, 'Haute quizine mentale', false, 'EXPERT',
        'Pour les grands chefs de la pensée. Maîtrise et précision !', 4)

ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    is_reference = EXCLUDED.is_reference,
    label = EXCLUDED.label,
    description = EXCLUDED.description,
    rank = EXCLUDED.rank;



---------------------------------------------------
-- Mise à jour de la séquence pour les niveaux de difficulté
---------------------------------------------------
SELECT setval('difficulty_levels_id_seq', (SELECT MAX(id) FROM difficulty_levels));