-- Insertion du quiz
INSERT INTO quizzes (id, title, description, category_id, theme_id, quiz_type_code, mastery_level_id, created_at,
                     updated_at, disabled_at)
VALUES (9, 'Mythologie nordique : dieux, créatures et légendes vikings',
        'Explorez les dieux, créatures et légendes de la mythologie nordique à travers un quiz captivant.',
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

-- QUESTION 161
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (161, 'Quel dieu nordique est surnommé le "Père de tout" et règne sur Asgard ?',
        'Odin est considéré comme le dieu suprême dans la mythologie nordique. Il règne sur Asgard et est surnommé le "Père de tout" pour son rôle de créateur et chef des dieux.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (641, 'Odin', TRUE, 161, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (642, 'Thor', FALSE, 161, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (643, 'Loki', FALSE, 161, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (644, 'Freyja', FALSE, 161, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 162
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (162, 'Quel dieu est célèbre pour son marteau Mjöllnir et contrôle la foudre ?',
        'Thor est le dieu du tonnerre et de la guerre. Il est célèbre pour son marteau Mjöllnir, qui contrôle la foudre et revient toujours à sa main.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (645, 'Thor', TRUE, 162, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (646, 'Odin', FALSE, 162, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (647, 'Tyr', FALSE, 162, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (648, 'Baldr', FALSE, 162, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 163
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (163, 'Quel dieu est associé à la ruse, au feu et peut changer de forme ?',
        'Loki est un dieu malicieux connu pour sa ruse et sa capacité à se métamorphoser. Il est souvent associé au feu et joue un rôle ambigu dans les légendes.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (649, 'Loki', TRUE, 163, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (650, 'Heimdall', FALSE, 163, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (651, 'Njörd', FALSE, 163, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (652, 'Bragi', FALSE, 163, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 164
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (164, 'Quel animal est le compagnon d’Odin et lui rapporte des nouvelles du monde ?',
        'Odin possède deux corbeaux, Huginn et Muninn, qui parcourent le monde et lui rapportent ce qu’ils voient et entendent.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (653, 'Le corbeau', TRUE, 164, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (654, 'Le loup', FALSE, 164, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (655, 'Le serpent', FALSE, 164, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (656, 'Le cheval', FALSE, 164, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 165
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (165, 'Quel est le nom de l’arbre-monde reliant les neuf royaumes ?',
        'Yggdrasil est l’arbre cosmique qui relie les neuf mondes de la mythologie nordique. Il est central dans la cosmologie nordique.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (657, 'Yggdrasil', TRUE, 165, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (658, 'Valhalla', FALSE, 165, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (659, 'Bifrost', FALSE, 165, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (660, 'Midgard', FALSE, 165, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 166
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (166, 'Quel est le nom du pont arc-en-ciel reliant Asgard à Midgard ?',
        'Bifrost est le pont arc-en-ciel qui relie le monde des dieux (Asgard) à celui des humains (Midgard).',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (661, 'Bifrost', TRUE, 166, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (662, 'Valhalla', FALSE, 166, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (663, 'Helheim', FALSE, 166, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (664, 'Jotunheim', FALSE, 166, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 167
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (167, 'Quelle déesse est associée à l’amour, la fertilité et la guerre ?',
        'Freyja est une déesse majeure associée à l’amour, à la beauté, à la fertilité, mais aussi à la guerre et à la mort.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (665, 'Freyja', TRUE, 167, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (666, 'Frigg', FALSE, 167, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (667, 'Sif', FALSE, 167, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (668, 'Idunn', FALSE, 167, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 168
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (168, 'Quel dieu manchot a sacrifié sa main pour enchaîner le loup Fenrir ?',
        'Tyr est le dieu du courage et de la guerre. Il a sacrifié sa main pour permettre aux dieux d’enchaîner le loup Fenrir.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (669, 'Tyr', TRUE, 168, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (670, 'Odin', FALSE, 168, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (671, 'Thor', FALSE, 168, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (672, 'Baldr', FALSE, 168, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 169
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (169, 'Quel est le nom du loup géant qui dévore Odin lors du Ragnarök ?',
        'Fenrir est un loup monstrueux, fils de Loki. Lors du Ragnarök, il est prophétisé qu’il tuera Odin.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (673, 'Fenrir', TRUE, 169, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (674, 'Jörmungand', FALSE, 169, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (675, 'Sleipnir', FALSE, 169, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (676, 'Garm', FALSE, 169, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 170
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (170, 'Quel serpent géant affronte Thor lors du Ragnarök ?',
        'Jörmungand, aussi appelé le serpent de Midgard, est l’ennemi juré de Thor. Ils se tuent mutuellement lors du Ragnarök.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (677, 'Jörmungand', TRUE, 170, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (678, 'Fenrir', FALSE, 170, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (679, 'Nidhogg', FALSE, 170, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (680, 'Garm', FALSE, 170, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 171
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (171, 'Quel est le nom du paradis des guerriers morts au combat ?',
        'Valhalla est le palais d’Odin où les guerriers valeureux morts au combat sont emmenés par les Valkyries pour se préparer au Ragnarök.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (681, 'Valhalla', TRUE, 171, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (682, 'Helheim', FALSE, 171, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (683, 'Asgard', FALSE, 171, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (684, 'Vanaheim', FALSE, 171, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 172
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (172, 'Quelle prophétie annonce la fin du monde et la mort de nombreux dieux ?',
        'Le Ragnarök est la prophétie apocalyptique de la mythologie nordique marquant la fin des dieux et un renouveau du monde.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (685, 'Ragnarök', TRUE, 172, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (686, 'Edda', FALSE, 172, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (687, 'Yule', FALSE, 172, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (688, 'Fimbulvetr', FALSE, 172, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 173
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (173, 'Quel dieu est le gardien du Bifrost et veille sur Asgard ?',
        'Heimdall est le gardien du pont Bifrost. Il a une ouïe et une vue extraordinaires et protège les dieux contre les intrusions.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (689, 'Heimdall', TRUE, 173, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (690, 'Bragi', FALSE, 173, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (691, 'Njörd', FALSE, 173, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (692, 'Baldr', FALSE, 173, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 174
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (174, 'Quel est le nom du cheval à huit pattes d’Odin ?',
        'Sleipnir est le cheval à huit pattes d’Odin, le plus rapide et le plus puissant des chevaux. Il a été engendré par Loki.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (693, 'Sleipnir', TRUE, 174, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (694, 'Gullinbursti', FALSE, 174, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (695, 'Fenrir', FALSE, 174, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (696, 'Grani', FALSE, 174, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 175
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (175, 'Quel dieu est tué par une flèche de gui lancée par son frère Höd, manipulé par Loki ?',
        'Baldr, dieu aimé de tous, est tué à cause d’un stratagème de Loki qui pousse Höd à lui lancer une flèche faite de gui, son unique faiblesse.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (697, 'Baldr', TRUE, 175, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (698, 'Thor', FALSE, 175, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (699, 'Tyr', FALSE, 175, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (700, 'Vidar', FALSE, 175, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 176
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (176, 'Quel peuple mythique fabrique les armes magiques des dieux nordiques ?',
        'Les nains sont les forgerons des dieux. Ils ont notamment créé Mjöllnir, le marteau de Thor, et d’autres artefacts légendaires.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (701, 'Les nains', TRUE, 176, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (702, 'Les géants', FALSE, 176, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (703, 'Les elfes', FALSE, 176, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (704, 'Les Vanes', FALSE, 176, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 177
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (177, 'Quel dieu est connu pour sa sagesse, la poésie et le sacrifice de son œil ?',
        'Odin est réputé pour sa soif de connaissance. Il a sacrifié un œil pour boire à la source de la sagesse et est aussi le patron des poètes.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (705, 'Odin', TRUE, 177, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (706, 'Bragi', FALSE, 177, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (707, 'Tyr', FALSE, 177, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (708, 'Njörd', FALSE, 177, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 178
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (178, 'Quel est le nom de la déesse de la jeunesse, gardienne des pommes d’immortalité ?',
        'Idunn est la déesse de la jeunesse éternelle. Elle garde les pommes d’or qui empêchent les dieux de vieillir.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (709, 'Idunn', TRUE, 178, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (710, 'Freyja', FALSE, 178, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (711, 'Hel', FALSE, 178, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (712, 'Sif', FALSE, 178, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 179
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (179, 'Quel royaume est le monde des morts dans la mythologie nordique ?',
        'Helheim est le royaume des morts, gouverné par Hel. Il accueille les âmes de ceux qui ne sont pas morts au combat.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (713, 'Helheim', TRUE, 179, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (714, 'Asgard', FALSE, 179, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (715, 'Vanaheim', FALSE, 179, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (716, 'Midgard', FALSE, 179, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 180
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (180, 'Quel dragon ronge les racines d’Yggdrasil ?',
        'Nidhogg est un dragon maléfique qui ronge sans cesse les racines d’Yggdrasil, contribuant au déclin du monde.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (717, 'Nidhogg', TRUE, 180, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (718, 'Jörmungand', FALSE, 180, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (719, 'Fenrir', FALSE, 180, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (720, 'Gullinbursti', FALSE, 180, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;


-- Insertion des relations entre quiz et questions
INSERT INTO quiz_questions (quiz_id, question_id)
VALUES (9, 161),
       (9, 162),
       (9, 163),
       (9, 164),
       (9, 165),
       (9, 166),
       (9, 167),
       (9, 168),
       (9, 169),
       (9, 170),
       (9, 171),
       (9, 172),
       (9, 173),
       (9, 174),
       (9, 175),
       (9, 176),
       (9, 177),
       (9, 178),
       (9, 179),
       (9, 180)
ON CONFLICT (quiz_id, question_id) DO NOTHING;


-- Mise à jour de la séquence pour les quiz
SELECT setval('quizzes_id_seq', (SELECT MAX(id) FROM quizzes));
-- Mise à jour de la séquence pour les questions
SELECT setval('questions_id_seq', (SELECT MAX(id) FROM questions));
-- Mise à jour de la séquence pour les réponses
SELECT setval('classic_answers_id_seq', (SELECT MAX(id) FROM classic_answers));