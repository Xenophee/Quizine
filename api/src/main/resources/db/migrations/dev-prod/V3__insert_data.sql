INSERT INTO users (username, email, password)
VALUES ('alice', 'alice@example.com', 'password123'),
       ('bob', 'bob@example.com', 'password456');



---------------------------------------------------
-- Insertion des thèmes
---------------------------------------------------

INSERT INTO themes (id, name, description, is_default, created_at, updated_at, disabled_at)
VALUES (1, 'Histoire & Géographie', 'Étude des événements passés et de la géographie mondiale.',
        false, CURRENT_TIMESTAMP, null, CURRENT_TIMESTAMP),

       (2, 'Art', 'Exploration des différentes formes d’art, de la peinture à la musique.',
        false, CURRENT_TIMESTAMP, null, null),

       (3, 'Droit & Politique', 'Analyse des systèmes juridiques et politiques à travers le monde.',
        false, CURRENT_TIMESTAMP, null, CURRENT_TIMESTAMP),

       (4, 'Sciences humaines', 'Étude des comportements humains de la culture et de la société.',
        false, CURRENT_TIMESTAMP, null, null),

       (5, 'Littérature', 'Étude des œuvres littéraires et des mouvements littéraires.',
        false, CURRENT_TIMESTAMP, null, null),

       (6, 'Sciences & Techniques', 'Découverte des avancées scientifiques et technologiques.',
        false, CURRENT_TIMESTAMP, null, CURRENT_TIMESTAMP),

       (7, 'Informatique', 'Exploration des concepts et technologies informatiques.',
        false, CURRENT_TIMESTAMP, null, null),

       (8, 'Divers', 'Thèmes variés et éclectiques.',
        true, CURRENT_TIMESTAMP, null, null);


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
        CURRENT_TIMESTAMP, null, CURRENT_TIMESTAMP);


---------------------------------------------------
-- Insertion des quiz types
---------------------------------------------------

INSERT INTO quiz_types (code, name, description, created_at, updated_at, disabled_at)
VALUES ('CLASSIC', 'Classique', 'Un quiz où vous devez déterminer la ou les bonnes réponses !',
        CURRENT_TIMESTAMP, null, null),

       ('TRUE_FALSE', 'Vrai/Faux',
        'Un quiz rapide où vous devez trancher par “Vrai” ou “Faux” sur chaque affirmation.',
        CURRENT_TIMESTAMP, null, null),

       ('MIXTE', 'Mixture',
        'Un mélange de styles ! Ce type de quiz combine différentes formes de questions pour un défi plus complet.',
        CURRENT_TIMESTAMP, null, null);


---------------------------------------------------
-- Insertion des questions types
---------------------------------------------------

INSERT INTO question_types (code, name, description, instruction, created_at, updated_at)
VALUES ('CLASSIC', 'Classique',
        'Le type Classique permet de proposer plusieurs réponses dont une ou plusieurs peuvent être correctes.',
        'Déterminez la bonne réponse.',
        CURRENT_TIMESTAMP, null),

       ('TRUE_FALSE', 'Vrai/Faux',
        'Le type Vrai/Faux permet de proposer une affirmation à valider ou invalider.',
        'Déterminez si cette affirmation est vrai ou fausse.',
        CURRENT_TIMESTAMP, null);



---------------------------------------------------
-- Insertion des relations entre quiz types et questions types
---------------------------------------------------

INSERT INTO quiz_type_questions (quiz_type_code, question_type_code)
VALUES ('CLASSIC', 'CLASSIC'),    -- Quiz Classique avec type de question Classique
       ('TRUE_FALSE', 'TRUE_FALSE'), -- Quiz Vrai/Faux avec type de question Vrai/Faux
       ('MIXTE', 'TRUE_FALSE'), -- Quiz Mixture avec tout type de question
       ('MIXTE', 'CLASSIC');
-- Quiz Mixture avec tout type de question


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
        2, CURRENT_TIMESTAMP, null, null);


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
        'Pour les grands chefs de la pensée. Maîtrise et précision !', 4),

       (5, 'Quizine ultime', false, 'SPÉCIAL',
        'Pour les véritables maîtres du quiz. Un défi ultime pour les esprits les plus affûtés.', null);


---------------------------------------------------
-- Insertion des règles de jeu
---------------------------------------------------

INSERT INTO game_rules (id, is_reference, answer_options_count, points_per_good_answer, points_penalty_per_wrong_answer,
                        timer_seconds, points_timer_multiplier, points_penalty_multiplier,
                        combo_2_bonus, combo_3_bonus, combo_4_bonus, combo_5_bonus,
                        priority, question_type_code, difficulty_level_id)
VALUES (1, false, 2, 5, 2, 30,
        1.2, 1.1, 1, 2, 3, 4,
        1, 'CLASSIC', 4),

       (4, true, 4, 8, 4, 45,
        1.5, 1.3, 2, 3, 4, 5,
        1, 'CLASSIC', 1),

       (3, false, 0, 12, 6, 60,
        1.8, 1.6, 3, 5, 7, 9,
        1, 'CLASSIC', 3),

       (2, true, 0, 6, 3, 20,
        1.4, 1.2, 2, 3, 4, 5,
        1, 'TRUE_FALSE', 1),

       (5, true, 0, 10, 5, 30,
        1.5, 1.3, 2, 3, 4, 5,
        1, 'TRUE_FALSE', 5);


---------------------------------------------------
-- Insertion des relations entre question types et niveaux de difficulté
---------------------------------------------------

INSERT INTO question_type_difficulty (question_type_code, difficulty_level_id)
VALUES ('CLASSIC', 1), -- Type de question Classique avec niveau de difficulté Moyen
       ('CLASSIC', 3), -- Type de question Classique avec niveau de difficulté Difficile
       ('CLASSIC', 4), -- Type de question Classique avec niveau de difficulté Facile
       ('TRUE_FALSE', 1),
       ('TRUE_FALSE', 5);
-- Type de question Vrai/Faux avec niveau de difficulté Moyen