-- Insertion du quiz
INSERT INTO quizzes (id, title, description, category_id, theme_id, quiz_type_code, mastery_level_id)
VALUES (8, 'Mythologie égyptienne : dieux, légendes et symboles',
        'Plongez dans l’univers fascinant de la mythologie égyptienne, découvrez les dieux, les légendes et les symboles qui ont façonné cette civilisation millénaire.',
        8, 4, 'CLASSIC', 3);

-- QUESTION 141
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (141, 'Quel dieu à tête de faucon est associé au soleil ?',
        'Rê est le dieu solaire principal de la mythologie égyptienne, souvent représenté avec une tête de faucon surmontée d’un disque solaire.',
        'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (561, 'Rê', TRUE, 141),
       (562, 'Osiris', FALSE, 141),
       (563, 'Anubis', FALSE, 141),
       (564, 'Seth', FALSE, 141);

-- QUESTION 142
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (142, 'Quel dieu à tête de chacal préside à la momification ?',
        'Anubis est le dieu des morts et de la momification, représenté avec une tête de chacal.',
        'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (565, 'Anubis', TRUE, 142),
       (566, 'Horus', FALSE, 142),
       (567, 'Thot', FALSE, 142),
       (568, 'Geb', FALSE, 142);

-- QUESTION 143
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (143, 'Quelle déesse est la mère d’Horus et l’épouse d’Osiris ?',
        'Isis est l’épouse d’Osiris et la mère d’Horus, très vénérée pour ses pouvoirs magiques et protecteurs.',
        'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (569, 'Isis', TRUE, 143),
       (570, 'Bastet', FALSE, 143),
       (571, 'Nout', FALSE, 143),
       (572, 'Hathor', FALSE, 143);

-- QUESTION 144
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (144, 'Quel dieu est le juge des morts dans l’au-delà ?',
        'Osiris est le dieu des morts et préside au jugement des âmes dans le royaume des morts.',
        'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (573, 'Osiris', TRUE, 144),
       (574, 'Rê', FALSE, 144),
       (575, 'Seth', FALSE, 144),
       (576, 'Ptah', FALSE, 144);

-- QUESTION 145
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (145, 'Quel animal est sacré pour la déesse Bastet ?',
        'Le chat est l’animal sacré de Bastet, déesse de la maison, des femmes et des chats.',
        'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (577, 'Le chat', TRUE, 145),
       (578, 'Le crocodile', FALSE, 145),
       (579, 'Le faucon', FALSE, 145),
       (580, 'Le bélier', FALSE, 145);

-- QUESTION 146
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (146, 'Quel dieu est représenté avec une tête d’ibis ?',
        'Thot est le dieu de l’écriture et du savoir, représenté avec une tête d’ibis.',
        'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (581, 'Thot', TRUE, 146),
       (582, 'Sobek', FALSE, 146),
       (583, 'Horus', FALSE, 146),
       (584, 'Anubis', FALSE, 146);

-- QUESTION 147
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (147, 'Quel dieu du chaos tua Osiris selon le mythe ?',
        'Seth est le dieu du chaos, de la guerre et des tempêtes ; il a tué son frère Osiris.',
        'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (585, 'Seth', TRUE, 147),
       (586, 'Rê', FALSE, 147),
       (587, 'Geb', FALSE, 147),
       (588, 'Khnoum', FALSE, 147);

-- QUESTION 148
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (148, 'Quelle déesse personnifie la justice et l’ordre cosmique ?',
        'Maât incarne la justice, la vérité et l’équilibre universel dans la mythologie égyptienne.',
        'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (589, 'Maât', TRUE, 148),
       (590, 'Isis', FALSE, 148),
       (591, 'Néphthys', FALSE, 148),
       (592, 'Sekhmet', FALSE, 148);

-- QUESTION 149
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (149, 'Quel dieu à tête de crocodile règne sur les eaux du Nil ?',
        'Sobek est le dieu crocodile associé aux eaux du Nil et à la fertilité.',
        'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (593, 'Sobek', TRUE, 149),
       (594, 'Horus', FALSE, 149),
       (595, 'Ptah', FALSE, 149),
       (596, 'Rê', FALSE, 149);

-- QUESTION 150
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (150, 'Quel dieu à tête de bélier est le créateur dans certains mythes ?',
        'Khnoum est un dieu créateur, souvent représenté avec une tête de bélier, façonnant les humains sur un tour de potier.',
        'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (597, 'Khnoum', TRUE, 150),
       (598, 'Geb', FALSE, 150),
       (599, 'Seth', FALSE, 150),
       (600, 'Anubis', FALSE, 150);

-- QUESTION 151
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (151, 'Quel symbole représente la vie éternelle en Égypte ancienne ?',
        'La croix ankh est un symbole puissant représentant la vie éternelle dans la culture égyptienne.',
        'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (601, 'La croix ankh', TRUE, 151),
       (602, 'Le scarabée', FALSE, 151),
       (603, 'Le lotus', FALSE, 151),
       (604, 'Le sceptre ouas', FALSE, 151);

-- QUESTION 152
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (152, 'Quel dieu-enfant est souvent représenté assis sur les genoux d’Isis ?',
        'Horus enfant est souvent représenté dans les bras ou sur les genoux de sa mère Isis.',
        'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (605, 'Horus', TRUE, 152),
       (606, 'Anubis', FALSE, 152),
       (607, 'Seth', FALSE, 152),
       (608, 'Ptah', FALSE, 152);

-- QUESTION 153
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (153, 'Quel animal est associé au dieu Anubis ?',
        'Le chacal est l’animal associé à Anubis, lié à la mort et aux nécropoles.',
        'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (609, 'Le chacal', TRUE, 153),
       (610, 'Le crocodile', FALSE, 153),
       (611, 'Le chat', FALSE, 153),
       (612, 'Le taureau', FALSE, 153);

-- QUESTION 154
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (154, 'Quelle déesse à tête de vache est liée à la musique et à l’amour ?',
        'Hathor est une déesse bienveillante associée à l’amour, à la musique et à la maternité.',
        'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (613, 'Hathor', TRUE, 154),
       (614, 'Nout', FALSE, 154),
       (615, 'Isis', FALSE, 154),
       (616, 'Maât', FALSE, 154);

-- QUESTION 155
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (155, 'Quel dieu est le protecteur des scribes et des savants ?',
        'Thot est le dieu de l’écriture, du savoir et des scribes dans l’Égypte antique.',
        'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (617, 'Thot', TRUE, 155),
       (618, 'Seth', FALSE, 155),
       (619, 'Sobek', FALSE, 155),
       (620, 'Rê', FALSE, 155);

-- QUESTION 156
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (156, 'Quel animal sacré représente le dieu Apis ?',
        'Apis est représenté sous la forme d’un taureau sacré, symbole de force et de fertilité.',
        'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (621, 'Le taureau', TRUE, 156),
       (622, 'Le chat', FALSE, 156),
       (623, 'Le faucon', FALSE, 156),
       (624, 'Le crocodile', FALSE, 156);

-- QUESTION 157
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (157, 'Quelle déesse est la sœur et alliée d’Isis ?',
        'Néphthys est la sœur d’Isis et une divinité protectrice des morts, souvent alliée dans les rituels funéraires.',
        'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (625, 'Néphthys', TRUE, 157),
       (626, 'Sekhmet', FALSE, 157),
       (627, 'Bastet', FALSE, 157),
       (628, 'Maât', FALSE, 157);

-- QUESTION 158
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (158, 'Quel dieu solaire effectue chaque nuit un voyage dans le monde souterrain ?',
        'Rê traverse chaque nuit le monde souterrain à bord de sa barque solaire, affrontant les ténèbres avant de renaître à l’aube.',
        'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (629, 'Rê', TRUE, 158),
       (630, 'Osiris', FALSE, 158),
       (631, 'Seth', FALSE, 158),
       (632, 'Geb', FALSE, 158);

-- QUESTION 159
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (159, 'Quel animal est associé à la déesse Sekhmet ?',
        'Sekhmet est représentée avec une tête de lionne, symbolisant sa puissance destructrice.',
        'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (633, 'La lionne', TRUE, 159),
       (634, 'Le crocodile', FALSE, 159),
       (635, 'Le chat', FALSE, 159),
       (636, 'Le faucon', FALSE, 159);

-- QUESTION 160
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (160, 'Quel est le nom du livre funéraire le plus célèbre de l’Égypte ancienne ?',
        'Le Livre des Morts est un recueil de formules magiques destiné à guider les défunts dans l’au-delà.',
        'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (637, 'Le Livre des Morts', TRUE, 160),
       (638, 'Le Livre des Rois', FALSE, 160),
       (639, 'Le Papyrus d’Annie', FALSE, 160),
       (640, 'Le Livre d’Horus', FALSE, 160);



-- Insertion des relations entre quiz et questions
INSERT INTO quiz_questions (quiz_id, question_id)
VALUES (8, 141),
       (8, 142),
       (8, 143),
       (8, 144),
       (8, 145),
       (8, 146),
       (8, 147),
       (8, 148),
       (8, 149),
       (8, 150),
       (8, 151),
       (8, 152),
       (8, 153),
       (8, 154),
       (8, 155),
       (8, 156),
       (8, 157),
       (8, 158),
       (8, 159),
       (8, 160);