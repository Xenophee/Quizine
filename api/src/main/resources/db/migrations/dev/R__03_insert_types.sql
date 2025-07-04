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
        CURRENT_TIMESTAMP, null, null)

ON CONFLICT (code) DO UPDATE SET
    name = EXCLUDED.name,
    description = EXCLUDED.description,
    created_at = EXCLUDED.created_at,
    updated_at = EXCLUDED.updated_at,
    disabled_at = EXCLUDED.disabled_at;


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
        CURRENT_TIMESTAMP, null)

ON CONFLICT (code) DO UPDATE SET
    name = EXCLUDED.name,
    description = EXCLUDED.description,
    instruction = EXCLUDED.instruction,
    created_at = EXCLUDED.created_at,
    updated_at = EXCLUDED.updated_at;



---------------------------------------------------
-- Insertion des relations entre quiz types et questions types
---------------------------------------------------

INSERT INTO quiz_type_questions (quiz_type_code, question_type_code)
VALUES ('CLASSIC', 'CLASSIC'),    -- Quiz Classique avec type de question Classique
       ('TRUE_FALSE', 'TRUE_FALSE'), -- Quiz Vrai/Faux avec type de question Vrai/Faux
       ('MIXTE', 'TRUE_FALSE'), -- Quiz Mixture avec tout type de question
       ('MIXTE', 'CLASSIC') -- Quiz Mixture avec tout type de question

ON CONFLICT (quiz_type_code, question_type_code) DO NOTHING;
