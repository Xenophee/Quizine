-- Insertion du quiz
INSERT INTO quizzes (id, title, id_category, id_theme)
VALUES (7, 'Littérature médiévale : figures, genres et merveilles du Moyen Âge', 11, 4);

-- QUESTION 121
INSERT INTO questions (id, text, id_quiz)
VALUES (121, 'Quel est le principal adversaire de Tristan dans la légende médiévale ?', 7);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (481, 'Le roi Marc', TRUE, 121),
       (482, 'Charlemagne', FALSE, 121),
       (483, 'Arthur', FALSE, 121),
       (484, 'Gauvain', FALSE, 121);

-- QUESTION 122
INSERT INTO questions (id, text, id_quiz)
VALUES (122, 'Quel est le genre du "Jeu de Robin et Marion" d’Adam de la Halle ?', 7);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (485, 'La pastourelle', TRUE, 122),
       (486, 'Le lai', FALSE, 122),
       (487, 'La chanson de geste', FALSE, 122),
       (488, 'La ballade', FALSE, 122);

-- QUESTION 123
INSERT INTO questions (id, text, id_quiz)
VALUES (123, 'Quel est le nom du chevalier au lion dans les romans de Chrétien de Troyes ?', 7);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (489, 'Yvain', TRUE, 123),
       (490, 'Perceval', FALSE, 123),
       (491, 'Lancelot', FALSE, 123),
       (492, 'Galahad', FALSE, 123);

-- QUESTION 124
INSERT INTO questions (id, text, id_quiz)
VALUES (124, 'Quel est le principal motif des "Miracles de Notre-Dame" de Gautier de Coinci ?', 7);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (493, 'L’intervention de la Vierge', TRUE, 124),
       (494, 'La satire sociale', FALSE, 124),
       (495, 'La quête du Graal', FALSE, 124),
       (496, 'La ruse animale', FALSE, 124);

-- QUESTION 125
INSERT INTO questions (id, text, id_quiz)
VALUES (125, 'Quel est le nom du géant dans la "Chanson de Roland" ?', 7);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (497, 'Baligant', TRUE, 125),
       (498, 'Ogier', FALSE, 125),
       (499, 'Renart', FALSE, 125),
       (500, 'Ganelon', FALSE, 125);

-- QUESTION 126
INSERT INTO questions (id, text, id_quiz)
VALUES (126, 'Quel est le rôle du ménestrel au Moyen Âge ?', 7);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (501, 'Chanter et raconter des histoires dans les cours', TRUE, 126),
       (502, 'Diriger des armées', FALSE, 126),
       (503, 'Écrire des lois', FALSE, 126),
       (504, 'Construire des cathédrales', FALSE, 126);

-- QUESTION 127
INSERT INTO questions (id, text, id_quiz)
VALUES (127, 'Quel est le thème central du "Roman de Silence" ?', 7);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (505, 'La question de l’identité et du genre', TRUE, 127),
       (506, 'La guerre sainte', FALSE, 127),
       (507, 'La satire religieuse', FALSE, 127),
       (508, 'La quête du Graal', FALSE, 127);

-- QUESTION 128
INSERT INTO questions (id, text, id_quiz)
VALUES (128, 'Quel est le nom du chevalier qui tombe amoureux de Guenièvre ?', 7);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (509, 'Lancelot', TRUE, 128),
       (510, 'Perceval', FALSE, 128),
       (511, 'Tristan', FALSE, 128),
       (512, 'Gauvain', FALSE, 128);

-- QUESTION 129
INSERT INTO questions (id, text, id_quiz)
VALUES (129, 'Quel est le principal sujet des "Sermons joyeux" médiévaux ?', 7);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (513, 'La parodie des thèmes religieux', TRUE, 129),
       (514, 'La guerre', FALSE, 129),
       (515, 'L’amour courtois', FALSE, 129),
       (516, 'La quête chevaleresque', FALSE, 129);

-- QUESTION 130
INSERT INTO questions (id, text, id_quiz)
VALUES (130, 'Quel est le nom du héros qui affronte le dragon dans la littérature médiévale anglo-saxonne ?', 7);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (517, 'Beowulf', TRUE, 130),
       (518, 'Roland', FALSE, 130),
       (519, 'Arthur', FALSE, 130),
       (520, 'Yvain', FALSE, 130);

