-- Insertion du quiz
INSERT INTO quizzes (id, title, id_category, id_theme)
VALUES (2, 'Littérature gothique : chefs-d’œuvre et figures marquantes (XVIIIe-XIXe siècles)', 7, 4);

-- QUESTION 21
INSERT INTO questions (id, text, id_quiz)
VALUES (21, 'Quel roman de Mary Shelley est un pilier du genre gothique ?', 2);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (81, 'Frankenstein', TRUE, 21),
       (82, 'Dracula', FALSE, 21),
       (83, 'Le Moine', FALSE, 21),
       (84, 'Les Mystères d''Udolpho', FALSE, 21);

-- QUESTION 22
INSERT INTO questions (id, text, id_quiz)
VALUES (22, 'Quel auteur a popularisé la figure du vampire dans la littérature gothique ?', 2);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (85, 'Bram Stoker', TRUE, 22),
       (86, 'Horace Walpole', FALSE, 22),
       (87, 'Mary Shelley', FALSE, 22),
       (88, 'Ann Radcliffe', FALSE, 22);

-- QUESTION 23
INSERT INTO questions (id, text, id_quiz)
VALUES (23, 'Quel roman d’Ann Radcliffe est célèbre pour ses descriptions de paysages inquiétants ?', 2);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (89, 'Les Mystères d''Udolpho', TRUE, 23),
       (90, 'Northanger Abbey', FALSE, 23),
       (91, 'Frankenstein', FALSE, 23),
       (92, 'Vathek', FALSE, 23);

-- QUESTION 24
INSERT INTO questions (id, text, id_quiz)
VALUES (24, 'Quel est le nom du protagoniste principal dans "Dracula" de Bram Stoker ?', 2);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (93, 'Jonathan Harker', TRUE, 24),
       (94, 'Victor Frankenstein', FALSE, 24),
       (95, 'Ambrosio', FALSE, 24),
       (96, 'Manfred', FALSE, 24);

-- QUESTION 25
INSERT INTO questions (id, text, id_quiz)
VALUES (25, 'Quel roman gothique se déroule principalement en Italie ?', 2);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (97, 'Les Mystères d''Udolpho', TRUE, 25),
       (98, 'Dracula', FALSE, 25),
       (99, 'Frankenstein', FALSE, 25),
       (100, 'Le Château d''Otrante', FALSE, 25);

-- QUESTION 26
INSERT INTO questions (id, text, id_quiz)
VALUES (26, 'Quel est le principal antagoniste dans "Le Moine" de Matthew Lewis ?', 2);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (101, 'Le Diable', TRUE, 26),
       (102, 'Le Fantôme', FALSE, 26),
       (103, 'Le Vampire', FALSE, 26),
       (104, 'Le Loup-garou', FALSE, 26);

-- QUESTION 27
INSERT INTO questions (id, text, id_quiz)
VALUES (27, 'Quel auteur a écrit "Le Château d’Otrante" ?', 2);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (105, 'Horace Walpole', TRUE, 27),
       (106, 'Matthew Lewis', FALSE, 27),
       (107, 'William Beckford', FALSE, 27),
       (108, 'Mary Shelley', FALSE, 27);

-- QUESTION 28
INSERT INTO questions (id, text, id_quiz)
VALUES (28, 'Quel motif architectural est omniprésent dans les romans gothiques ?', 2);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (109, 'Le château', TRUE, 28),
       (110, 'La cabane', FALSE, 28),
       (111, 'Le manoir moderne', FALSE, 28),
       (112, 'Le palais', FALSE, 28);

-- QUESTION 29
INSERT INTO questions (id, text, id_quiz)
VALUES (29, 'Dans quel roman trouve-t-on le personnage de Manfred ?', 2);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (113, 'Le Château d''Otrante', TRUE, 29),
       (114, 'Les Mystères d''Udolpho', FALSE, 29),
       (115, 'Dracula', FALSE, 29),
       (116, 'Vathek', FALSE, 29);

-- QUESTION 30
INSERT INTO questions (id, text, id_quiz)
VALUES (30, 'Quel roman gothique a été publié en 1818 ?', 2);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (117, 'Frankenstein', TRUE, 30),
       (118, 'Le Moine', FALSE, 30),
       (119, 'Dracula', FALSE, 30),
       (120, 'Le Château d''Otrante', FALSE, 30);

