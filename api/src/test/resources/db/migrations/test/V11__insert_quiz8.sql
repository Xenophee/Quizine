-- Insertion du quiz
INSERT INTO quizzes (id, title, is_vip_only, id_category, id_theme)
VALUES (8, 'Mythologie égyptienne : dieux, légendes et symboles', FALSE, 14, 1);

-- QUESTION 141
INSERT INTO questions (id, text, id_quiz)
VALUES (141, 'Quel dieu à tête de faucon est associé au soleil ?', 8);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (561, 'Rê', TRUE, 141),
       (562, 'Osiris', FALSE, 141),
       (563, 'Anubis', FALSE, 141),
       (564, 'Seth', FALSE, 141);

-- QUESTION 142
INSERT INTO questions (id, text, id_quiz)
VALUES (142, 'Quel dieu à tête de chacal préside à la momification ?', 8);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (565, 'Anubis', TRUE, 142),
       (566, 'Horus', FALSE, 142),
       (567, 'Thot', FALSE, 142),
       (568, 'Geb', FALSE, 142);

-- QUESTION 143
INSERT INTO questions (id, text, id_quiz)
VALUES (143, 'Quelle déesse est la mère d’Horus et l’épouse d’Osiris ?', 8);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (569, 'Isis', TRUE, 143),
       (570, 'Bastet', FALSE, 143),
       (571, 'Nout', FALSE, 143),
       (572, 'Hathor', FALSE, 143);

-- QUESTION 144
INSERT INTO questions (id, text, id_quiz)
VALUES (144, 'Quel dieu est le juge des morts dans l’au-delà ?', 8);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (573, 'Osiris', TRUE, 144),
       (574, 'Rê', FALSE, 144),
       (575, 'Seth', FALSE, 144),
       (576, 'Ptah', FALSE, 144);

-- QUESTION 145
INSERT INTO questions (id, text, id_quiz)
VALUES (145, 'Quel animal est sacré pour la déesse Bastet ?', 8);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (577, 'Le chat', TRUE, 145),
       (578, 'Le crocodile', FALSE, 145),
       (579, 'Le faucon', FALSE, 145),
       (580, 'Le bélier', FALSE, 145);

-- QUESTION 146
INSERT INTO questions (id, text, id_quiz)
VALUES (146, 'Quel dieu est représenté avec une tête d’ibis ?', 8);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (581, 'Thot', TRUE, 146),
       (582, 'Sobek', FALSE, 146),
       (583, 'Horus', FALSE, 146),
       (584, 'Anubis', FALSE, 146);

-- QUESTION 147
INSERT INTO questions (id, text, id_quiz)
VALUES (147, 'Quel dieu du chaos tua Osiris selon le mythe ?', 8);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (585, 'Seth', TRUE, 147),
       (586, 'Rê', FALSE, 147),
       (587, 'Geb', FALSE, 147),
       (588, 'Khnoum', FALSE, 147);

-- QUESTION 148
INSERT INTO questions (id, text, id_quiz)
VALUES (148, 'Quelle déesse personnifie la justice et l’ordre cosmique ?', 8);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (589, 'Maât', TRUE, 148),
       (590, 'Isis', FALSE, 148),
       (591, 'Néphthys', FALSE, 148),
       (592, 'Sekhmet', FALSE, 148);

-- QUESTION 149
INSERT INTO questions (id, text, id_quiz)
VALUES (149, 'Quel dieu à tête de crocodile règne sur les eaux du Nil ?', 8);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (593, 'Sobek', TRUE, 149),
       (594, 'Horus', FALSE, 149),
       (595, 'Ptah', FALSE, 149),
       (596, 'Rê', FALSE, 149);

-- QUESTION 150
INSERT INTO questions (id, text, id_quiz)
VALUES (150, 'Quel dieu à tête de bélier est le créateur dans certains mythes ?', 8);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (597, 'Khnoum', TRUE, 150),
       (598, 'Geb', FALSE, 150),
       (599, 'Seth', FALSE, 150),
       (600, 'Anubis', FALSE, 150);

