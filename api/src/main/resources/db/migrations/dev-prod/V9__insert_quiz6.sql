-- Insertion du quiz
INSERT INTO quizzes (id, title, is_vip_only, id_category, id_theme)
VALUES (6, 'Littérature médiévale : héros, légendes et formes poétiques', FALSE, 11, 4);

-- QUESTION 101
INSERT INTO questions (id, text, id_quiz)
VALUES (101, 'Quel est le principal adversaire de Roland dans la "Chanson de Roland" ?', 6);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (401, 'Marsile', TRUE, 101),
       (402, 'Charlemagne', FALSE, 101),
       (403, 'Renard', FALSE, 101),
       (404, 'Lancelot', FALSE, 101);

-- QUESTION 102
INSERT INTO questions (id, text, id_quiz)
VALUES (102, 'Quel type de texte raconte des miracles attribués à des saints ?', 6);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (405, 'La légende hagiographique', TRUE, 102),
       (406, 'Le fabliau', FALSE, 102),
       (407, 'Le lai', FALSE, 102),
       (408, 'La farce', FALSE, 102);

-- QUESTION 103
INSERT INTO questions (id, text, id_quiz)
VALUES (103, 'Quel poète médiéval est connu pour ses "Plaintes" et ses "Ballades" ?', 6);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (409, 'Rutebeuf', TRUE, 103),
       (410, 'Chrétien de Troyes', FALSE, 103),
       (411, 'Guillaume de Lorris', FALSE, 103),
       (412, 'Adam de la Halle', FALSE, 103);

-- QUESTION 104
INSERT INTO questions (id, text, id_quiz)
VALUES (104, 'Quel est le nom du roi légendaire qui règne sur Camelot ?', 6);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (413, 'Arthur', TRUE, 104),
       (414, 'Roland', FALSE, 104),
       (415, 'Renart', FALSE, 104),
       (416, 'Charlemagne', FALSE, 104);

-- QUESTION 105
INSERT INTO questions (id, text, id_quiz)
VALUES (105, 'Quel est le thème principal des "Lais" de Marie de France ?', 6);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (417, 'L’amour merveilleux et les aventures', TRUE, 105),
       (418, 'La satire politique', FALSE, 105),
       (419, 'La guerre sainte', FALSE, 105),
       (420, 'La vie monastique', FALSE, 105);

-- QUESTION 106
INSERT INTO questions (id, text, id_quiz)
VALUES (106, 'Quel texte médiéval met en scène la quête du Graal ?', 6);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (421, 'Perceval ou le Conte du Graal', TRUE, 106),
       (422, 'Le Roman de la Rose', FALSE, 106),
       (423, 'La Farce de Maître Pathelin', FALSE, 106),
       (424, 'Le Livre de la Cité des Dames', FALSE, 106);

-- QUESTION 107
INSERT INTO questions (id, text, id_quiz)
VALUES (107, 'Quel animal symbolise la ruse dans la littérature médiévale ?', 6);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (425, 'Le renard', TRUE, 107),
       (426, 'Le lion', FALSE, 107),
       (427, 'Le cheval', FALSE, 107),
       (428, 'Le chien', FALSE, 107);

-- QUESTION 108
INSERT INTO questions (id, text, id_quiz)
VALUES (108, 'Quel est le nom du cycle épique consacré à Charlemagne et ses preux ?', 6);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (429, 'La Matière de France', TRUE, 108),
       (430, 'La Matière de Bretagne', FALSE, 108),
       (431, 'La Matière de Rome', FALSE, 108),
       (432, 'La Matière d’Orient', FALSE, 108);

-- QUESTION 109
INSERT INTO questions (id, text, id_quiz)
VALUES (109, 'Quel est le genre du "Roman de Fauvel" ?', 6);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (433, 'La satire politique et religieuse', TRUE, 109),
       (434, 'Le lai', FALSE, 109),
       (435, 'La chanson de geste', FALSE, 109),
       (436, 'La farce', FALSE, 109);

-- QUESTION 110
INSERT INTO questions (id, text, id_quiz)
VALUES (110, 'Quel personnage féminin est célèbre pour ses prophéties dans la légende arthurienne ?', 6);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (437, 'Morgane', TRUE, 110),
       (438, 'Iseut', FALSE, 110),
       (439, 'Aliénor', FALSE, 110),
       (440, 'Béatrice', FALSE, 110);

