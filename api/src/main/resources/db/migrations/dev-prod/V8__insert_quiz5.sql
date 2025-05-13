-- Insertion du quiz
INSERT INTO quizzes (id, title, is_vip_only, id_category, id_theme)
VALUES (5, 'Littérature médiévale : épopées, romans et fabliaux', FALSE, 11, 4);

-- QUESTION 81
INSERT INTO questions (id, text, id_quiz)
VALUES (81, 'Quel est le plus célèbre poème épique de la littérature française médiévale ?', 5);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (321, 'La Chanson de Roland', TRUE, 81),
       (322, 'Le Roman de la Rose', FALSE, 81),
       (323, 'Le Livre de la Cité des Dames', FALSE, 81),
       (324, 'Tristan et Iseut', FALSE, 81);

-- QUESTION 82
INSERT INTO questions (id, text, id_quiz)
VALUES (82, 'Quel genre littéraire médiéval met en scène des aventures chevaleresques ?', 5);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (325, 'Le roman courtois', TRUE, 82),
       (326, 'Le fabliau', FALSE, 82),
       (327, 'La farce', FALSE, 82),
       (328, 'La ballade', FALSE, 82);

-- QUESTION 83
INSERT INTO questions (id, text, id_quiz)
VALUES (83, 'Qui est l''auteur du "Roman de la Rose" ?', 5);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (329, 'Guillaume de Lorris et Jean de Meung', TRUE, 83),
       (330, 'Chrétien de Troyes', FALSE, 83),
       (331, 'Marie de France', FALSE, 83),
       (332, 'Rutebeuf', FALSE, 83);

-- QUESTION 84
INSERT INTO questions (id, text, id_quiz)
VALUES (84, 'Quel personnage incarne l’idéal du chevalier courtois ?', 5);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (333, 'Lancelot', TRUE, 84),
       (334, 'Gargantua', FALSE, 84),
       (335, 'Roland', FALSE, 84),
       (336, 'Renard', FALSE, 84);

-- QUESTION 85
INSERT INTO questions (id, text, id_quiz)
VALUES (85, 'Quel animal rusé est le héros d’un célèbre recueil médiéval ?', 5);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (337, 'Le renard', TRUE, 85),
       (338, 'Le loup', FALSE, 85),
       (339, 'Le lion', FALSE, 85),
       (340, 'Le cerf', FALSE, 85);

-- QUESTION 86
INSERT INTO questions (id, text, id_quiz)
VALUES (86, 'Quel est le principal thème des fabliaux ?', 5);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (341, 'La satire sociale et les plaisanteries', TRUE, 86),
       (342, 'L’amour courtois', FALSE, 86),
       (343, 'La guerre', FALSE, 86),
       (344, 'La foi religieuse', FALSE, 86);

-- QUESTION 87
INSERT INTO questions (id, text, id_quiz)
VALUES (87, 'Quel poète est célèbre pour ses ballades mélancoliques au Moyen Âge ?', 5);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (345, 'François Villon', TRUE, 87),
       (346, 'Rutebeuf', FALSE, 87),
       (347, 'Guillaume de Machaut', FALSE, 87),
       (348, 'Jean Bodel', FALSE, 87);

-- QUESTION 88
INSERT INTO questions (id, text, id_quiz)
VALUES (88, 'Quelle œuvre raconte l’amour tragique de deux amants bretons ?', 5);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (349, 'Tristan et Iseut', TRUE, 88),
       (350, 'Le Roman de Renart', FALSE, 88),
       (351, 'La Chanson de Roland', FALSE, 88),
       (352, 'Le Jeu de la Feuillée', FALSE, 88);

-- QUESTION 89
INSERT INTO questions (id, text, id_quiz)
VALUES (89, 'Quel auteur est célèbre pour ses romans arthuriens ?', 5);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (353, 'Chrétien de Troyes', TRUE, 89),
       (354, 'Jean de Meung', FALSE, 89),
       (355, 'Marie de France', FALSE, 89),
       (356, 'Adam de la Halle', FALSE, 89);

-- QUESTION 90
INSERT INTO questions (id, text, id_quiz)
VALUES (90, 'Quel est le premier grand texte en langue d''oc ?', 5);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (357, 'La Chanson de la Croisade albigeoise', TRUE, 90),
       (358, 'Le Roman de la Rose', FALSE, 90),
       (359, 'Le Jeu de Robin et Marion', FALSE, 90),
       (360, 'Le Dit de la panthère d''amour', FALSE, 90);

