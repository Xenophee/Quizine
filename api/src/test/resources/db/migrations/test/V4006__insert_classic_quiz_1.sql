-- Insertion du quiz
INSERT INTO quizzes (id, title, description, category_id, theme_id, quiz_type_code, mastery_level_id)
VALUES (1, 'Philosophie grecque : penseurs, écoles et concepts fondateurs',
        'Un voyage à travers les idées et les figures emblématiques de la philosophie grecque antique, explorant les contributions majeures des penseurs qui ont façonné la pensée occidentale.',
        7, 4, 'CLASSIC', 1);

-- QUESTION 1
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (1, 'Quel philosophe grec est célèbre pour sa méthode dialectique et la maxime "Connais-toi toi-même" ?',
        'Socrate est célèbre pour sa méthode dialectique et la maxime "Connais-toi toi-même", qui incite à l’introspection et à la recherche de la vérité.',
        'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (1, 'Socrate', TRUE, 1),
       (2, 'Platon', FALSE, 1),
       (3, 'Aristote', FALSE, 1),
       (4, 'Pythagore', FALSE, 1);

-- QUESTION 2
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (2, 'Quel est le concept central de la philosophie d’Héraclite ?',
        'Héraclite est connu pour son concept de changement perpétuel, symbolisé par la phrase "On ne se baigne jamais deux fois dans le même fleuve".',
        'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (5, 'Le changement perpétuel', TRUE, 2),
       (6, 'Le bonheur', FALSE, 2),
       (7, 'La dialectique', FALSE, 2),
       (8, 'L’ataraxie', FALSE, 2);

-- QUESTION 3
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (3, 'Quel philosophe est considéré comme le fondateur de l’Académie d’Athènes ?',
        'Platon est considéré comme le fondateur de l’Académie d’Athènes, une des premières institutions d’enseignement supérieur dans le monde occidental.',
        'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (9, 'Platon', TRUE, 3),
       (10, 'Socrate', FALSE, 3),
       (11, 'Aristote', FALSE, 3),
       (12, 'Épicure', FALSE, 3);

-- QUESTION 4
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (4, 'Quel philosophe a développé la théorie des Formes ou Idées ?',
        'Platon a développé la théorie des Formes ou Idées, selon laquelle les objets sensibles ne sont que des copies imparfaites de ces Formes idéales.',
        'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (13, 'Platon', TRUE, 4),
       (14, 'Aristote', FALSE, 4),
       (15, 'Socrate', FALSE, 4),
       (16, 'Parménide', FALSE, 4);

-- QUESTION 5
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (5, 'Qui est l’auteur de "L’Éthique à Nicomaque" ?',
        'Aristote est l’auteur de "L’Éthique à Nicomaque", une œuvre majeure sur la morale et la vertu.',
        'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (17, 'Aristote', TRUE, 5),
       (18, 'Platon', FALSE, 5),
       (19, 'Socrate', FALSE, 5),
       (20, 'Zénon', FALSE, 5);

-- QUESTION 6
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (6, 'Quel philosophe présocratique a affirmé que "tout est nombre" ?',
        'Pythagore a affirmé que "tout est nombre", soulignant l’importance des mathématiques dans la compréhension de l’univers.',
        'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (21, 'Pythagore', TRUE, 6),
       (22, 'Thalès', FALSE, 6),
       (23, 'Démocrite', FALSE, 6),
       (24, 'Anaximandre', FALSE, 6);

-- QUESTION 7
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (7, 'Quelle école philosophique prônait l’ataraxie et la recherche du plaisir modéré ?',
        'L’épicurisme prône l’ataraxie (absence de trouble) et la recherche du plaisir modéré comme voie vers le bonheur.',
        'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (25, 'L’épicurisme', TRUE, 7),
       (26, 'Le stoïcisme', FALSE, 7),
       (27, 'Le cynisme', FALSE, 7),
       (28, 'Le platonisme', FALSE, 7);

-- QUESTION 8
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (8, 'Qui est le fondateur du stoïcisme ?',
        'Zénon de Citium est le fondateur du stoïcisme, une école qui enseigne la vertu comme le seul bien et l’importance de la raison.',
        'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (29, 'Zénon de Citium', TRUE, 8),
       (30, 'Épicure', FALSE, 8),
       (31, 'Aristote', FALSE, 8),
       (32, 'Platon', FALSE, 8);

-- QUESTION 9
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (9, 'Quel philosophe a proposé la théorie des quatre éléments (eau, air, feu, terre) ?',
        'Empédocle a proposé la théorie des quatre éléments (eau, air, feu, terre) comme les principes fondamentaux de toute matière.',
        'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (33, 'Empédocle', TRUE, 9),
       (34, 'Thalès', FALSE, 9),
       (35, 'Démocrite', FALSE, 9),
       (36, 'Anaxagore', FALSE, 9);

-- QUESTION 10
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (10, 'Quel philosophe a affirmé que l’archè (principe) de toute chose est l’eau ?',
        'Thalès de Milet a affirmé que l’archè (principe) de toute chose est l’eau, considérant l’eau comme le fondement de toute existence.',
        'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (37, 'Thalès', TRUE, 10),
       (38, 'Anaximandre', FALSE, 10),
       (39, 'Pythagore', FALSE, 10),
       (40, 'Parménide', FALSE, 10);

-- QUESTION 11
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (11, 'Quel philosophe est célèbre pour ses paradoxes sur le mouvement ?',
        'Zénon d’Élée est célèbre pour ses paradoxes sur le mouvement, qui mettent en question la notion de continuité et de changement.',
        'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (41, 'Zénon d’Élée', TRUE, 11),
       (42, 'Héraclite', FALSE, 11),
       (43, 'Parménide', FALSE, 11),
       (44, 'Démocrite', FALSE, 11);

-- QUESTION 12
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (12, 'Quel disciple de Socrate a écrit de nombreux dialogues philosophiques ?',
        'Platon, disciple de Socrate, est célèbre pour ses dialogues philosophiques qui explorent des thèmes tels que la justice, la beauté et la vérité.',
        'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (45, 'Platon', TRUE, 12),
       (46, 'Aristote', FALSE, 12),
       (47, 'Épicure', FALSE, 12),
       (48, 'Zénon', FALSE, 12);

-- QUESTION 13
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (13, 'Quel philosophe a fondé le Lycée et l’école péripatétique ?',
        'Aristote a fondé le Lycée et l’école péripatétique, où il enseignait en marchant avec ses élèves.',
        'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (49, 'Aristote', TRUE, 13),
       (50, 'Platon', FALSE, 13),
       (51, 'Socrate', FALSE, 13),
       (52, 'Pythagore', FALSE, 13);

-- QUESTION 14
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (14, 'Quel philosophe a introduit la notion d’immuabilité de l’être ?',
        'Parménide a introduit la notion d’immuabilité de l’être, affirmant que le changement est une illusion et que la réalité est éternelle et immuable.',
        'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (53, 'Parménide', TRUE, 14),
       (54, 'Héraclite', FALSE, 14),
       (55, 'Empédocle', FALSE, 14),
       (56, 'Anaxagore', FALSE, 14);

-- QUESTION 15
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (15, 'Quelle allégorie célèbre Platon utilise-t-il pour illustrer la connaissance ?',
        'Platon utilise l’allégorie de la caverne pour illustrer la différence entre le monde sensible et le monde intelligible, montrant comment les philosophes accèdent à la connaissance véritable.',
        'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (57, 'L’allégorie de la caverne', TRUE, 15),
       (58, 'L’allégorie du navire', FALSE, 15),
       (59, 'L’allégorie du soleil', FALSE, 15),
       (60, 'L’allégorie du char ailé', FALSE, 15);

-- QUESTION 16
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (16, 'Quel philosophe a développé la théorie atomiste avec Leucippe ?',
        'Démocrite, en collaboration avec Leucippe, a développé la théorie atomiste, affirmant que tout est composé de petites particules indivisibles appelées atomes.',
        'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (61, 'Démocrite', TRUE, 16),
       (62, 'Épicure', FALSE, 16),
       (63, 'Anaxagore', FALSE, 16),
       (64, 'Platon', FALSE, 16);

-- QUESTION 17
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (17, 'Quel concept central Aristote développe-t-il en logique ?',
        'Aristote développe le concept de syllogisme en logique, une forme de raisonnement déductif où une conclusion est tirée de deux prémisses.',
        'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (65, 'Le syllogisme', TRUE, 17),
       (66, 'La dialectique', FALSE, 17),
       (67, 'L’ataraxie', FALSE, 17),
       (68, 'L’archè', FALSE, 17);

-- QUESTION 18
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (18, 'Quel philosophe est célèbre pour sa vie austère et son rejet des conventions sociales ?',
        'Diogène de Sinope est célèbre pour sa vie austère et son rejet des conventions sociales, incarnant les idéaux du cynisme.',
        'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (69, 'Diogène de Sinope', TRUE, 18),
       (70, 'Platon', FALSE, 18),
       (71, 'Socrate', FALSE, 18),
       (72, 'Aristote', FALSE, 18);

-- QUESTION 19
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (19, 'Quel philosophe a fondé l’école du Jardin à Athènes ?',
        'Épicure a fondé l’école du Jardin à Athènes, où il enseignait la recherche du bonheur par la modération et l’amitié.',
        'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (73, 'Épicure', TRUE, 19),
       (74, 'Zénon', FALSE, 19),
       (75, 'Platon', FALSE, 19),
       (76, 'Pythagore', FALSE, 19);

-- QUESTION 20
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (20, 'Quel philosophe présocratique a proposé l’idée d’un "principe infini" (apeiron) ?',
        'Anaximandre a proposé l’idée d’un "principe infini" (apeiron) comme origine de toutes choses, suggérant que l’univers est en perpétuel devenir.',
        'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (77, 'Anaximandre', TRUE, 20),
       (78, 'Thalès', FALSE, 20),
       (79, 'Parménide', FALSE, 20),
       (80, 'Empédocle', FALSE, 20);


-- Insertion des relations entre quiz et questions
INSERT INTO quiz_questions (quiz_id, question_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (1, 5),
       (1, 6),
       (1, 7),
       (1, 8),
       (1, 9),
       (1, 10),
       (1, 11),
       (1, 12),
       (1, 13),
       (1, 14),
       (1, 15),
       (1, 16),
       (1, 17),
       (1, 18),
       (1, 19),
       (1, 20);