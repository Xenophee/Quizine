-- Insertion du quiz
INSERT INTO quizzes (id, title, description, category_id, theme_id, quiz_type_code, mastery_level_id, created_at,
                     updated_at, disabled_at)
VALUES (8, 'Mythologie égyptienne : dieux, légendes et symboles',
        'Plongez dans l’univers fascinant de la mythologie égyptienne, découvrez les dieux, les légendes et les symboles qui ont façonné cette civilisation millénaire.',
        8, 4, 'CLASSIC', 3,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)

ON CONFLICT (id) DO UPDATE SET title            = EXCLUDED.title,
                               description      = EXCLUDED.description,
                               category_id      = EXCLUDED.category_id,
                               theme_id         = EXCLUDED.theme_id,
                               mastery_level_id = EXCLUDED.mastery_level_id,
                               created_at       = EXCLUDED.created_at,
                               updated_at       = EXCLUDED.updated_at,
                               disabled_at      = EXCLUDED.disabled_at;

-- QUESTION 141
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (141, 'Quel dieu à tête de faucon est associé au soleil ?',
        'Rê est le dieu solaire principal de la mythologie égyptienne, souvent représenté avec une tête de faucon surmontée d’un disque solaire.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (561, 'Rê', TRUE, 141, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (562, 'Osiris', FALSE, 141, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (563, 'Anubis', FALSE, 141, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (564, 'Seth', FALSE, 141, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 142
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (142, 'Quel dieu à tête de chacal préside à la momification ?',
        'Anubis est le dieu des morts et de la momification, représenté avec une tête de chacal.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (565, 'Anubis', TRUE, 142, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (566, 'Horus', FALSE, 142, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (567, 'Thot', FALSE, 142, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (568, 'Geb', FALSE, 142, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 143
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (143, 'Quelle déesse est la mère d’Horus et l’épouse d’Osiris ?',
        'Isis est l’épouse d’Osiris et la mère d’Horus, très vénérée pour ses pouvoirs magiques et protecteurs.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (569, 'Isis', TRUE, 143, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (570, 'Bastet', FALSE, 143, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (571, 'Nout', FALSE, 143, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (572, 'Hathor', FALSE, 143, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 144
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (144, 'Quel dieu est le juge des morts dans l’au-delà ?',
        'Osiris est le dieu des morts et préside au jugement des âmes dans le royaume des morts.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (573, 'Osiris', TRUE, 144, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (574, 'Rê', FALSE, 144, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (575, 'Seth', FALSE, 144, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (576, 'Ptah', FALSE, 144, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 145
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (145, 'Quel animal est sacré pour la déesse Bastet ?',
        'Le chat est l’animal sacré de Bastet, déesse de la maison, des femmes et des chats.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (577, 'Le chat', TRUE, 145, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (578, 'Le crocodile', FALSE, 145, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (579, 'Le faucon', FALSE, 145, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (580, 'Le bélier', FALSE, 145, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 146
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (146, 'Quel dieu est représenté avec une tête d’ibis ?',
        'Thot est le dieu de l’écriture et du savoir, représenté avec une tête d’ibis.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (581, 'Thot', TRUE, 146, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (582, 'Sobek', FALSE, 146, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (583, 'Horus', FALSE, 146, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (584, 'Anubis', FALSE, 146, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 147
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (147, 'Quel dieu du chaos tua Osiris selon le mythe ?',
        'Seth est le dieu du chaos, de la guerre et des tempêtes ; il a tué son frère Osiris.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (585, 'Seth', TRUE, 147, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (586, 'Rê', FALSE, 147, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (587, 'Geb', FALSE, 147, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (588, 'Khnoum', FALSE, 147, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 148
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (148, 'Quelle déesse personnifie la justice et l’ordre cosmique ?',
        'Maât incarne la justice, la vérité et l’équilibre universel dans la mythologie égyptienne.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (589, 'Maât', TRUE, 148, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (590, 'Isis', FALSE, 148, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (591, 'Néphthys', FALSE, 148, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (592, 'Sekhmet', FALSE, 148, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 149
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (149, 'Quel dieu à tête de crocodile règne sur les eaux du Nil ?',
        'Sobek est le dieu crocodile associé aux eaux du Nil et à la fertilité.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (593, 'Sobek', TRUE, 149, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (594, 'Horus', FALSE, 149, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (595, 'Ptah', FALSE, 149, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (596, 'Rê', FALSE, 149, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 150
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (150, 'Quel dieu à tête de bélier est le créateur dans certains mythes ?',
        'Khnoum est un dieu créateur, souvent représenté avec une tête de bélier, façonnant les humains sur un tour de potier.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (597, 'Khnoum', TRUE, 150, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (598, 'Geb', FALSE, 150, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (599, 'Seth', FALSE, 150, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (600, 'Anubis', FALSE, 150, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 151
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (151, 'Quel symbole représente la vie éternelle en Égypte ancienne ?',
        'La croix ankh est un symbole puissant représentant la vie éternelle dans la culture égyptienne.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (601, 'La croix ankh', TRUE, 151, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (602, 'Le scarabée', FALSE, 151, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (603, 'Le lotus', FALSE, 151, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (604, 'Le sceptre ouas', FALSE, 151, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 152
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (152, 'Quel dieu-enfant est souvent représenté assis sur les genoux d’Isis ?',
        'Horus enfant est souvent représenté dans les bras ou sur les genoux de sa mère Isis.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (605, 'Horus', TRUE, 152, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (606, 'Anubis', FALSE, 152, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (607, 'Seth', FALSE, 152, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (608, 'Ptah', FALSE, 152, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 153
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (153, 'Quel animal est associé au dieu Anubis ?',
        'Le chacal est l’animal associé à Anubis, lié à la mort et aux nécropoles.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (609, 'Le chacal', TRUE, 153, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (610, 'Le crocodile', FALSE, 153, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (611, 'Le chat', FALSE, 153, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (612, 'Le taureau', FALSE, 153, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 154
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (154, 'Quelle déesse à tête de vache est liée à la musique et à l’amour ?',
        'Hathor est une déesse bienveillante associée à l’amour, à la musique et à la maternité.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (613, 'Hathor', TRUE, 154, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (614, 'Nout', FALSE, 154, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (615, 'Isis', FALSE, 154, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (616, 'Maât', FALSE, 154, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 155
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (155, 'Quel dieu est le protecteur des scribes et des savants ?',
        'Thot est le dieu de l’écriture, du savoir et des scribes dans l’Égypte antique.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (617, 'Thot', TRUE, 155, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (618, 'Seth', FALSE, 155, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (619, 'Sobek', FALSE, 155, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (620, 'Rê', FALSE, 155, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 156
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (156, 'Quel animal sacré représente le dieu Apis ?',
        'Apis est représenté sous la forme d’un taureau sacré, symbole de force et de fertilité.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (621, 'Le taureau', TRUE, 156, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (622, 'Le chat', FALSE, 156, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (623, 'Le faucon', FALSE, 156, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (624, 'Le crocodile', FALSE, 156, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 157
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (157, 'Quelle déesse est la sœur et alliée d’Isis ?',
        'Néphthys est la sœur d’Isis et une divinité protectrice des morts, souvent alliée dans les rituels funéraires.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (625, 'Néphthys', TRUE, 157, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (626, 'Sekhmet', FALSE, 157, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (627, 'Bastet', FALSE, 157, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (628, 'Maât', FALSE, 157, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 158
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (158, 'Quel dieu solaire effectue chaque nuit un voyage dans le monde souterrain ?',
        'Rê traverse chaque nuit le monde souterrain à bord de sa barque solaire, affrontant les ténèbres avant de renaître à l’aube.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (629, 'Rê', TRUE, 158, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (630, 'Osiris', FALSE, 158, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (631, 'Seth', FALSE, 158, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (632, 'Geb', FALSE, 158, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 159
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (159, 'Quel animal est associé à la déesse Sekhmet ?',
        'Sekhmet est représentée avec une tête de lionne, symbolisant sa puissance destructrice.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (633, 'La lionne', TRUE, 159, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (634, 'Le crocodile', FALSE, 159, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (635, 'Le chat', FALSE, 159, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (636, 'Le faucon', FALSE, 159, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 160
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (160, 'Quel est le nom du livre funéraire le plus célèbre de l’Égypte ancienne ?',
        'Le Livre des Morts est un recueil de formules magiques destiné à guider les défunts dans l’au-delà.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (637, 'Le Livre des Morts', TRUE, 160, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (638, 'Le Livre des Rois', FALSE, 160, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (639, 'Le Papyrus d’Annie', FALSE, 160, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (640, 'Le Livre d’Horus', FALSE, 160, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;



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
       (8, 160)
ON CONFLICT (quiz_id, question_id) DO NOTHING;


-- Mise à jour de la séquence pour les quiz
SELECT setval('quizzes_id_seq', (SELECT MAX(id) FROM quizzes));
-- Mise à jour de la séquence pour les questions
SELECT setval('questions_id_seq', (SELECT MAX(id) FROM questions));
-- Mise à jour de la séquence pour les réponses
SELECT setval('classic_answers_id_seq', (SELECT MAX(id) FROM classic_answers));