-- Insertion du quiz
INSERT INTO quizzes (id, title,id_category, id_theme)
VALUES (1, 'Philosophie grecque : penseurs, écoles et concepts fondateurs', 8, 5);

-- QUESTION 1
INSERT INTO questions (id, text, id_quiz)
VALUES (1, 'Quel philosophe grec est célèbre pour sa méthode dialectique et la maxime "Connais-toi toi-même" ?', 1);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (1, 'Socrate', TRUE, 1),
       (2, 'Platon', FALSE, 1),
       (3, 'Aristote', FALSE, 1),
       (4, 'Pythagore', FALSE, 1);

-- QUESTION 2
INSERT INTO questions (id, text, id_quiz)
VALUES (2, 'Quel est le concept central de la philosophie d’Héraclite ?', 1);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (5, 'Le changement perpétuel', TRUE, 2),
       (6, 'Le bonheur', FALSE, 2),
       (7, 'La dialectique', FALSE, 2),
       (8, 'L’ataraxie', FALSE, 2);

-- QUESTION 3
INSERT INTO questions (id, text, id_quiz)
VALUES (3, 'Quel philosophe est considéré comme le fondateur de l’Académie d’Athènes ?', 1);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (9, 'Platon', TRUE, 3),
       (10, 'Socrate', FALSE, 3),
       (11, 'Aristote', FALSE, 3),
       (12, 'Épicure', FALSE, 3);

-- QUESTION 4
INSERT INTO questions (id, text, id_quiz)
VALUES (4, 'Quel philosophe a développé la théorie des Formes ou Idées ?', 1);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (13, 'Platon', TRUE, 4),
       (14, 'Aristote', FALSE, 4),
       (15, 'Socrate', FALSE, 4),
       (16, 'Parménide', FALSE, 4);

-- QUESTION 5
INSERT INTO questions (id, text, id_quiz)
VALUES (5, 'Qui est l’auteur de "L’Éthique à Nicomaque" ?', 1);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (17, 'Aristote', TRUE, 5),
       (18, 'Platon', FALSE, 5),
       (19, 'Socrate', FALSE, 5),
       (20, 'Zénon', FALSE, 5);

-- QUESTION 6
INSERT INTO questions (id, text, id_quiz)
VALUES (6, 'Quel philosophe présocratique a affirmé que "tout est nombre" ?', 1);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (21, 'Pythagore', TRUE, 6),
       (22, 'Thalès', FALSE, 6),
       (23, 'Démocrite', FALSE, 6),
       (24, 'Anaximandre', FALSE, 6);

-- QUESTION 7
INSERT INTO questions (id, text, id_quiz)
VALUES (7, 'Quelle école philosophique prônait l’ataraxie et la recherche du plaisir modéré ?', 1);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (25, 'L’épicurisme', TRUE, 7),
       (26, 'Le stoïcisme', FALSE, 7),
       (27, 'Le cynisme', FALSE, 7),
       (28, 'Le platonisme', FALSE, 7);

-- QUESTION 8
INSERT INTO questions (id, text, id_quiz)
VALUES (8, 'Qui est le fondateur du stoïcisme ?', 1);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (29, 'Zénon de Citium', TRUE, 8),
       (30, 'Épicure', FALSE, 8),
       (31, 'Aristote', FALSE, 8),
       (32, 'Platon', FALSE, 8);

-- QUESTION 9
INSERT INTO questions (id, text, id_quiz)
VALUES (9, 'Quel philosophe a proposé la théorie des quatre éléments (eau, air, feu, terre) ?', 1);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (33, 'Empédocle', TRUE, 9),
       (34, 'Thalès', FALSE, 9),
       (35, 'Démocrite', FALSE, 9),
       (36, 'Anaxagore', FALSE, 9);

-- QUESTION 10
INSERT INTO questions (id, text, id_quiz)
VALUES (10, 'Quel philosophe a affirmé que l’archè (principe) de toute chose est l’eau ?', 1);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (37, 'Thalès', TRUE, 10),
       (38, 'Anaximandre', FALSE, 10),
       (39, 'Pythagore', FALSE, 10),
       (40, 'Parménide', FALSE, 10);