-- QUESTION 151
INSERT INTO questions (id, text, id_quiz)
VALUES (151, 'Quel symbole représente la vie éternelle en Égypte ancienne ?', 8);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (601, 'La croix ankh', TRUE, 151),
       (602, 'Le scarabée', FALSE, 151),
       (603, 'Le lotus', FALSE, 151),
       (604, 'Le sceptre ouas', FALSE, 151);

-- QUESTION 152
INSERT INTO questions (id, text, id_quiz)
VALUES (152, 'Quel dieu-enfant est souvent représenté assis sur les genoux d’Isis ?', 8);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (605, 'Horus', TRUE, 152),
       (606, 'Anubis', FALSE, 152),
       (607, 'Seth', FALSE, 152),
       (608, 'Ptah', FALSE, 152);

-- QUESTION 153
INSERT INTO questions (id, text, id_quiz)
VALUES (153, 'Quel animal est associé au dieu Anubis ?', 8);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (609, 'Le chacal', TRUE, 153),
       (610, 'Le crocodile', FALSE, 153),
       (611, 'Le chat', FALSE, 153),
       (612, 'Le taureau', FALSE, 153);

-- QUESTION 154
INSERT INTO questions (id, text, id_quiz)
VALUES (154, 'Quelle déesse à tête de vache est liée à la musique et à l’amour ?', 8);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (613, 'Hathor', TRUE, 154),
       (614, 'Nout', FALSE, 154),
       (615, 'Isis', FALSE, 154),
       (616, 'Maât', FALSE, 154);

-- QUESTION 155
INSERT INTO questions (id, text, id_quiz)
VALUES (155, 'Quel dieu est le protecteur des scribes et des savants ?', 8);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (617, 'Thot', TRUE, 155),
       (618, 'Seth', FALSE, 155),
       (619, 'Sobek', FALSE, 155),
       (620, 'Rê', FALSE, 155);

-- QUESTION 156
INSERT INTO questions (id, text, id_quiz)
VALUES (156, 'Quel animal sacré représente le dieu Apis ?', 8);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (621, 'Le taureau', TRUE, 156),
       (622, 'Le chat', FALSE, 156),
       (623, 'Le faucon', FALSE, 156),
       (624, 'Le crocodile', FALSE, 156);

-- QUESTION 157
INSERT INTO questions (id, text, id_quiz)
VALUES (157, 'Quelle déesse est la sœur et alliée d’Isis ?', 8);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (625, 'Néphthys', TRUE, 157),
       (626, 'Sekhmet', FALSE, 157),
       (627, 'Bastet', FALSE, 157),
       (628, 'Maât', FALSE, 157);

-- QUESTION 158
INSERT INTO questions (id, text, id_quiz)
VALUES (158, 'Quel dieu solaire effectue chaque nuit un voyage dans le monde souterrain ?', 8);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (629, 'Rê', TRUE, 158),
       (630, 'Osiris', FALSE, 158),
       (631, 'Seth', FALSE, 158),
       (632, 'Geb', FALSE, 158);

-- QUESTION 159
INSERT INTO questions (id, text, id_quiz)
VALUES (159, 'Quel animal est associé à la déesse Sekhmet ?', 8);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (633, 'La lionne', TRUE, 159),
       (634, 'Le crocodile', FALSE, 159),
       (635, 'Le chat', FALSE, 159),
       (636, 'Le faucon', FALSE, 159);

-- QUESTION 160
INSERT INTO questions (id, text, id_quiz)
VALUES (160, 'Quel est le nom du livre funéraire le plus célèbre de l’Égypte ancienne ?', 8);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (637, 'Le Livre des Morts', TRUE, 160),
       (638, 'Le Livre des Rois', FALSE, 160),
       (639, 'Le Papyrus d’Annie', FALSE, 160),
       (640, 'Le Livre d’Horus', FALSE, 160);
