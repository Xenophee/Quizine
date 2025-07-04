-- Insertion du quiz
INSERT INTO quizzes (id, title, description, category_id, theme_id, quiz_type_code, mastery_level_id, created_at,
                     updated_at, disabled_at)
VALUES (7, 'Littérature médiévale : figures, genres et merveilles du Moyen Âge',
        'Explorez les chefs-d’œuvre de la littérature médiévale, des épopées chevaleresques aux récits de miracles, en passant par les poèmes allégoriques et les farces comiques.',
        10, 5, 'CLASSIC', 3,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)

ON CONFLICT (id) DO UPDATE SET title            = EXCLUDED.title,
                               description      = EXCLUDED.description,
                               category_id      = EXCLUDED.category_id,
                               theme_id         = EXCLUDED.theme_id,
                               mastery_level_id = EXCLUDED.mastery_level_id,
                               created_at       = EXCLUDED.created_at,
                               updated_at       = EXCLUDED.updated_at,
                               disabled_at      = EXCLUDED.disabled_at;

-- QUESTION 121
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (121, 'Quel est le principal adversaire de Tristan dans la légende médiévale ?',
        'Le roi Marc, oncle de Tristan, est son rival amoureux dans la légende, en raison de leur amour partagé pour Iseut.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (481, 'Le roi Marc', TRUE, 121, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (482, 'Charlemagne', FALSE, 121, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (483, 'Arthur', FALSE, 121, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (484, 'Gauvain', FALSE, 121, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 122
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (122, 'Quel est le genre du "Jeu de Robin et Marion" d’Adam de la Halle ?',
        'Il s’agit d’une pastourelle, forme poétique et musicale qui met en scène l’amour entre un chevalier et une bergère.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (485, 'La pastourelle', TRUE, 122, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (486, 'Le lai', FALSE, 122, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (487, 'La chanson de geste', FALSE, 122, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (488, 'La ballade', FALSE, 122, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 123
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (123, 'Quel est le nom du chevalier au lion dans les romans de Chrétien de Troyes ?',
        'Yvain est appelé "le chevalier au lion" pour avoir été aidé par un lion qu’il a sauvé.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (489, 'Yvain', TRUE, 123, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (490, 'Perceval', FALSE, 123, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (491, 'Lancelot', FALSE, 123, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (492, 'Galahad', FALSE, 123, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 124
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (124, 'Quel est le principal motif des "Miracles de Notre-Dame" de Gautier de Coinci ?',
        'Ces récits médiévaux exaltent la puissance protectrice et miséricordieuse de la Vierge Marie.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (493, 'L’intervention de la Vierge', TRUE, 124, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (494, 'La satire sociale', FALSE, 124, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (495, 'La quête du Graal', FALSE, 124, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (496, 'La ruse animale', FALSE, 124, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 125
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (125, 'Quel est le nom du géant dans la "Chanson de Roland" ?',
        'Baligant, émir de Babylone, est un puissant adversaire de Charlemagne dans l’œuvre.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (497, 'Baligant', TRUE, 125, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (498, 'Ogier', FALSE, 125, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (499, 'Renart', FALSE, 125, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (500, 'Ganelon', FALSE, 125, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 126
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (126, 'Quel est le rôle du ménestrel au Moyen Âge ?',
        'Le ménestrel était un artiste ambulant qui chantait, racontait des histoires et divertissait dans les cours seigneuriales.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (501, 'Chanter et raconter des histoires dans les cours', TRUE, 126, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (502, 'Diriger des armées', FALSE, 126, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (503, 'Écrire des lois', FALSE, 126, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (504, 'Construire des cathédrales', FALSE, 126, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 127
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (127, 'Quel est le thème central du "Roman de Silence" ?',
        'Ce roman aborde la question du genre à travers l’histoire d’une fille élevée comme un garçon pour hériter.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (505, 'La question de l’identité et du genre', TRUE, 127, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (506, 'La guerre sainte', FALSE, 127, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (507, 'La satire religieuse', FALSE, 127, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (508, 'La quête du Graal', FALSE, 127, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 128
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (128, 'Quel est le nom du chevalier qui tombe amoureux de Guenièvre ?',
        'Lancelot est le chevalier de la Table Ronde qui vit un amour interdit avec la reine Guenièvre.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (509, 'Lancelot', TRUE, 128, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (510, 'Perceval', FALSE, 128, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (511, 'Tristan', FALSE, 128, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (512, 'Gauvain', FALSE, 128, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 129
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (129, 'Quel est le principal sujet des "Sermons joyeux" médiévaux ?',
        'Ils tournent en dérision les sermons religieux sérieux en les parodiant, souvent sur un ton satirique.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (513, 'La parodie des thèmes religieux', TRUE, 129, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (514, 'La guerre', FALSE, 129, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (515, 'L’amour courtois', FALSE, 129, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (516, 'La quête chevaleresque', FALSE, 129, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 130
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (130, 'Quel est le nom du héros qui affronte le dragon dans la littérature médiévale anglo-saxonne ?',
        'Beowulf, héros anglo-saxon, tue un dragon dans l’ultime partie de son épopée.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (517, 'Beowulf', TRUE, 130, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (518, 'Roland', FALSE, 130, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (519, 'Arthur', FALSE, 130, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (520, 'Yvain', FALSE, 130, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 131
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (131, 'Quel est le genre du "Dit" au Moyen Âge ?',
        'Le "Dit" est un poème narratif, souvent allégorique ou moral, caractéristique de la poésie du XIVe siècle.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (521, 'Un poème narratif souvent allégorique', TRUE, 131, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (522, 'Un traité scientifique', FALSE, 131, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (523, 'Un texte juridique', FALSE, 131, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (524, 'Une chanson de geste', FALSE, 131, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 132
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (132, 'Quel personnage est le traître dans la "Chanson de Roland" ?',
        'Ganelon trahit Roland en pactisant avec les Sarrasins, provoquant la mort de son neveu à Roncevaux.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (525, 'Ganelon', TRUE, 132, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (526, 'Marsile', FALSE, 132, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (527, 'Baligant', FALSE, 132, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (528, 'Olivier', FALSE, 132, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 133
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (133, 'Quel est le nom de la fée qui élève Lancelot dans la légende arthurienne ?',
        'Viviane, la Dame du Lac, recueille Lancelot enfant et l’élève dans un monde féerique avant qu’il ne devienne chevalier.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (529, 'Viviane', TRUE, 133, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (530, 'Morgane', FALSE, 133, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (531, 'Iseut', FALSE, 133, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (532, 'Aliénor', FALSE, 133, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 134
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (134, 'Quel est le principal message des "Dialogues des morts" médiévaux ?',
        'Les "Dialogues des morts" mettent en scène des conversations entre défunts pour souligner la vanité des richesses et du pouvoir terrestre.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (533, 'La vanité des biens terrestres', TRUE, 134, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (534, 'La gloire de la chevalerie', FALSE, 134, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (535, 'La beauté de la nature', FALSE, 134, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (536, 'La puissance royale', FALSE, 134, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 135
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (135, 'Quel est le nom du roman où Perceval cherche le Graal ?',
        '"Perceval ou le Conte du Graal", écrit par Chrétien de Troyes, est le premier roman à évoquer la quête du Graal par le jeune chevalier.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (537, 'Perceval ou le Conte du Graal', TRUE, 135, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (538, 'Le Roman de la Rose', FALSE, 135, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (539, 'Le Roman de Renart', FALSE, 135, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (540, 'Tristan et Iseut', FALSE, 135, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 136
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (136, 'Quel est le rôle du scribe au Moyen Âge ?',
        'Les scribes médiévaux copiaient à la main les textes religieux, littéraires et juridiques, contribuant à la transmission du savoir.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (541, 'Copier et illustrer les manuscrits', TRUE, 136, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (542, 'Diriger les armées', FALSE, 136, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (543, 'Composer de la musique', FALSE, 136, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (544, 'Guider les pèlerins', FALSE, 136, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 137
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (137, 'Quel est le motif principal du "Bestiaire" médiéval ?',
        'Les bestiaires médiévaux associaient chaque animal à une signification symbolique ou morale, mêlant réel et fantastique.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (545, 'Les animaux réels et fantastiques porteurs de symboles', TRUE, 137, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (546, 'Les batailles épiques', FALSE, 137, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (547, 'Les récits de miracles', FALSE, 137, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (548, 'Les histoires d’amour', FALSE, 137, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 138
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (138, 'Quel est le nom du héros qui affronte le géant Ferragus dans la "Chanson de Roland" ?',
        'Olivier, fidèle compagnon de Roland, affronte Ferragus, un géant redoutable, dans un épisode célèbre de la "Chanson de Roland".',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (549, 'Olivier', TRUE, 138, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (550, 'Roland', FALSE, 138, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (551, 'Ganelon', FALSE, 138, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (552, 'Marsile', FALSE, 138, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 139
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (139, 'Quel est le genre de la "Farce de Maître Pathelin" ?',
        'La "Farce de Maître Pathelin" est une comédie burlesque qui tourne en dérision les travers de la société médiévale.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (553, 'La comédie burlesque', TRUE, 139, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (554, 'La chanson de geste', FALSE, 139, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (555, 'Le lai', FALSE, 139, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (556, 'Le miracle', FALSE, 139, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 140
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (140, 'Quel est le principal message des "Danse macabre" médiévales ?',
        'Les "Danses macabres" rappellent que la mort frappe toutes les classes sociales, du roi au paysan, sans distinction.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (557, 'L’égalité de tous devant la mort', TRUE, 140, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (558, 'La victoire du roi', FALSE, 140, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (559, 'La puissance de l’amour', FALSE, 140, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (560, 'La gloire des chevaliers', FALSE, 140, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;



-- Insertion des relations entre quiz et questions
INSERT INTO quiz_questions (quiz_id, question_id)
VALUES (7, 121),
       (7, 122),
       (7, 123),
       (7, 124),
       (7, 125),
       (7, 126),
       (7, 127),
       (7, 128),
       (7, 129),
       (7, 130),
       (7, 131),
       (7, 132),
       (7, 133),
       (7, 134),
       (7, 135),
       (7, 136),
       (7, 137),
       (7, 138),
       (7, 139),
       (7, 140)
ON CONFLICT (quiz_id, question_id) DO NOTHING;


-- Mise à jour de la séquence pour les quiz
SELECT setval('quizzes_id_seq', (SELECT MAX(id) FROM quizzes));
-- Mise à jour de la séquence pour les questions
SELECT setval('questions_id_seq', (SELECT MAX(id) FROM questions));
-- Mise à jour de la séquence pour les réponses
SELECT setval('classic_answers_id_seq', (SELECT MAX(id) FROM classic_answers));