-- QUESTION 11
INSERT INTO questions (id, text, id_quiz)
VALUES (11, 'Quel philosophe est célèbre pour ses paradoxes sur le mouvement ?', 1);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (41, 'Zénon d’Élée', TRUE, 11),
       (42, 'Héraclite', FALSE, 11),
       (43, 'Parménide', FALSE, 11),
       (44, 'Démocrite', FALSE, 11);

-- QUESTION 12
INSERT INTO questions (id, text, id_quiz)
VALUES (12, 'Quel disciple de Socrate a écrit de nombreux dialogues philosophiques ?', 1);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (45, 'Platon', TRUE, 12),
       (46, 'Aristote', FALSE, 12),
       (47, 'Épicure', FALSE, 12),
       (48, 'Zénon', FALSE, 12);

-- QUESTION 13
INSERT INTO questions (id, text, id_quiz)
VALUES (13, 'Quel philosophe a fondé le Lycée et l’école péripatétique ?', 1);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (49, 'Aristote', TRUE, 13),
       (50, 'Platon', FALSE, 13),
       (51, 'Socrate', FALSE, 13),
       (52, 'Pythagore', FALSE, 13);

-- QUESTION 14
INSERT INTO questions (id, text, id_quiz)
VALUES (14, 'Quel philosophe a introduit la notion d’immuabilité de l’être ?', 1);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (53, 'Parménide', TRUE, 14),
       (54, 'Héraclite', FALSE, 14),
       (55, 'Empédocle', FALSE, 14),
       (56, 'Anaxagore', FALSE, 14);

-- QUESTION 15
INSERT INTO questions (id, text, id_quiz)
VALUES (15, 'Quelle allégorie célèbre Platon utilise-t-il pour illustrer la connaissance ?', 1);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (57, 'L’allégorie de la caverne', TRUE, 15),
       (58, 'L’allégorie du navire', FALSE, 15),
       (59, 'L’allégorie du soleil', FALSE, 15),
       (60, 'L’allégorie du char ailé', FALSE, 15);

-- QUESTION 16
INSERT INTO questions (id, text, id_quiz)
VALUES (16, 'Quel philosophe a développé la théorie atomiste avec Leucippe ?', 1);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (61, 'Démocrite', TRUE, 16),
       (62, 'Épicure', FALSE, 16),
       (63, 'Anaxagore', FALSE, 16),
       (64, 'Platon', FALSE, 16);

-- QUESTION 17
INSERT INTO questions (id, text, id_quiz)
VALUES (17, 'Quel concept central Aristote développe-t-il en logique ?', 1);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (65, 'Le syllogisme', TRUE, 17),
       (66, 'La dialectique', FALSE, 17),
       (67, 'L’ataraxie', FALSE, 17),
       (68, 'L’archè', FALSE, 17);

-- QUESTION 18
INSERT INTO questions (id, text, id_quiz)
VALUES (18, 'Quel philosophe est célèbre pour sa vie austère et son rejet des conventions sociales ?', 1);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (69, 'Diogène de Sinope', TRUE, 18),
       (70, 'Platon', FALSE, 18),
       (71, 'Socrate', FALSE, 18),
       (72, 'Aristote', FALSE, 18);

-- QUESTION 19
INSERT INTO questions (id, text, id_quiz)
VALUES (19, 'Quel philosophe a fondé l’école du Jardin à Athènes ?', 1);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (73, 'Épicure', TRUE, 19),
       (74, 'Zénon', FALSE, 19),
       (75, 'Platon', FALSE, 19),
       (76, 'Pythagore', FALSE, 19);

-- QUESTION 20
INSERT INTO questions (id, text, id_quiz)
VALUES (20, 'Quel philosophe présocratique a proposé l’idée d’un "principe infini" (apeiron) ?', 1);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (77, 'Anaximandre', TRUE, 20),
       (78, 'Thalès', FALSE, 20),
       (79, 'Parménide', FALSE, 20),
       (80, 'Empédocle', FALSE, 20);