-- QUESTION 31
INSERT INTO questions (id, text, id_quiz)
VALUES (31, 'Quel roman gothique est une satire du genre écrite par Jane Austen ?', 2);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (121, 'Northanger Abbey', TRUE, 31),
       (122, 'Le Moine', FALSE, 31),
       (123, 'Les Mystères d''Udolpho', FALSE, 31),
       (124, 'Dracula', FALSE, 31);

-- QUESTION 32
INSERT INTO questions (id, text, id_quiz)
VALUES (32, 'Qui est l’auteur du roman "Vathek" ?', 2);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (125, 'William Beckford', TRUE, 32),
       (126, 'Ann Radcliffe', FALSE, 32),
       (127, 'Horace Walpole', FALSE, 32),
       (128, 'Matthew Lewis', FALSE, 32);

-- QUESTION 33
INSERT INTO questions (id, text, id_quiz)
VALUES (33, 'Quel élément surnaturel est fréquent dans la littérature gothique ?', 2);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (129, 'Le surnaturel', TRUE, 33),
       (130, 'Le réalisme social', FALSE, 33),
       (131, 'La satire politique', FALSE, 33),
       (132, 'L''humour', FALSE, 33);

-- QUESTION 34
INSERT INTO questions (id, text, id_quiz)
VALUES (34, 'Quel roman met en scène le personnage d’Emily St. Aubert ?', 2);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (133, 'Les Mystères d''Udolpho', TRUE, 34),
       (134, 'Frankenstein', FALSE, 34),
       (135, 'Le Moine', FALSE, 34),
       (136, 'Northanger Abbey', FALSE, 34);

-- QUESTION 35
INSERT INTO questions (id, text, id_quiz)
VALUES (35, 'Quel roman gothique a inspiré de nombreux films sur la créature artificielle ?', 2);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (137, 'Frankenstein', TRUE, 35),
       (138, 'Dracula', FALSE, 35),
       (139, 'Le Moine', FALSE, 35),
       (140, 'Vathek', FALSE, 35);

-- QUESTION 36
INSERT INTO questions (id, text, id_quiz)
VALUES (36, 'Quel auteur américain est célèbre pour ses récits gothiques et macabres ?', 2);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (141, 'Edgar Allan Poe', TRUE, 36),
       (142, 'Bram Stoker', FALSE, 36),
       (143, 'Mary Shelley', FALSE, 36),
       (144, 'Ann Radcliffe', FALSE, 36);

-- QUESTION 37
INSERT INTO questions (id, text, id_quiz)
VALUES (37, 'Quel roman gothique explore la dualité du bien et du mal à travers un portrait ?', 2);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (145, 'Le Portrait de Dorian Gray', TRUE, 37),
       (146, 'Le Moine', FALSE, 37),
       (147, 'Frankenstein', FALSE, 37),
       (148, 'Vathek', FALSE, 37);

-- QUESTION 38
INSERT INTO questions (id, text, id_quiz)
VALUES (38, 'Quel château est le décor central du roman fondateur du genre ?', 2);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (149, 'Otrante', TRUE, 38),
       (150, 'Udolpho', FALSE, 38),
       (151, 'Whitby', FALSE, 38),
       (152, 'Transylvanie', FALSE, 38);

-- QUESTION 39
INSERT INTO questions (id, text, id_quiz)
VALUES (39, 'Quel roman gothique a pour sous-titre "Le Prométhée moderne" ?', 2);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (153, 'Frankenstein', TRUE, 39),
       (154, 'Dracula', FALSE, 39),
       (155, 'Le Moine', FALSE, 39),
       (156, 'Les Mystères d''Udolpho', FALSE, 39);

-- QUESTION 40
INSERT INTO questions (id, text, id_quiz)
VALUES (40, 'Quel est le rôle du décor dans la littérature gothique ?', 2);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (157, 'Créer une atmosphère de peur et de mystère', TRUE, 40),
       (158, 'Décrire la vie quotidienne', FALSE, 40),
       (159, 'Faire rire le lecteur', FALSE, 40),
       (160, 'Présenter la société moderne', FALSE, 40);
