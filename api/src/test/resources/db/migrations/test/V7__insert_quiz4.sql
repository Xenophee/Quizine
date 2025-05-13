-- Insertion du quiz
INSERT INTO quizzes (id, title, is_vip_only, id_category, id_theme)
VALUES (4, 'Littérature gothique : symboles, héritages et curiosités', FALSE, 8, 4);

-- QUESTION 61
INSERT INTO questions (id, text, id_quiz)
VALUES (61, 'Quel roman gothique commence par la découverte d’un manuscrit mystérieux ?', 4);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (241, 'Le Château d''Otrante', TRUE, 61),
       (242, 'Dracula', FALSE, 61),
       (243, 'Frankenstein', FALSE, 61),
       (244, 'Vathek', FALSE, 61);

-- QUESTION 62
INSERT INTO questions (id, text, id_quiz)
VALUES (62, 'Quel objet est souvent porteur de malédiction dans les récits gothiques ?', 4);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (245, 'Un bijou ancien', TRUE, 62),
       (246, 'Un chapeau', FALSE, 62),
       (247, 'Un livre de cuisine', FALSE, 62),
       (248, 'Une montre à gousset', FALSE, 62);

-- QUESTION 63
INSERT INTO questions (id, text, id_quiz)
VALUES (63, 'Quel roman gothique fut adapté en ballet par Charles Gounod ?', 4);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (249, 'La Nonne sanglante', TRUE, 63),
       (250, 'Le Moine', FALSE, 63),
       (251, 'Les Mystères d''Udolphe', FALSE, 63),
       (252, 'Dracula', FALSE, 63);

-- QUESTION 64
INSERT INTO questions (id, text, id_quiz)
VALUES (64, 'Quel écrivain romantique français a écrit des nouvelles gothiques comme "Smarra" ?', 4);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (253, 'Charles Nodier', TRUE, 64),
       (254, 'Théophile Gautier', FALSE, 64),
       (255, 'Alfred de Musset', FALSE, 64),
       (256, 'Jules Verne', FALSE, 64);

-- QUESTION 65
INSERT INTO questions (id, text, id_quiz)
VALUES (65, 'Quel roman gothique met en scène un portrait qui vieillit à la place de son propriétaire ?', 4);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (257, 'Le Portrait de Dorian Gray', TRUE, 65),
       (258, 'Le Moine', FALSE, 65),
       (259, 'Frankenstein', FALSE, 65),
       (260, 'Northanger Abbey', FALSE, 65);

-- QUESTION 66
INSERT INTO questions (id, text, id_quiz)
VALUES (66, 'Dans quel pays se déroule principalement l’action du roman "Dracula" ?', 4);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (261, 'Roumanie', TRUE, 66),
       (262, 'Italie', FALSE, 66),
       (263, 'France', FALSE, 66),
       (264, 'Espagne', FALSE, 66);

-- QUESTION 67
INSERT INTO questions (id, text, id_quiz)
VALUES (67, 'Quel est le rôle du labyrinthe dans l’imaginaire gothique ?', 4);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (265, 'Symboliser la perte et l’égarement', TRUE, 67),
       (266, 'Représenter la liberté', FALSE, 67),
       (267, 'Illustrer la richesse', FALSE, 67),
       (268, 'Montrer la modernité', FALSE, 67);

-- QUESTION 68
INSERT INTO questions (id, text, id_quiz)
VALUES (68, 'Quel roman gothique est souvent cité comme précurseur du roman policier ?', 4);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (269, 'Les Mystères d''Udolphe', TRUE, 68),
       (270, 'Dracula', FALSE, 68),
       (271, 'Vathek', FALSE, 68),
       (272, 'Le Moine', FALSE, 68);

-- QUESTION 69
INSERT INTO questions (id, text, id_quiz)
VALUES (69, 'Quel auteur gothique est aussi connu pour ses contes de Noël ?', 4);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (273, 'Charles Dickens', TRUE, 69),
       (274, 'Oscar Wilde', FALSE, 69),
       (275, 'Mary Shelley', FALSE, 69),
       (276, 'Matthew Lewis', FALSE, 69);

