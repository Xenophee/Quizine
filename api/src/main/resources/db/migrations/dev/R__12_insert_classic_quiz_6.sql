-- Insertion du quiz
INSERT INTO quizzes (id, title, description, category_id, theme_id, quiz_type_code, mastery_level_id, created_at,
                     updated_at, disabled_at)
VALUES (6, 'Littérature médiévale : héros, légendes et formes poétiques',
        'Un voyage à travers les épopées, les romans de chevalerie et la poésie médiévale.',
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

-- QUESTION 101
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (101, 'Quel est le principal adversaire de Roland dans la "Chanson de Roland" ?',
        'Le roi Marsile, roi de Saragosse, est l''adversaire principal de Roland dans la "Chanson de Roland".',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (401, 'Marsile', TRUE, 101, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (402, 'Charlemagne', FALSE, 101, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (403, 'Renard', FALSE, 101, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (404, 'Lancelot', FALSE, 101, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 102
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (102, 'Quel type de texte raconte des miracles attribués à des saints ?',
        'La légende hagiographique est un récit qui raconte les miracles et la vie des saints, souvent pour inspirer la foi.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (405, 'La légende hagiographique', TRUE, 102, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (406, 'Le fabliau', FALSE, 102, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (407, 'Le lai', FALSE, 102, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (408, 'La farce', FALSE, 102, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 103
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (103, 'Quel poète médiéval est connu pour ses "Plaintes" et ses "Ballades" ?',
        'Rutebeuf est un poète médiéval français connu pour ses "Plaintes" et ses "Ballades", qui expriment sa vie personnelle et ses souffrances.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (409, 'Rutebeuf', TRUE, 103, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (410, 'Chrétien de Troyes', FALSE, 103, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (411, 'Guillaume de Lorris', FALSE, 103, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (412, 'Adam de la Halle', FALSE, 103, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 104
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (104, 'Quel est le nom du roi légendaire qui règne sur Camelot ?',
        'Arthur est le roi légendaire de Camelot, célèbre pour sa quête du Graal et ses chevaliers de la Table Ronde.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (413, 'Arthur', TRUE, 104, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (414, 'Roland', FALSE, 104, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (415, 'Renart', FALSE, 104, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (416, 'Charlemagne', FALSE, 104, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 105
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (105, 'Quel est le thème principal des "Lais" de Marie de France ?',
        'Les "Lais" de Marie de France sont des récits poétiques qui traitent principalement de l’amour merveilleux et des aventures, souvent avec une touche de fantastique.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (417, 'L’amour merveilleux et les aventures', TRUE, 105, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (418, 'La satire politique', FALSE, 105, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (419, 'La guerre sainte', FALSE, 105, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (420, 'La vie monastique', FALSE, 105, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 106
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (106, 'Quel texte médiéval met en scène la quête du Graal ?',
        'Le "Perceval ou le Conte du Graal" de Chrétien de Troyes est l’un des premiers récits à mettre en scène la quête du Graal, un symbole de pureté et de spiritualité.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (421, 'Perceval ou le Conte du Graal', TRUE, 106, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (422, 'Le Roman de la Rose', FALSE, 106, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (423, 'La Farce de Maître Pathelin', FALSE, 106, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (424, 'Le Livre de la Cité des Dames', FALSE, 106, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 107
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (107, 'Quel animal symbolise la ruse dans la littérature médiévale ?',
        'Le renard est souvent utilisé comme symbole de ruse et de tromperie dans la littérature médiévale, notamment dans le "Roman de Renart".',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (425, 'Le renard', TRUE, 107, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (426, 'Le lion', FALSE, 107, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (427, 'Le cheval', FALSE, 107, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (428, 'Le chien', FALSE, 107, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 108
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (108, 'Quel est le nom du cycle épique consacré à Charlemagne et ses preux ?',
        'La Matière de France est le cycle épique qui traite des exploits de Charlemagne et de ses chevaliers, mettant en avant les valeurs de bravoure et de loyauté.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (429, 'La Matière de France', TRUE, 108, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (430, 'La Matière de Bretagne', FALSE, 108, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (431, 'La Matière de Rome', FALSE, 108, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (432, 'La Matière d’Orient', FALSE, 108, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 109
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (109, 'Quel est le genre du "Roman de Fauvel" ?',
        'Le "Roman de Fauvel" est une satire politique et religieuse qui utilise un âne nommé Fauvel pour critiquer la corruption dans l’Église et la société médiévale.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (433, 'La satire politique et religieuse', TRUE, 109, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (434, 'Le lai', FALSE, 109, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (435, 'La chanson de geste', FALSE, 109, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (436, 'La farce', FALSE, 109, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 110
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (110, 'Quel personnage féminin est célèbre pour ses prophéties dans la légende arthurienne ?',
        'Morgane, également connue sous le nom de Morgane la Fée, est un personnage féminin célèbre dans la légende arthurienne, souvent associée à la magie et aux prophéties.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (437, 'Morgane', TRUE, 110, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (438, 'Iseut', FALSE, 110, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (439, 'Aliénor', FALSE, 110, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (440, 'Béatrice', FALSE, 110, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 111
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (111, 'Quel est le principal cadre des romans de chevalerie ?',
        'La cour du roi est le cadre principal des romans de chevalerie, où se déroulent les aventures des chevaliers et les intrigues amoureuses.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (441, 'La cour du roi', TRUE, 111, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (442, 'Le monastère', FALSE, 111, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (443, 'La place du marché', FALSE, 111, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (444, 'La forêt urbaine', FALSE, 111, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 112
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (112, 'Quel est le nom de la bien-aimée de Tristan ?',
        'Iseut est la bien-aimée de Tristan dans la légende médiévale, célèbre pour leur amour tragique et interdit.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (445, 'Iseut', TRUE, 112, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (446, 'Guenièvre', FALSE, 112, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (447, 'Béatrice', FALSE, 112, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (448, 'Viviane', FALSE, 112, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 113
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (113, 'Quel est le principal mode de diffusion de la poésie lyrique médiévale ?',
        'Le chant accompagné de musique est le principal mode de diffusion de la poésie lyrique médiévale, souvent interprété par des troubadours et des trouvères.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (449, 'Le chant accompagné de musique', TRUE, 113, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (450, 'L’imprimerie', FALSE, 113, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (451, 'La sculpture', FALSE, 113, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (452, 'La peinture', FALSE, 113, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 114
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (114, 'Quel est le nom du célèbre recueil de récits animaliers satiriques ?',
        'Le "Roman de Renart" est un recueil de récits animaliers satiriques qui met en scène des animaux anthropomorphes, notamment le renard, pour critiquer la société médiévale.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (453, 'Le Roman de Renart', TRUE, 114, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (454, 'La Chanson de Roland', FALSE, 114, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (455, 'Le Livre du Voir Dit', FALSE, 114, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (456, 'Le Dit de la panthère d’amour', FALSE, 114, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 115
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (115, 'Quel auteur médiéval est considéré comme l’un des premiers poètes de la langue française ?',
        'Chrétien de Troyes est considéré comme l’un des premiers poètes de la langue française, célèbre pour ses romans de chevalerie et ses contributions à la littérature médiévale.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (457, 'Chrétien de Troyes', TRUE, 115, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (458, 'François Villon', FALSE, 115, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (459, 'Jean de Meung', FALSE, 115, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (460, 'Rutebeuf', FALSE, 115, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 116
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (116, 'Quel genre littéraire médiéval vise à instruire par le rire ?',
        'Le fabliau est un genre littéraire médiéval qui utilise l’humour et la satire pour instruire et divertir, souvent en mettant en scène des personnages de la vie quotidienne.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (461, 'Le fabliau', TRUE, 116, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (462, 'Le lai', FALSE, 116, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (463, 'La chanson de geste', FALSE, 116, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (464, 'Le miracle', FALSE, 116, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 117
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (117, 'Quel est le thème principal du "Dit des trois morts et des trois vifs" ?',
        'Le "Dit des trois morts et des trois vifs" est un poème médiéval qui traite de la méditation sur la mort, un thème récurrent dans la littérature médiévale, soulignant la vanité de la vie et l’inévitabilité de la mort.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (465, 'La méditation sur la mort', TRUE, 117, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (466, 'La conquête amoureuse', FALSE, 117, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (467, 'La guerre', FALSE, 117, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (468, 'La satire politique', FALSE, 117, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 118
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (118, 'Quel est le nom du chevalier qui découvre le Graal dans la légende arthurienne ?',
        'Galahad est le chevalier qui découvre le Graal dans la légende arthurienne, souvent décrit comme le plus pur et le plus vertueux des chevaliers de la Table Ronde.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (469, 'Galahad', TRUE, 118, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (470, 'Gauvain', FALSE, 118, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (471, 'Lancelot', FALSE, 118, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (472, 'Yvain', FALSE, 118, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 119
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (119, 'Quel est le rôle du troubadour dans la société médiévale ?',
        'Le troubadour est un poète et musicien qui compose et chante des poèmes courtois, souvent sur l’amour et la chevalerie, jouant un rôle important dans la culture médiévale.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (473, 'Composer et chanter des poèmes courtois', TRUE, 119, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (474, 'Juger les procès', FALSE, 119, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (475, 'Diriger l’armée', FALSE, 119, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (476, 'Construire des cathédrales', FALSE, 119, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 120
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (120, 'Quel est le principal message des chansons de geste ?',
        'Les chansons de geste exaltent l’héroïsme et la fidélité des chevaliers, souvent en mettant en avant les valeurs de bravoure, d’honneur et de loyauté envers le roi et la patrie.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (477, 'L’exaltation de l’héroïsme et de la fidélité', TRUE, 120, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (478, 'La critique de la société', FALSE, 120, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (479, 'La vie quotidienne des paysans', FALSE, 120, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (480, 'L’humour absurde', FALSE, 120, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;


-- Insertion des relations entre quiz et questions
INSERT INTO quiz_questions (quiz_id, question_id)
VALUES (6, 101),
       (6, 102),
       (6, 103),
       (6, 104),
       (6, 105),
       (6, 106),
       (6, 107),
       (6, 108),
       (6, 109),
       (6, 110),
       (6, 111),
       (6, 112),
       (6, 113),
       (6, 114),
       (6, 115),
       (6, 116),
       (6, 117),
       (6, 118),
       (6, 119),
       (6, 120)
ON CONFLICT (quiz_id, question_id) DO NOTHING;


-- Mise à jour de la séquence pour les quiz
SELECT setval('quizzes_id_seq', (SELECT MAX(id) FROM quizzes));
-- Mise à jour de la séquence pour les questions
SELECT setval('questions_id_seq', (SELECT MAX(id) FROM questions));
-- Mise à jour de la séquence pour les réponses
SELECT setval('classic_answers_id_seq', (SELECT MAX(id) FROM classic_answers));