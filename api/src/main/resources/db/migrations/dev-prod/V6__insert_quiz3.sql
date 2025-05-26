-- Insertion du quiz
INSERT INTO quizzes (id, title, id_category, id_theme)
VALUES (3, 'Littérature gothique : motifs, personnages et influences', 7, 4);

-- QUESTION 41
INSERT INTO questions (id, text, id_quiz)
VALUES (41, 'Quel roman gothique met en scène la famille Usher et une mystérieuse maison ?', 3);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (161, 'La Chute de la maison Usher', TRUE, 41),
       (162, 'Le Moine', FALSE, 41),
       (163, 'Frankenstein', FALSE, 41),
       (164, 'Le Château d''Otrante', FALSE, 41);

-- QUESTION 42
INSERT INTO questions (id, text, id_quiz)
VALUES (42, 'Quel personnage féminin est souvent victime dans le roman gothique ?', 3);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (165, 'La demoiselle en détresse', TRUE, 42),
       (166, 'La sorcière', FALSE, 42),
       (167, 'La reine', FALSE, 42),
       (168, 'La servante', FALSE, 42);

-- QUESTION 43
INSERT INTO questions (id, text, id_quiz)
VALUES (43, 'Quel poème d’Edgar Allan Poe est emblématique de l’atmosphère gothique ?', 3);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (169, 'Le Corbeau', TRUE, 43),
       (170, 'Ode à un rossignol', FALSE, 43),
       (171, 'L’Albatros', FALSE, 43),
       (172, 'La Belle Dame sans Merci', FALSE, 43);

-- QUESTION 44
INSERT INTO questions (id, text, id_quiz)
VALUES (44, 'Quel décor naturel est souvent associé à la littérature gothique ?', 3);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (173, 'La forêt sombre', TRUE, 44),
       (174, 'La plage', FALSE, 44),
       (175, 'Le désert', FALSE, 44),
       (176, 'La prairie', FALSE, 44);

-- QUESTION 45
INSERT INTO questions (id, text, id_quiz)
VALUES (45, 'Quel sentiment domine l’ambiance des romans gothiques ?', 3);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (177, 'L’angoisse', TRUE, 45),
       (178, 'La joie', FALSE, 45),
       (179, 'L’espoir', FALSE, 45),
       (180, 'La nostalgie', FALSE, 45);

-- QUESTION 46
INSERT INTO questions (id, text, id_quiz)
VALUES (46, 'Quel roman gothique anglais met en scène un pacte avec le diable ?', 3);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (181, 'Le Moine', TRUE, 46),
       (182, 'Dracula', FALSE, 46),
       (183, 'Northanger Abbey', FALSE, 46),
       (184, 'Le Portrait de Dorian Gray', FALSE, 46);

-- QUESTION 47
INSERT INTO questions (id, text, id_quiz)
VALUES (47, 'Quel personnage est une figure récurrente du roman gothique ?', 3);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (185, 'Le spectre', TRUE, 47),
       (186, 'Le détective', FALSE, 47),
       (187, 'Le pirate', FALSE, 47),
       (188, 'Le chevalier', FALSE, 47);

-- QUESTION 48
INSERT INTO questions (id, text, id_quiz)
VALUES (48, 'Quel roman gothique a pour cadre principal l’abbaye de Northanger ?', 3);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (189, 'Northanger Abbey', TRUE, 48),
       (190, 'Le Moine', FALSE, 48),
       (191, 'Les Mystères d''Udolphe', FALSE, 48),
       (192, 'Vathek', FALSE, 48);

-- QUESTION 49
INSERT INTO questions (id, text, id_quiz)
VALUES (49, 'Quel écrivain français du XIXe siècle a été influencé par le gothique anglais ?', 3);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (193, 'Charles Nodier', TRUE, 49),
       (194, 'Victor Hugo', FALSE, 49),
       (195, 'Gustave Flaubert', FALSE, 49),
       (196, 'Émile Zola', FALSE, 49);

-- QUESTION 50
INSERT INTO questions (id, text, id_quiz)
VALUES (50, 'Quel roman gothique est centré sur la malédiction d’une famille ?', 3);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (197, 'La Chute de la maison Usher', TRUE, 50),
       (198, 'Frankenstein', FALSE, 50),
       (199, 'Le Moine', FALSE, 50),
       (200, 'Vathek', FALSE, 50);

