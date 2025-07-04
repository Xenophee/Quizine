-- Insertion du quiz
INSERT INTO quizzes (id, title, description, category_id, theme_id, quiz_type_code, mastery_level_id, created_at,
                     updated_at, disabled_at)
VALUES (4, 'Littérature gothique : symboles, héritages et curiosités',
        'Explorez les mystères de la littérature gothique à travers ses symboles, ses héritages et ses curiosités. Un voyage dans l’obscurité et la beauté du genre.',
        6, 5, 'CLASSIC', 3,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)

ON CONFLICT (id) DO UPDATE SET title            = EXCLUDED.title,
                               description      = EXCLUDED.description,
                               category_id      = EXCLUDED.category_id,
                               theme_id         = EXCLUDED.theme_id,
                               mastery_level_id = EXCLUDED.mastery_level_id,
                               created_at       = EXCLUDED.created_at,
                               updated_at       = EXCLUDED.updated_at,
                               disabled_at      = EXCLUDED.disabled_at;

-- QUESTION 61
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (61, 'Quel roman gothique commence par la découverte d’un manuscrit mystérieux ?',
        'Le Château d''Otrante est souvent considéré comme le premier roman gothique, et il commence par la découverte d''un manuscrit mystérieux.', 'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (241, 'Le Château d''Otrante', TRUE, 61, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (242, 'Dracula', FALSE, 61, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (243, 'Frankenstein', FALSE, 61, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (244, 'Vathek', FALSE, 61, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 62
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (62, 'Quel objet est souvent porteur de malédiction dans les récits gothiques ?',
        'Dans les récits gothiques, les bijoux anciens sont souvent porteurs de malédictions, symbolisant le passé troublé et les secrets.', 'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (245, 'Un bijou ancien', TRUE, 62, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (246, 'Un chapeau', FALSE, 62, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (247, 'Un livre de cuisine', FALSE, 62, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (248, 'Une montre à gousset', FALSE, 62, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 63
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (63, 'Quel roman gothique fut adapté en ballet par Charles Gounod ?',
        'La Nonne sanglante est un roman gothique qui a été adapté en ballet par Charles Gounod, mettant en avant les thèmes de la passion et du surnaturel.', 'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (249, 'La Nonne sanglante', TRUE, 63, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (250, 'Le Moine', FALSE, 63, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (251, 'Les Mystères d''Udolphe', FALSE, 63, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (252, 'Dracula', FALSE, 63, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 64
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (64, 'Quel écrivain romantique français a écrit des nouvelles gothiques comme "Smarra" ?',
        'Charles Nodier est un écrivain romantique français connu pour ses nouvelles gothiques, notamment "Smarra", qui explore les thèmes du rêve et de l''horreur.', 'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (253, 'Charles Nodier', TRUE, 64, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (254, 'Théophile Gautier', FALSE, 64, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (255, 'Alfred de Musset', FALSE, 64, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (256, 'Jules Verne', FALSE, 64, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 65
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (65, 'Quel roman gothique met en scène un portrait qui vieillit à la place de son propriétaire ?',
        'Le Portrait de Dorian Gray est un roman gothique d’Oscar Wilde où le portrait du protagoniste vieillit à sa place, symbolisant la dépravation et la corruption morale.', 'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (257, 'Le Portrait de Dorian Gray', TRUE, 65, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (258, 'Le Moine', FALSE, 65, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (259, 'Frankenstein', FALSE, 65, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (260, 'Northanger Abbey', FALSE, 65, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 66
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (66, 'Dans quel pays se déroule principalement l’action du roman "Dracula" ?',
        'Le roman "Dracula" de Bram Stoker se déroule principalement en Roumanie, dans la région de la Transylvanie, qui est célèbre pour ses châteaux gothiques et ses légendes de vampires.', 'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (261, 'Roumanie', TRUE, 66, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (262, 'Italie', FALSE, 66, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (263, 'France', FALSE, 66, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (264, 'Espagne', FALSE, 66, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 67
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (67, 'Quel est le rôle du labyrinthe dans l’imaginaire gothique ?',
        'Dans l’imaginaire gothique, le labyrinthe symbolise souvent la perte et l’égarement, représentant les méandres de l’esprit humain et les mystères de l’inconscient.', 'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (265, 'Symboliser la perte et l’égarement', TRUE, 67, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (266, 'Représenter la liberté', FALSE, 67, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (267, 'Illustrer la richesse', FALSE, 67, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (268, 'Montrer la modernité', FALSE, 67, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 68
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (68, 'Quel roman gothique est souvent cité comme précurseur du roman policier ?',
        'Les Mystères d''Udolphe d’Ann Radcliffe est souvent cité comme précurseur du roman policier en raison de ses intrigues complexes et de ses mystères à résoudre.', 'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (269, 'Les Mystères d''Udolphe', TRUE, 68, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (270, 'Dracula', FALSE, 68, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (271, 'Vathek', FALSE, 68, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (272, 'Le Moine', FALSE, 68, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 69
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (69, 'Quel auteur gothique est aussi connu pour ses contes de Noël ?',
        'Charles Dickens est connu pour ses contes de Noël, mais il a également écrit des œuvres gothiques comme "Le Mystère d’Edwin Drood".', 'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (273, 'Charles Dickens', TRUE, 69, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (274, 'Oscar Wilde', FALSE, 69, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (275, 'Mary Shelley', FALSE, 69, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (276, 'Matthew Lewis', FALSE, 69, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 70
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (70, 'Quel élément de la nature est souvent personnifié dans les romans gothiques ?',
        'Dans les romans gothiques, le vent est souvent personnifié, symbolisant les forces invisibles et mystérieuses qui influencent le destin des personnages.', 'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (277, 'Le vent', TRUE, 70, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (278, 'Le soleil', FALSE, 70, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (279, 'La mer', FALSE, 70, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (280, 'Le sable', FALSE, 70, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 71
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (71, 'Quel roman gothique met en scène une femme accusée de sorcellerie ?',
        'Le Moine de Matthew Lewis met en scène une femme accusée de sorcellerie, explorant les thèmes de la répression et de la passion interdite.', 'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (281, 'Le Moine', TRUE, 71, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (282, 'Dracula', FALSE, 71, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (283, 'Frankenstein', FALSE, 71, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (284, 'Vathek', FALSE, 71, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 72
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (72, 'Quel objet permet souvent de révéler un secret dans les récits gothiques ?',
        'Dans les récits gothiques, une clé est souvent utilisée pour révéler un secret, ouvrant des portes vers des mystères cachés et des révélations surprenantes.', 'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (285, 'Une clé', TRUE, 72, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (286, 'Une plume', FALSE, 72, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (287, 'Un miroir', FALSE, 72, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (288, 'Un sablier', FALSE, 72, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 73
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (73, 'Quel roman gothique est célèbre pour ses descriptions de paysages alpins ?',
        'Les Mystères d''Udolphe d’Ann Radcliffe est célèbre pour ses descriptions de paysages alpins, créant une atmosphère mystérieuse et romantique.', 'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (289, 'Frankenstein', TRUE, 73, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (290, 'Le Moine', FALSE, 73, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (291, 'Northanger Abbey', FALSE, 73, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (292, 'Le Château d''Otrante', FALSE, 73, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 74
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (74, 'Quel personnage gothique est souvent associé à la folie ?',
        'Le savant dément est un personnage classique de la littérature gothique, souvent associé à la folie et à la quête de connaissances interdites.', 'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (293, 'Le savant dément', TRUE, 74, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (294, 'Le poète', FALSE, 74, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (295, 'Le prince', FALSE, 74, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (296, 'Le marchand', FALSE, 74, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 75
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (75, 'Quel roman gothique met en scène un héritage mystérieux ?',
        'Northanger Abbey de Jane Austen, bien que principalement une satire, met en scène un héritage mystérieux qui influence l’intrigue.', 'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (297, 'Northanger Abbey', TRUE, 75, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (298, 'Le Moine', FALSE, 75, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (299, 'Dracula', FALSE, 75, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (300, 'Vathek', FALSE, 75, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 76
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (76, 'Quel animal est le plus souvent associé au vampire dans la littérature gothique ?',
        'La chauve-souris est l’animal le plus souvent associé au vampire dans la littérature gothique, symbolisant le mystère et la nuit.', 'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (301, 'La chauve-souris', TRUE, 76, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (302, 'Le chat', FALSE, 76, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (303, 'Le hibou', FALSE, 76, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (304, 'Le loup', FALSE, 76, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 77
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (77, 'Quel rôle joue la musique dans certains romans gothiques ?',
        'Dans certains romans gothiques, la musique joue un rôle crucial pour créer une ambiance envoûtante et inquiétante, renforçant l’atmosphère mystérieuse et sombre.', 'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (305, 'Créer une ambiance envoûtante et inquiétante', TRUE, 77, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (306, 'Apporter de l’humour', FALSE, 77, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (307, 'Montrer la modernité', FALSE, 77, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (308, 'Rassurer le lecteur', FALSE, 77, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 78
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (78, 'Quel roman gothique met en scène une tempête surnaturelle ?',
        'Le Château d''Otrante de Horace Walpole met en scène une tempête surnaturelle qui annonce les événements tragiques et mystérieux du récit.', 'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (309, 'Le Château d''Otrante', TRUE, 78, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (310, 'Frankenstein', FALSE, 78, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (311, 'Northanger Abbey', FALSE, 78, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (312, 'Les Mystères d''Udolphe', FALSE, 78, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 79
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (79, 'Quel écrivain gothique a inspiré le mouvement du "roman noir" en France ?',
        'Ann Radcliffe est souvent considérée comme l''inspiratrice du mouvement du "roman noir" en France, avec ses récits sombres et mystérieux qui ont influencé de nombreux auteurs.', 'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (313, 'Ann Radcliffe', TRUE, 79, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (314, 'Mary Shelley', FALSE, 79, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (315, 'Oscar Wilde', FALSE, 79, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (316, 'Bram Stoker', FALSE, 79, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 80
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (80, 'Quel est l’effet du huis clos dans la littérature gothique ?',
        'Le huis clos dans la littérature gothique renforce la tension et l’angoisse, créant une atmosphère claustrophobique et oppressante qui intensifie les émotions des personnages.', 'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (317, 'Renforcer la tension et l’angoisse', TRUE, 80, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (318, 'Favoriser la détente', FALSE, 80, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (319, 'Rendre le récit comique', FALSE, 80, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (320, 'Ouvrir sur l’aventure', FALSE, 80, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;



-- Insertion des relations entre quiz et questions
INSERT INTO quiz_questions (quiz_id, question_id)
VALUES (4, 61),
       (4, 62),
       (4, 63),
       (4, 64),
       (4, 65),
       (4, 66),
       (4, 67),
       (4, 68),
       (4, 69),
       (4, 70),
       (4, 71),
       (4, 72),
       (4, 73),
       (4, 74),
       (4, 75),
       (4, 76),
       (4, 77),
       (4, 78),
       (4, 79),
       (4, 80)
ON CONFLICT (quiz_id, question_id) DO NOTHING;


-- Mise à jour de la séquence pour les quiz
SELECT setval('quizzes_id_seq', (SELECT MAX(id) FROM quizzes));
-- Mise à jour de la séquence pour les questions
SELECT setval('questions_id_seq', (SELECT MAX(id) FROM questions));
-- Mise à jour de la séquence pour les réponses
SELECT setval('classic_answers_id_seq', (SELECT MAX(id) FROM classic_answers));