INSERT INTO quizzes (id, title, description, category_id, theme_id, quiz_type_code, mastery_level_id)
VALUES (17, 'Le cinéma : Histoire et Techniques',
        'Testez vos connaissances sur l’histoire du cinéma, les techniques de réalisation et les grands classiques du septième art.',
        2, 2, 'TRUE_FALSE', 2);

-- QUESTION 292
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (292, 'Le premier film de l’histoire du cinéma est "La Sortie de l’Usine Lumière à Lyon".',
        'Vrai. Ce court-métrage de 1895 est considéré comme le premier film de l’histoire.', true, 'TRUE_FALSE');

-- QUESTION 293
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (293, 'Le cinéma muet a été remplacé par le cinéma parlant dans les années 1920.',
        'Vrai. Le passage au cinéma parlant a eu lieu à la fin des années 1920, marquant la fin de l’ère du cinéma muet.', true, 'TRUE_FALSE');

-- QUESTION 294
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (294, 'Le film "Citizen Kane" a été réalisé par Orson Welles en 1941.',
        'Vrai. "Citizen Kane" est souvent considéré comme l’un des plus grands films de tous les temps et a été réalisé par Orson Welles.', true, 'TRUE_FALSE');

-- QUESTION 295
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (295, 'Le terme "Plan-séquence" désigne une technique de montage cinématographique.',
        'Faux. Un plan-séquence est une prise de vue continue sans coupure, tandis que le montage consiste à assembler plusieurs plans.', false, 'TRUE_FALSE');

-- QUESTION 296
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (296, 'Le film "Avatar" de James Cameron a été le premier à dépasser les 2 milliards de dollars au box-office.',
        'Faux. "Titanic", également réalisé par James Cameron, a été le premier film à dépasser les 2 milliards de dollars.', false, 'TRUE_FALSE');

-- QUESTION 297
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (297, 'Le festival de Cannes est le plus prestigieux festival de cinéma au monde.',
        'Vrai. Le festival de Cannes est reconnu internationalement pour sa sélection de films et ses récompenses.', true, 'TRUE_FALSE');

-- QUESTION 298
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (298, 'Le terme "Blockbuster" désigne un film à gros budget qui rencontre un grand succès commercial.',
        'Vrai. Un blockbuster est généralement un film à gros budget qui attire un large public et génère des revenus importants.', true, 'TRUE_FALSE');

-- QUESTION 299
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (299, 'Le cinéma numérique a remplacé le cinéma analogique dans les années 2000.',
        'Vrai. Le passage au cinéma numérique a eu lieu dans les années 2000, remplaçant progressivement les projecteurs analogiques.', true, 'TRUE_FALSE');

-- QUESTION 300
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (300, 'Les Oscars sont décernés chaque année par l’Academy of Motion Picture Arts and Sciences.',
        'Vrai. Les Oscars, ou Academy Awards, sont remis chaque année par l’Academy of Motion Picture Arts and Sciences pour récompenser les meilleurs films et professionnels du cinéma.', true, 'TRUE_FALSE');


-- Insertion des relations entre quiz et questions
INSERT INTO quiz_questions (quiz_id, question_id)
VALUES
(17, 292),
(17, 293),
(17, 294),
(17, 295),
(17, 296),
(17, 297),
(17, 298),
(17, 299),
(17, 300);