-- QUESTION 51
INSERT INTO questions (id, text, id_quiz)
VALUES (51, 'Quel rôle joue la météo dans la littérature gothique ?', 3);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (201, 'Elle accentue le suspense et la peur', TRUE, 51),
       (202, 'Elle apporte de la légèreté', FALSE, 51),
       (203, 'Elle est toujours ensoleillée', FALSE, 51),
       (204, 'Elle n’a aucun impact', FALSE, 51);

-- QUESTION 52
INSERT INTO questions (id, text, id_quiz)
VALUES (52, 'Quel roman gothique est inspiré par les légendes orientales ?', 3);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (205, 'Vathek', TRUE, 52),
       (206, 'Le Château d''Otrante', FALSE, 52),
       (207, 'Dracula', FALSE, 52),
       (208, 'Frankenstein', FALSE, 52);

-- QUESTION 53
INSERT INTO questions (id, text, id_quiz)
VALUES (53, 'Quel sentiment est souvent ressenti par les héroïnes gothiques ?', 3);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (209, 'La terreur', TRUE, 53),
       (210, 'L’ennui', FALSE, 53),
       (211, 'La fierté', FALSE, 53),
       (212, 'L’indifférence', FALSE, 53);

-- QUESTION 54
INSERT INTO questions (id, text, id_quiz)
VALUES (54, 'Quel animal est fréquemment associé au surnaturel dans le gothique ?', 3);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (213, 'Le corbeau', TRUE, 54),
       (214, 'Le cheval', FALSE, 54),
       (215, 'Le chien', FALSE, 54),
       (216, 'Le serpent', FALSE, 54);

-- QUESTION 55
INSERT INTO questions (id, text, id_quiz)
VALUES (55, 'Quel écrivain britannique a écrit de nombreux contes gothiques pour enfants ?', 3);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (217, 'M. R. James', TRUE, 55),
       (218, 'Jane Austen', FALSE, 55),
       (219, 'Oscar Wilde', FALSE, 55),
       (220, 'Emily Brontë', FALSE, 55);

-- QUESTION 56
INSERT INTO questions (id, text, id_quiz)
VALUES (56, 'Quel motif symbolise la frontière entre le monde des vivants et celui des morts ?', 3);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (221, 'Le cimetière', TRUE, 56),
       (222, 'Le théâtre', FALSE, 56),
       (223, 'La bibliothèque', FALSE, 56),
       (224, 'La taverne', FALSE, 56);

-- QUESTION 57
INSERT INTO questions (id, text, id_quiz)
VALUES (57, 'Quel roman gothique met en scène un tableau maléfique ?', 3);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (225, 'Le Portrait de Dorian Gray', TRUE, 57),
       (226, 'Frankenstein', FALSE, 57),
       (227, 'Le Moine', FALSE, 57),
       (228, 'Les Mystères d''Udolphe', FALSE, 57);

-- QUESTION 58
INSERT INTO questions (id, text, id_quiz)
VALUES (58, 'Quel élément de décor est souvent le théâtre d’apparitions spectrales ?', 3);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (229, 'Le couloir sombre', TRUE, 58),
       (230, 'Le grenier lumineux', FALSE, 58),
       (231, 'La cuisine', FALSE, 58),
       (232, 'Le jardin potager', FALSE, 58);

-- QUESTION 59
INSERT INTO questions (id, text, id_quiz)
VALUES (59, 'Quel roman gothique a pour thème la quête de l’immortalité ?', 3);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (233, 'Le Portrait de Dorian Gray', TRUE, 59),
       (234, 'Le Moine', FALSE, 59),
       (235, 'Dracula', FALSE, 59),
       (236, 'Northanger Abbey', FALSE, 59);

-- QUESTION 60
INSERT INTO questions (id, text, id_quiz)
VALUES (60, 'Quel est l’effet recherché par l’utilisation du surnaturel dans le roman gothique ?', 3);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (237, 'Troubler et fasciner le lecteur', TRUE, 60),
       (238, 'Rassurer le lecteur', FALSE, 60),
       (239, 'Expliquer la science', FALSE, 60),
       (240, 'Faire rire', FALSE, 60);
