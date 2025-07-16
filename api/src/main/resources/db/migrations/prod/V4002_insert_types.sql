---------------------------------------------------
-- Insertion des quiz types
---------------------------------------------------

INSERT INTO quiz_types (code, name, description)
VALUES ('CLASSIC', 'Classique', 'Un quiz où vous devez déterminer la ou les bonnes réponses !'),

       ('TRUE_FALSE', 'Vrai/Faux',
        'Un quiz rapide où vous devez trancher par “Vrai” ou “Faux” sur chaque affirmation.'),

       ('MIXTE', 'Mixture',
        'Un mélange de styles ! Ce type de quiz combine différentes formes de questions pour un défi plus complet.');


---------------------------------------------------
-- Insertion des questions types
---------------------------------------------------

INSERT INTO question_types (code, name, description, instruction)
VALUES ('CLASSIC', 'Classique',
        'Le type Classique permet de proposer plusieurs réponses dont une ou plusieurs peuvent être correctes.',
        'Déterminez la bonne réponse.'),

       ('TRUE_FALSE', 'Vrai/Faux',
        'Le type Vrai/Faux permet de proposer une affirmation à valider ou invalider.',
        'Déterminez si cette affirmation est vrai ou fausse.');



---------------------------------------------------
-- Insertion des relations entre quiz types et questions types
---------------------------------------------------

INSERT INTO quiz_type_questions (quiz_type_code, question_type_code)
VALUES ('CLASSIC', 'CLASSIC'),    -- Quiz Classique avec type de question Classique
       ('TRUE_FALSE', 'TRUE_FALSE'), -- Quiz Vrai/Faux avec type de question Vrai/Faux
       ('MIXTE', 'TRUE_FALSE'), -- Quiz Mixture avec tout type de question
       ('MIXTE', 'CLASSIC'); -- Quiz Mixture avec tout type de question
