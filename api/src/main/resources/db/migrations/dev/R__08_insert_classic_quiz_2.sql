-- Insertion du quiz
INSERT INTO quizzes (id, title, description, category_id, theme_id, quiz_type_code, mastery_level_id, created_at,
                     updated_at, disabled_at)
VALUES (2, 'Littérature gothique : chefs-d’œuvre et figures marquantes (XVIIIe-XIXe siècles)',
        'Un voyage à travers les ténèbres de la littérature gothique, explorant les chefs-d’œuvre et les figures emblématiques du genre.',
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

-- QUESTION 21
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (21, 'Quel roman de Mary Shelley est un pilier du genre gothique ?',
        'Le roman "Frankenstein" de Mary Shelley est considéré comme l’un des premiers romans de science-fiction et un pilier du genre gothique. Il explore les thèmes de la création, de la responsabilité et de l’horreur.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (81, 'Frankenstein', TRUE, 21, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (82, 'Dracula', FALSE, 21, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (83, 'Le Moine', FALSE, 21, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (84, 'Les Mystères d''Udolpho', FALSE, 21, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 22
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (22, 'Quel auteur a popularisé la figure du vampire dans la littérature gothique ?',
        'Bram Stoker est l’auteur du roman "Dracula", qui a popularisé la figure du vampire dans la littérature gothique. Son œuvre a eu une influence majeure sur la représentation des vampires dans la culture populaire.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (85, 'Bram Stoker', TRUE, 22, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (86, 'Horace Walpole', FALSE, 22, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (87, 'Mary Shelley', FALSE, 22, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (88, 'Ann Radcliffe', FALSE, 22, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 23
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (23, 'Quel roman d’Ann Radcliffe est célèbre pour ses descriptions de paysages inquiétants ?',
        'Les "Mystères d’Udolpho" d’Ann Radcliffe est célèbre pour ses descriptions de paysages inquiétants et ses atmosphères mystérieuses. Ce roman est un exemple emblématique du genre gothique.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (89, 'Les Mystères d''Udolpho', TRUE, 23, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (90, 'Northanger Abbey', FALSE, 23, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (91, 'Frankenstein', FALSE, 23, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (92, 'Vathek', FALSE, 23, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 24
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (24, 'Quel est le nom du protagoniste principal dans "Dracula" de Bram Stoker ?',
        'Le protagoniste principal dans "Dracula" de Bram Stoker est Jonathan Harker. Il est un jeune avocat qui se rend en Transylvanie pour aider le comte Dracula à acheter une propriété en Angleterre.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (93, 'Jonathan Harker', TRUE, 24, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (94, 'Victor Frankenstein', FALSE, 24, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (95, 'Ambrosio', FALSE, 24, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (96, 'Manfred', FALSE, 24, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 25
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (25, 'Quel roman gothique se déroule principalement en Italie ?',
        'Les "Mystères d’Udolpho" d’Ann Radcliffe se déroule principalement en Italie. Ce roman est connu pour ses paysages pittoresques et ses châteaux mystérieux.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (97, 'Les Mystères d''Udolpho', TRUE, 25, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (98, 'Dracula', FALSE, 25, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (99, 'Frankenstein', FALSE, 25, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (100, 'Le Château d''Otrante', FALSE, 25, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 26
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (26, 'Quel est le principal antagoniste dans "Le Moine" de Matthew Lewis ?',
        'Le principal antagoniste dans "Le Moine" de Matthew Lewis est le Diable. Ce roman est connu pour ses thèmes sombres et ses explorations de la corruption morale.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (101, 'Le Diable', TRUE, 26, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (102, 'Le Fantôme', FALSE, 26, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (103, 'Le Vampire', FALSE, 26, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (104, 'Le Loup-garou', FALSE, 26, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 27
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (27, 'Quel auteur a écrit "Le Château d’Otrante" ?',
        'Horace Walpole est l’auteur de "Le Château d’Otrante", souvent considéré comme le premier roman gothique. Il a établi de nombreux tropes du genre, tels que les châteaux hantés et les événements surnaturels.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (105, 'Horace Walpole', TRUE, 27, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (106, 'Matthew Lewis', FALSE, 27, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (107, 'William Beckford', FALSE, 27, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (108, 'Mary Shelley', FALSE, 27, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 28
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (28, 'Quel motif architectural est omniprésent dans les romans gothiques ?',
        'Les châteaux sont un motif architectural omniprésent dans les romans gothiques. Ils servent souvent de cadre pour des événements mystérieux et surnaturels.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (109, 'Le château', TRUE, 28, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (110, 'La cabane', FALSE, 28, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (111, 'Le manoir moderne', FALSE, 28, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (112, 'Le palais', FALSE, 28, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 29
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (29, 'Dans quel roman trouve-t-on le personnage de Manfred ?',
        'Le personnage de Manfred est le protagoniste du roman "Le Château d’Otrante" d’Horace Walpole. Il est un noble tourmenté par des événements surnaturels dans son château.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (113, 'Le Château d''Otrante', TRUE, 29, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (114, 'Les Mystères d''Udolpho', FALSE, 29, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (115, 'Dracula', FALSE, 29, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (116, 'Vathek', FALSE, 29, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 30
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (30, 'Quel roman gothique a été publié en 1818 ?',
        'Le roman "Frankenstein" de Mary Shelley a été publié en 1818. Il est souvent considéré comme l’un des premiers romans de science-fiction et un pilier du genre gothique.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (117, 'Frankenstein', TRUE, 30, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (118, 'Le Moine', FALSE, 30, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (119, 'Dracula', FALSE, 30, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (120, 'Le Château d''Otrante', FALSE, 30, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 31
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (31, 'Quel roman gothique est une satire du genre écrite par Jane Austen ?',
        'Northanger Abbey de Jane Austen est une satire du genre gothique. Bien qu’elle utilise des éléments gothiques, elle les tourne en dérision et critique les excès du genre.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (121, 'Northanger Abbey', TRUE, 31, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (122, 'Le Moine', FALSE, 31, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (123, 'Les Mystères d''Udolpho', FALSE, 31, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (124, 'Dracula', FALSE, 31, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 32
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (32, 'Qui est l’auteur du roman "Vathek" ?',
        'William Beckford est l’auteur du roman "Vathek". Ce roman gothique, publié en 1786, est connu pour son atmosphère orientale et ses thèmes de décadence et de débauche.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (125, 'William Beckford', TRUE, 32, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (126, 'Ann Radcliffe', FALSE, 32, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (127, 'Horace Walpole', FALSE, 32, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (128, 'Matthew Lewis', FALSE, 32, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 33
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (33, 'Quel élément surnaturel est fréquent dans la littérature gothique ?',
        'Le surnaturel est un élément fréquent dans la littérature gothique. Il inclut des fantômes, des vampires, des démons et d’autres créatures fantastiques qui créent une atmosphère de mystère et de peur.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (129, 'Le surnaturel', TRUE, 33, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (130, 'Le réalisme social', FALSE, 33, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (131, 'La satire politique', FALSE, 33, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (132, 'L''humour', FALSE, 33, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 34
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (34, 'Quel roman met en scène le personnage d’Emily St. Aubert ?',
        'Les "Mystères d’Udolpho" d’Ann Radcliffe met en scène le personnage d’Emily St. Aubert. Ce roman est un exemple emblématique du genre gothique, avec ses paysages inquiétants et ses intrigues mystérieuses.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (133, 'Les Mystères d''Udolpho', TRUE, 34, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (134, 'Frankenstein', FALSE, 34, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (135, 'Le Moine', FALSE, 34, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (136, 'Northanger Abbey', FALSE, 34, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 35
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (35, 'Quel roman gothique a inspiré de nombreux films sur la créature artificielle ?',
        'Le roman "Frankenstein" de Mary Shelley a inspiré de nombreux films sur la créature artificielle. Il explore les thèmes de la création, de la responsabilité et de l’horreur, et est considéré comme un pilier du genre gothique.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (137, 'Frankenstein', TRUE, 35, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (138, 'Dracula', FALSE, 35, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (139, 'Le Moine', FALSE, 35, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (140, 'Vathek', FALSE, 35, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 36
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (36, 'Quel auteur américain est célèbre pour ses récits gothiques et macabres ?',
        'Edgar Allan Poe est célèbre pour ses récits gothiques et macabres. Ses œuvres, telles que "Le Corbeau" et "Le Cœur révélateur", sont emblématiques du genre gothique américain.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (141, 'Edgar Allan Poe', TRUE, 36, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (142, 'Bram Stoker', FALSE, 36, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (143, 'Mary Shelley', FALSE, 36, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (144, 'Ann Radcliffe', FALSE, 36, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 37
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (37, 'Quel roman gothique explore la dualité du bien et du mal à travers un portrait ?',
        'Le Portrait de Dorian Gray d’Oscar Wilde explore la dualité du bien et du mal à travers un portrait. Le personnage principal, Dorian Gray, reste jeune et beau tandis que son portrait vieillit et reflète ses actions immorales.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (145, 'Le Portrait de Dorian Gray', TRUE, 37, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (146, 'Le Moine', FALSE, 37, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (147, 'Frankenstein', FALSE, 37, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (148, 'Vathek', FALSE, 37, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 38
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (38, 'Quel château est le décor central du roman fondateur du genre ?',
        'Le Château d’Otrante est le décor central du roman fondateur du genre gothique, écrit par Horace Walpole. Ce roman, publié en 1764, a établi de nombreux tropes du genre, tels que les châteaux hantés et les événements surnaturels.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (149, 'Otrante', TRUE, 38, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (150, 'Udolpho', FALSE, 38, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (151, 'Whitby', FALSE, 38, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (152, 'Transylvanie', FALSE, 38, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 39
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (39, 'Quel roman gothique a pour sous-titre "Le Prométhée moderne" ?',
        'Le roman "Frankenstein" de Mary Shelley a pour sous-titre "Le Prométhée moderne". Il explore les thèmes de la création, de la responsabilité et de l’horreur, et est considéré comme un pilier du genre gothique.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (153, 'Frankenstein', TRUE, 39, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (154, 'Dracula', FALSE, 39, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (155, 'Le Moine', FALSE, 39, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (156, 'Les Mystères d''Udolpho', FALSE, 39, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 40
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (40, 'Quel est le rôle du décor dans la littérature gothique ?',
        'Le décor dans la littérature gothique joue un rôle crucial en créant une atmosphère de peur et de mystère. Les châteaux, les paysages sombres et les éléments surnaturels contribuent à l’ambiance inquiétante des récits gothiques.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (157, 'Créer une atmosphère de peur et de mystère', TRUE, 40, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (158, 'Décrire la vie quotidienne', FALSE, 40, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (159, 'Faire rire le lecteur', FALSE, 40, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (160, 'Présenter la société moderne', FALSE, 40, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;



-- Insertion des relations entre quiz et questions
INSERT INTO quiz_questions (quiz_id, question_id)
VALUES (2, 21),
       (2, 22),
       (2, 23),
       (2, 24),
       (2, 25),
       (2, 26),
       (2, 27),
       (2, 28),
       (2, 29),
       (2, 30),
       (2, 31),
       (2, 32),
       (2, 33),
       (2, 34),
       (2, 35),
       (2, 36),
       (2, 37),
       (2, 38),
       (2, 39),
       (2, 40)
ON CONFLICT (quiz_id, question_id) DO NOTHING;


-- Mise à jour de la séquence pour les quiz
SELECT setval('quizzes_id_seq', (SELECT MAX(id) FROM quizzes));
-- Mise à jour de la séquence pour les questions
SELECT setval('questions_id_seq', (SELECT MAX(id) FROM questions));
-- Mise à jour de la séquence pour les réponses
SELECT setval('classic_answers_id_seq', (SELECT MAX(id) FROM classic_answers));