-- QUESTION 131
INSERT INTO questions (id, text, id_quiz)
VALUES (131, 'Quel est le genre du "Dit" au Moyen Âge ?', 7);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (521, 'Un poème narratif souvent allégorique', TRUE, 131),
       (522, 'Un traité scientifique', FALSE, 131),
       (523, 'Un texte juridique', FALSE, 131),
       (524, 'Une chanson de geste', FALSE, 131);

-- QUESTION 132
INSERT INTO questions (id, text, id_quiz)
VALUES (132, 'Quel personnage est le traître dans la "Chanson de Roland" ?', 7);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (525, 'Ganelon', TRUE, 132),
       (526, 'Marsile', FALSE, 132),
       (527, 'Baligant', FALSE, 132),
       (528, 'Olivier', FALSE, 132);

-- QUESTION 133
INSERT INTO questions (id, text, id_quiz)
VALUES (133, 'Quel est le nom de la fée qui élève Lancelot dans la légende arthurienne ?', 7);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (529, 'Viviane', TRUE, 133),
       (530, 'Morgane', FALSE, 133),
       (531, 'Iseut', FALSE, 133),
       (532, 'Aliénor', FALSE, 133);

-- QUESTION 134
INSERT INTO questions (id, text, id_quiz)
VALUES (134, 'Quel est le principal message des "Dialogues des morts" médiévaux ?', 7);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (533, 'La vanité des biens terrestres', TRUE, 134),
       (534, 'La gloire de la chevalerie', FALSE, 134),
       (535, 'La beauté de la nature', FALSE, 134),
       (536, 'La puissance royale', FALSE, 134);

-- QUESTION 135
INSERT INTO questions (id, text, id_quiz)
VALUES (135, 'Quel est le nom du roman où Perceval cherche le Graal ?', 7);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (537, 'Perceval ou le Conte du Graal', TRUE, 135),
       (538, 'Le Roman de la Rose', FALSE, 135),
       (539, 'Le Roman de Renart', FALSE, 135),
       (540, 'Tristan et Iseut', FALSE, 135);

-- QUESTION 136
INSERT INTO questions (id, text, id_quiz)
VALUES (136, 'Quel est le rôle du scribe au Moyen Âge ?', 7);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (541, 'Copier et illustrer les manuscrits', TRUE, 136),
       (542, 'Diriger les armées', FALSE, 136),
       (543, 'Composer de la musique', FALSE, 136),
       (544, 'Guider les pèlerins', FALSE, 136);

-- QUESTION 137
INSERT INTO questions (id, text, id_quiz)
VALUES (137, 'Quel est le motif principal du "Bestiaire" médiéval ?', 7);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (545, 'Les animaux réels et fantastiques porteurs de symboles', TRUE, 137),
       (546, 'Les batailles épiques', FALSE, 137),
       (547, 'Les récits de miracles', FALSE, 137),
       (548, 'Les histoires d’amour', FALSE, 137);

-- QUESTION 138
INSERT INTO questions (id, text, id_quiz)
VALUES (138, 'Quel est le nom du héros qui affronte le géant Ferragus dans la "Chanson de Roland" ?', 7);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (549, 'Olivier', TRUE, 138),
       (550, 'Roland', FALSE, 138),
       (551, 'Ganelon', FALSE, 138),
       (552, 'Marsile', FALSE, 138);

-- QUESTION 139
INSERT INTO questions (id, text, id_quiz)
VALUES (139, 'Quel est le genre de la "Farce de Maître Pathelin" ?', 7);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (553, 'La comédie burlesque', TRUE, 139),
       (554, 'La chanson de geste', FALSE, 139),
       (555, 'Le lai', FALSE, 139),
       (556, 'Le miracle', FALSE, 139);

-- QUESTION 140
INSERT INTO questions (id, text, id_quiz)
VALUES (140, 'Quel est le principal message des "Danse macabre" médiévales ?', 7);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (557, 'L’égalité de tous devant la mort', TRUE, 140),
       (558, 'La victoire du roi', FALSE, 140),
       (559, 'La puissance de l’amour', FALSE, 140),
       (560, 'La gloire des chevaliers', FALSE, 140);