-- QUESTION 70
INSERT INTO questions (id, text, id_quiz)
VALUES (70, 'Quel élément de la nature est souvent personnifié dans les romans gothiques ?', 4);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (277, 'Le vent', TRUE, 70),
       (278, 'Le soleil', FALSE, 70),
       (279, 'La mer', FALSE, 70),
       (280, 'Le sable', FALSE, 70);

-- QUESTION 71
INSERT INTO questions (id, text, id_quiz)
VALUES (71, 'Quel roman gothique met en scène une femme accusée de sorcellerie ?', 4);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (281, 'Le Moine', TRUE, 71),
       (282, 'Dracula', FALSE, 71),
       (283, 'Frankenstein', FALSE, 71),
       (284, 'Vathek', FALSE, 71);

-- QUESTION 72
INSERT INTO questions (id, text, id_quiz)
VALUES (72, 'Quel objet permet souvent de révéler un secret dans les récits gothiques ?', 4);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (285, 'Une clé', TRUE, 72),
       (286, 'Une plume', FALSE, 72),
       (287, 'Un miroir', FALSE, 72),
       (288, 'Un sablier', FALSE, 72);

-- QUESTION 73
INSERT INTO questions (id, text, id_quiz)
VALUES (73, 'Quel roman gothique est célèbre pour ses descriptions de paysages alpins ?', 4);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (289, 'Frankenstein', TRUE, 73),
       (290, 'Le Moine', FALSE, 73),
       (291, 'Northanger Abbey', FALSE, 73),
       (292, 'Le Château d''Otrante', FALSE, 73);

-- QUESTION 74
INSERT INTO questions (id, text, id_quiz)
VALUES (74, 'Quel personnage gothique est souvent associé à la folie ?', 4);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (293, 'Le savant dément', TRUE, 74),
       (294, 'Le poète', FALSE, 74),
       (295, 'Le prince', FALSE, 74),
       (296, 'Le marchand', FALSE, 74);

-- QUESTION 75
INSERT INTO questions (id, text, id_quiz)
VALUES (75, 'Quel roman gothique met en scène un héritage mystérieux ?', 4);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (297, 'Northanger Abbey', TRUE, 75),
       (298, 'Le Moine', FALSE, 75),
       (299, 'Dracula', FALSE, 75),
       (300, 'Vathek', FALSE, 75);

-- QUESTION 76
INSERT INTO questions (id, text, id_quiz)
VALUES (76, 'Quel animal est le plus souvent associé au vampire dans la littérature gothique ?', 4);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (301, 'La chauve-souris', TRUE, 76),
       (302, 'Le chat', FALSE, 76),
       (303, 'Le hibou', FALSE, 76),
       (304, 'Le loup', FALSE, 76);

-- QUESTION 77
INSERT INTO questions (id, text, id_quiz)
VALUES (77, 'Quel rôle joue la musique dans certains romans gothiques ?', 4);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (305, 'Créer une ambiance envoûtante et inquiétante', TRUE, 77),
       (306, 'Apporter de l’humour', FALSE, 77),
       (307, 'Montrer la modernité', FALSE, 77),
       (308, 'Rassurer le lecteur', FALSE, 77);

-- QUESTION 78
INSERT INTO questions (id, text, id_quiz)
VALUES (78, 'Quel roman gothique met en scène une tempête surnaturelle ?', 4);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (309, 'Le Château d''Otrante', TRUE, 78),
       (310, 'Frankenstein', FALSE, 78),
       (311, 'Northanger Abbey', FALSE, 78),
       (312, 'Les Mystères d''Udolphe', FALSE, 78);

-- QUESTION 79
INSERT INTO questions (id, text, id_quiz)
VALUES (79, 'Quel écrivain gothique a inspiré le mouvement du "roman noir" en France ?', 4);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (313, 'Ann Radcliffe', TRUE, 79),
       (314, 'Mary Shelley', FALSE, 79),
       (315, 'Oscar Wilde', FALSE, 79),
       (316, 'Bram Stoker', FALSE, 79);

-- QUESTION 80
INSERT INTO questions (id, text, id_quiz)
VALUES (80, 'Quel est l’effet du huis clos dans la littérature gothique ?', 4);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (317, 'Renforcer la tension et l’angoisse', TRUE, 80),
       (318, 'Favoriser la détente', FALSE, 80),
       (319, 'Rendre le récit comique', FALSE, 80),
       (320, 'Ouvrir sur l’aventure', FALSE, 80);
