-- Insertion du quiz
INSERT INTO quizzes (id, title, description, category_id, theme_id, quiz_type_code, mastery_level_id)
VALUES (5, 'Littérature médiévale : épopées, romans et fabliaux',
        'Un voyage à travers les épopées, les romans courtois et les fabliaux de la littérature médiévale.',
        10, 5, 'CLASSIC', 3);

-- QUESTION 81
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (81, 'Quel est le plus célèbre poème épique de la littérature française médiévale ?',
        'La Chanson de Roland est un poème épique qui raconte la bataille de Roncevaux et l''idéal chevaleresque.', 'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (321, 'La Chanson de Roland', TRUE, 81),
       (322, 'Le Roman de la Rose', FALSE, 81),
       (323, 'Le Livre de la Cité des Dames', FALSE, 81),
       (324, 'Tristan et Iseut', FALSE, 81);

-- QUESTION 82
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (82, 'Quel genre littéraire médiéval met en scène des aventures chevaleresques ?',
        'Le roman courtois est un genre littéraire qui met en avant les aventures et les amours des chevaliers.', 'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (325, 'Le roman courtois', TRUE, 82),
       (326, 'Le fabliau', FALSE, 82),
       (327, 'La farce', FALSE, 82),
       (328, 'La ballade', FALSE, 82);

-- QUESTION 83
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (83, 'Qui est l''auteur du "Roman de la Rose" ?',
        'Le "Roman de la Rose" est une œuvre écrite par Guillaume de Lorris et Jean de Meung, qui explore l''amour courtois.', 'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (329, 'Guillaume de Lorris et Jean de Meung', TRUE, 83),
       (330, 'Chrétien de Troyes', FALSE, 83),
       (331, 'Marie de France', FALSE, 83),
       (332, 'Rutebeuf', FALSE, 83);

-- QUESTION 84
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (84, 'Quel personnage incarne l’idéal du chevalier courtois ?',
        'Lancelot est souvent considéré comme l''archétype du chevalier courtois, célèbre pour son amour de la reine Guenièvre et ses exploits.', 'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (333, 'Lancelot', TRUE, 84),
       (334, 'Gargantua', FALSE, 84),
       (335, 'Roland', FALSE, 84),
       (336, 'Renard', FALSE, 84);

-- QUESTION 85
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (85, 'Quel animal rusé est le héros d’un célèbre recueil médiéval ?',
        'Le Roman de Renart met en scène Renart, un renard rusé qui incarne la ruse et la satire sociale.', 'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (337, 'Le renard', TRUE, 85),
       (338, 'Le loup', FALSE, 85),
       (339, 'Le lion', FALSE, 85),
       (340, 'Le cerf', FALSE, 85);

-- QUESTION 86
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (86, 'Quel est le principal thème des fabliaux ?',
        'Les fabliaux sont des récits courts et comiques qui traitent souvent de la satire sociale et des plaisanteries.', 'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (341, 'La satire sociale et les plaisanteries', TRUE, 86),
       (342, 'L’amour courtois', FALSE, 86),
       (343, 'La guerre', FALSE, 86),
       (344, 'La foi religieuse', FALSE, 86);

-- QUESTION 87
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (87, 'Quel poète est célèbre pour ses ballades mélancoliques au Moyen Âge ?',
        'François Villon est connu pour ses ballades qui expriment la mélancolie et la révolte sociale.', 'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (345, 'François Villon', TRUE, 87),
       (346, 'Rutebeuf', FALSE, 87),
       (347, 'Guillaume de Machaut', FALSE, 87),
       (348, 'Jean Bodel', FALSE, 87);

-- QUESTION 88
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (88, 'Quelle œuvre raconte l’amour tragique de deux amants bretons ?',
        'Tristan et Iseut est une légende médiévale qui raconte l''amour impossible entre Tristan et Iseut, souvent associée à la quête du Graal.', 'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (349, 'Tristan et Iseut', TRUE, 88),
       (350, 'Le Roman de Renart', FALSE, 88),
       (351, 'La Chanson de Roland', FALSE, 88),
       (352, 'Le Jeu de la Feuillée', FALSE, 88);

-- QUESTION 89
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (89, 'Quel auteur est célèbre pour ses romans arthuriens ?',
        'Chrétien de Troyes est un auteur majeur de la littérature médiévale, connu pour ses romans arthuriens qui mettent en avant les valeurs chevaleresques.', 'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (353, 'Chrétien de Troyes', TRUE, 89),
       (354, 'Jean de Meung', FALSE, 89),
       (355, 'Marie de France', FALSE, 89),
       (356, 'Adam de la Halle', FALSE, 89);

-- QUESTION 90
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (90, 'Quel est le premier grand texte en langue d''oc ?',
        'La Chanson de la Croisade albigeoise est l’un des premiers grands textes écrits en langue d''oc, relatant les événements de la croisade contre les Albigeois.', 'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (357, 'La Chanson de la Croisade albigeoise', TRUE, 90),
       (358, 'Le Roman de la Rose', FALSE, 90),
       (359, 'Le Jeu de Robin et Marion', FALSE, 90),
       (360, 'Le Dit de la panthère d''amour', FALSE, 90);

-- QUESTION 91
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (91, 'Quelle femme de lettres médiévale a défendu la cause des femmes ?',
        'Christine de Pizan est une pionnière de la littérature médiévale qui a écrit des œuvres en faveur des droits des femmes et de leur éducation.', 'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (361, 'Christine de Pizan', TRUE, 91),
       (362, 'Aliénor d''Aquitaine', FALSE, 91),
       (363, 'Marie de France', FALSE, 91),
       (364, 'Jeanne d''Arc', FALSE, 91);

-- QUESTION 92
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (92, 'Quel texte met en scène des miracles attribués à la Vierge ?',
        'Les Miracles de Notre-Dame est une collection de récits qui relatent les miracles attribués à la Vierge Marie, illustrant la foi populaire médiévale.', 'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (365, 'Les Miracles de Notre-Dame', TRUE, 92),
       (366, 'Le Livre du Voir Dit', FALSE, 92),
       (367, 'La Farce de Maître Pathelin', FALSE, 92),
       (368, 'Le Roman de Fauvel', FALSE, 92);

-- QUESTION 93
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (93, 'Quel est le genre du "Jeu de la Feuillée" d’Adam de la Halle ?',
        'Le "Jeu de la Feuillée" est une comédie satirique qui critique les mœurs de son époque, mêlant farce et satire sociale.', 'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (369, 'La comédie satirique', TRUE, 93),
       (370, 'Le roman courtois', FALSE, 93),
       (371, 'La chanson de geste', FALSE, 93),
       (372, 'La ballade', FALSE, 93);

-- QUESTION 94
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (94, 'Quel est le principal mode de transmission de la littérature médiévale ?',
        'La tradition orale a joué un rôle crucial dans la transmission des récits et des légendes médiévales avant l''invention de l''imprimerie.', 'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (373, 'La tradition orale', TRUE, 94),
       (374, 'L’imprimerie', FALSE, 94),
       (375, 'Le théâtre', FALSE, 94),
       (376, 'La gravure', FALSE, 94);

-- QUESTION 95
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (95, 'Quel est le thème central du "Roman de Renart" ?',
        'Le "Roman de Renart" est centré sur la ruse et la satire sociale, mettant en scène des animaux anthropomorphes pour critiquer les mœurs de la société médiévale.', 'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (377, 'La ruse et la satire sociale', TRUE, 95),
       (378, 'La quête du Graal', FALSE, 95),
       (379, 'L’amour impossible', FALSE, 95),
       (380, 'Le miracle religieux', FALSE, 95);

-- QUESTION 96
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (96, 'Quel est le nom du cycle légendaire autour du roi Arthur ?',
        'La Matière de Bretagne est le cycle légendaire qui regroupe les récits autour du roi Arthur, des chevaliers de la Table Ronde et de la quête du Graal.', 'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (381, 'La Matière de Bretagne', TRUE, 96),
       (382, 'La Matière de Rome', FALSE, 96),
       (383, 'La Matière de France', FALSE, 96),
       (384, 'La Matière d’Orient', FALSE, 96);

-- QUESTION 97
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (97, 'Quel est le principal sujet des "Lais" de Marie de France ?',
        'Les "Lais" de Marie de France sont des récits poétiques qui traitent principalement de l’amour et de l’aventure, souvent avec une touche de fantastique.', 'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (385, 'L’amour et l’aventure', TRUE, 97),
       (386, 'La guerre et la politique', FALSE, 97),
       (387, 'La philosophie', FALSE, 97),
       (388, 'La satire religieuse', FALSE, 97);

-- QUESTION 98
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (98, 'Quel est le nom du héros de la "Chanson de Roland" ?',
        'Roland est le héros de la "Chanson de Roland", un poème épique qui raconte la bataille de Roncevaux et l''idéal chevaleresque.', 'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (389, 'Roland', TRUE, 98),
       (390, 'Perceval', FALSE, 98),
       (391, 'Lancelot', FALSE, 98),
       (392, 'Yvain', FALSE, 98);

-- QUESTION 99
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (99, 'Quel est le rôle du jongleur dans la société médiévale ?',
        'Le jongleur était un artiste itinérant qui divertissait le public avec des histoires, de la musique et des acrobaties, jouant un rôle important dans la culture populaire médiévale.', 'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (393, 'Transmettre les histoires et divertir', TRUE, 99),
       (394, 'Régner sur le royaume', FALSE, 99),
       (395, 'Guider les pèlerins', FALSE, 99),
       (396, 'Construire des cathédrales', FALSE, 99);

-- QUESTION 100
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (100, 'Quel est le principal message du "Roman de la Rose" ?',
        'Le "Roman de la Rose" explore les thèmes de l’amour courtois, de la quête amoureuse et des relations humaines, tout en critiquant les conventions sociales de l’époque.', 'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (397, 'L’amour courtois et la quête amoureuse', TRUE, 100),
       (398, 'La guerre sainte', FALSE, 100),
       (399, 'La justice royale', FALSE, 100),
       (400, 'La satire des chevaliers', FALSE, 100);



-- Insertion des relations entre quiz et questions
INSERT INTO quiz_questions (quiz_id, question_id)
VALUES (5, 81),
       (5, 82),
       (5, 83),
       (5, 84),
       (5, 85),
       (5, 86),
       (5, 87),
       (5, 88),
       (5, 89),
       (5, 90),
       (5, 91),
       (5, 92),
       (5, 93),
       (5, 94),
       (5, 95),
       (5, 96),
       (5, 97),
       (5, 98),
       (5, 99),
       (5, 100);