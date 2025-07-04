---------------------------------------------------
-- Insertion des niveaux de difficulté
---------------------------------------------------

INSERT INTO difficulty_levels (id, name, is_reference, label, description, rank, created_at, updated_at, disabled_at, starts_at, ends_at, is_recurring)
VALUES (4, 'Réchauffe-méninges', false, 'FACILE',
        'Un petit tour de chauffe pour activer tes neurones en douceur.', 1
           , CURRENT_TIMESTAMP - INTERVAL '2 months', null, null,
        null, null, false),

       (1, 'Mi-cuisson cognitive', true, 'MOYEN',
        'Tes neurones commencent à frémir, la réflexion s’intensifie.', 2,
        CURRENT_TIMESTAMP - INTERVAL '2 months', null, null,
        null, null, false),

       (3, 'Saisie neuronale', false, 'DIFFICILE',
        'Ça crépite ! La matière grise est mise à rude épreuve.', 3,
        CURRENT_TIMESTAMP - INTERVAL '2 months', null, null,
        null, null, false),

       (2, 'Haute quizine mentale', false, 'EXPERT',
        'Pour les grands chefs de la pensée. Maîtrise et précision !', 4,
        CURRENT_TIMESTAMP - INTERVAL '2 months', null, null,
        null, null, false),

       (5, 'Quizine troll', false, 'SPÉCIAL',
        'Pour les véritables maîtres du quiz. Un défi troll pour vous faire uniquement perdre des points en cas d''échec !', null,
        CURRENT_TIMESTAMP, null, null,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '1 month', false),

       (6, 'Quizine pour les nuls', false, 'SPÉCIAL',
        'Parce que même lorsque vous êtes mauvais, on a pitié de vous...', null,
        CURRENT_TIMESTAMP, null, CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '1 month', false),

         (7, 'Quizine pour les boss', false, 'SPÉCIAL',
        'Parce que vous êtes tellement bon, que vous méritez bien plus de points...', null,
        CURRENT_TIMESTAMP, null, null,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '5 months', false); -- Ne possède pas de game rule associée