-- QUESTION 91
INSERT INTO questions (id, text, id_quiz)
VALUES (91, 'Quelle femme de lettres médiévale a défendu la cause des femmes ?', 5);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (361, 'Christine de Pizan', TRUE, 91),
       (362, 'Aliénor d''Aquitaine', FALSE, 91),
       (363, 'Marie de France', FALSE, 91),
       (364, 'Jeanne d''Arc', FALSE, 91);

-- QUESTION 92
INSERT INTO questions (id, text, id_quiz)
VALUES (92, 'Quel texte met en scène des miracles attribués à la Vierge ?', 5);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (365, 'Les Miracles de Notre-Dame', TRUE, 92),
       (366, 'Le Livre du Voir Dit', FALSE, 92),
       (367, 'La Farce de Maître Pathelin', FALSE, 92),
       (368, 'Le Roman de Fauvel', FALSE, 92);

-- QUESTION 93
INSERT INTO questions (id, text, id_quiz)
VALUES (93, 'Quel est le genre du "Jeu de la Feuillée" d’Adam de la Halle ?', 5);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (369, 'La comédie satirique', TRUE, 93),
       (370, 'Le roman courtois', FALSE, 93),
       (371, 'La chanson de geste', FALSE, 93),
       (372, 'La ballade', FALSE, 93);

-- QUESTION 94
INSERT INTO questions (id, text, id_quiz)
VALUES (94, 'Quel est le principal mode de transmission de la littérature médiévale ?', 5);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (373, 'La tradition orale', TRUE, 94),
       (374, 'L’imprimerie', FALSE, 94),
       (375, 'Le théâtre', FALSE, 94),
       (376, 'La gravure', FALSE, 94);

-- QUESTION 95
INSERT INTO questions (id, text, id_quiz)
VALUES (95, 'Quel est le thème central du "Roman de Renart" ?', 5);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (377, 'La ruse et la satire sociale', TRUE, 95),
       (378, 'La quête du Graal', FALSE, 95),
       (379, 'L’amour impossible', FALSE, 95),
       (380, 'Le miracle religieux', FALSE, 95);

-- QUESTION 96
INSERT INTO questions (id, text, id_quiz)
VALUES (96, 'Quel est le nom du cycle légendaire autour du roi Arthur ?', 5);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (381, 'La Matière de Bretagne', TRUE, 96),
       (382, 'La Matière de Rome', FALSE, 96),
       (383, 'La Matière de France', FALSE, 96),
       (384, 'La Matière d’Orient', FALSE, 96);

-- QUESTION 97
INSERT INTO questions (id, text, id_quiz)
VALUES (97, 'Quel est le principal sujet des "Lais" de Marie de France ?', 5);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (385, 'L’amour et l’aventure', TRUE, 97),
       (386, 'La guerre et la politique', FALSE, 97),
       (387, 'La philosophie', FALSE, 97),
       (388, 'La satire religieuse', FALSE, 97);

-- QUESTION 98
INSERT INTO questions (id, text, id_quiz)
VALUES (98, 'Quel est le nom du héros de la "Chanson de Roland" ?', 5);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (389, 'Roland', TRUE, 98),
       (390, 'Perceval', FALSE, 98),
       (391, 'Lancelot', FALSE, 98),
       (392, 'Yvain', FALSE, 98);

-- QUESTION 99
INSERT INTO questions (id, text, id_quiz)
VALUES (99, 'Quel est le rôle du jongleur dans la société médiévale ?', 5);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (393, 'Transmettre les histoires et divertir', TRUE, 99),
       (394, 'Régner sur le royaume', FALSE, 99),
       (395, 'Guider les pèlerins', FALSE, 99),
       (396, 'Construire des cathédrales', FALSE, 99);

-- QUESTION 100
INSERT INTO questions (id, text, id_quiz)
VALUES (100, 'Quel est le principal message du "Roman de la Rose" ?', 5);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (397, 'L’amour courtois et la quête amoureuse', TRUE, 100),
       (398, 'La guerre sainte', FALSE, 100),
       (399, 'La justice royale', FALSE, 100),
       (400, 'La satire des chevaliers', FALSE, 100);