-- QUESTION 111
INSERT INTO questions (id, text, id_quiz)
VALUES (111, 'Quel est le principal cadre des romans de chevalerie ?', 6);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (441, 'La cour du roi', TRUE, 111),
       (442, 'Le monastère', FALSE, 111),
       (443, 'La place du marché', FALSE, 111),
       (444, 'La forêt urbaine', FALSE, 111);

-- QUESTION 112
INSERT INTO questions (id, text, id_quiz)
VALUES (112, 'Quel est le nom de la bien-aimée de Tristan ?', 6);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (445, 'Iseut', TRUE, 112),
       (446, 'Guenièvre', FALSE, 112),
       (447, 'Béatrice', FALSE, 112),
       (448, 'Viviane', FALSE, 112);

-- QUESTION 113
INSERT INTO questions (id, text, id_quiz)
VALUES (113, 'Quel est le principal mode de diffusion de la poésie lyrique médiévale ?', 6);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (449, 'Le chant accompagné de musique', TRUE, 113),
       (450, 'L’imprimerie', FALSE, 113),
       (451, 'La sculpture', FALSE, 113),
       (452, 'La peinture', FALSE, 113);

-- QUESTION 114
INSERT INTO questions (id, text, id_quiz)
VALUES (114, 'Quel est le nom du célèbre recueil de récits animaliers satiriques ?', 6);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (453, 'Le Roman de Renart', TRUE, 114),
       (454, 'La Chanson de Roland', FALSE, 114),
       (455, 'Le Livre du Voir Dit', FALSE, 114),
       (456, 'Le Dit de la panthère d’amour', FALSE, 114);

-- QUESTION 115
INSERT INTO questions (id, text, id_quiz)
VALUES (115, 'Quel auteur médiéval est considéré comme l’un des premiers poètes de la langue française ?', 6);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (457, 'Chrétien de Troyes', TRUE, 115),
       (458, 'François Villon', FALSE, 115),
       (459, 'Jean de Meung', FALSE, 115),
       (460, 'Rutebeuf', FALSE, 115);

-- QUESTION 116
INSERT INTO questions (id, text, id_quiz)
VALUES (116, 'Quel genre littéraire médiéval vise à instruire par le rire ?', 6);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (461, 'Le fabliau', TRUE, 116),
       (462, 'Le lai', FALSE, 116),
       (463, 'La chanson de geste', FALSE, 116),
       (464, 'Le miracle', FALSE, 116);

-- QUESTION 117
INSERT INTO questions (id, text, id_quiz)
VALUES (117, 'Quel est le thème principal du "Dit des trois morts et des trois vifs" ?', 6);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (465, 'La méditation sur la mort', TRUE, 117),
       (466, 'La conquête amoureuse', FALSE, 117),
       (467, 'La guerre', FALSE, 117),
       (468, 'La satire politique', FALSE, 117);

-- QUESTION 118
INSERT INTO questions (id, text, id_quiz)
VALUES (118, 'Quel est le nom du chevalier qui découvre le Graal dans la légende arthurienne ?', 6);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (469, 'Galahad', TRUE, 118),
       (470, 'Gauvain', FALSE, 118),
       (471, 'Lancelot', FALSE, 118),
       (472, 'Yvain', FALSE, 118);

-- QUESTION 119
INSERT INTO questions (id, text, id_quiz)
VALUES (119, 'Quel est le rôle du troubadour dans la société médiévale ?', 6);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (473, 'Composer et chanter des poèmes courtois', TRUE, 119),
       (474, 'Juger les procès', FALSE, 119),
       (475, 'Diriger l’armée', FALSE, 119),
       (476, 'Construire des cathédrales', FALSE, 119);

-- QUESTION 120
INSERT INTO questions (id, text, id_quiz)
VALUES (120, 'Quel est le principal message des chansons de geste ?', 6);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (477, 'L’exaltation de l’héroïsme et de la fidélité', TRUE, 120),
       (478, 'La critique de la société', FALSE, 120),
       (479, 'La vie quotidienne des paysans', FALSE, 120),
       (480, 'L’humour absurde', FALSE, 120);
