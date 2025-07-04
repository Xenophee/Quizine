-- Insertion du quiz
INSERT INTO quizzes (id, title, description, category_id, theme_id, quiz_type_code, mastery_level_id)
VALUES  (12, 'Maîtrisez les Bases de l''Informatique',
         'Testez vos connaissances sur les concepts fondamentaux de l''informatique.',
         null, 7, 'TRUE_FALSE', 1);



-- QUESTION 221
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (221, 'Un octet est composé de 8 bits.',
        'Vrai. Un octet correspond toujours à 8 bits en informatique.', true, 'TRUE_FALSE');

-- QUESTION 222
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (222, 'Le système d’exploitation Windows a été créé par Apple.',
        'Faux. Windows est développé par Microsoft, pas par Apple.', false, 'TRUE_FALSE');

-- QUESTION 223
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (223, 'Un virus informatique est un programme malveillant.',
        'Vrai. Un virus est un logiciel conçu pour nuire à un système informatique.', true, 'TRUE_FALSE');

-- QUESTION 224
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (224, 'HTML est un langage de programmation.',
        'Faux. HTML est un langage de balisage, pas de programmation.', false, 'TRUE_FALSE');

-- QUESTION 225
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (225, 'Le cloud permet de stocker des fichiers sur Internet.',
        'Vrai. Le cloud désigne des services de stockage et de gestion de données en ligne.', true, 'TRUE_FALSE');

-- QUESTION 226
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (226, 'Un mot de passe fort contient uniquement des lettres minuscules.',
        'Faux. Un mot de passe fort doit contenir des majuscules, minuscules, chiffres et caractères spéciaux.', false, 'TRUE_FALSE');

-- QUESTION 227
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (227, 'Linux est un système d’exploitation libre et gratuit.',
        'Vrai. La plupart des distributions Linux sont libres et gratuites.', true, 'TRUE_FALSE');

-- QUESTION 228
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (228, 'Un fichier PDF peut contenir des images.',
        'Vrai. Un fichier PDF peut inclure du texte, des images et d’autres éléments multimédias.', true, 'TRUE_FALSE');

-- QUESTION 229
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (229, 'Un navigateur web sert à envoyer des emails.',
        'Faux. Un navigateur sert à naviguer sur Internet, pas à envoyer des emails directement.', false, 'TRUE_FALSE');

-- QUESTION 230
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (230, 'L’adresse IP identifie un appareil sur un réseau.',
        'Vrai. L’adresse IP sert à identifier de façon unique chaque appareil connecté à un réseau.', true, 'TRUE_FALSE');

-- QUESTION 231
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (231, 'Un smartphone ne peut pas se connecter à Internet.',
        'Faux. Les smartphones sont conçus pour accéder à Internet via Wi-Fi ou réseau mobile.', false, 'TRUE_FALSE');

-- QUESTION 232
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (232, 'Le format JPEG est utilisé pour les images.',
        'Vrai. JPEG est un format courant pour les fichiers image.', true, 'TRUE_FALSE');

-- QUESTION 233
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (233, 'Un pare-feu protège un ordinateur contre les virus.',
        'Faux. Un pare-feu filtre le trafic réseau, mais ne remplace pas un antivirus.', false, 'TRUE_FALSE');

-- QUESTION 234
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (234, 'La RAM sert à stocker les fichiers de façon permanente.',
        'Faux. La RAM est une mémoire temporaire, effacée à l’arrêt de l’ordinateur.', false, 'TRUE_FALSE');

-- QUESTION 235
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (235, 'Un logiciel open source est obligatoirement gratuit.',
        'Faux. Open source signifie que le code source est accessible, mais pas forcément gratuit.', false, 'TRUE_FALSE');

-- QUESTION 236
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (236, 'Le Bluetooth permet de connecter des appareils sans fil.',
        'Vrai. Le Bluetooth est une technologie de communication sans fil à courte portée.', true, 'TRUE_FALSE');

-- QUESTION 237
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (237, 'Un captcha sert à vérifier qu’un utilisateur est humain.',
        'Vrai. Les captchas sont utilisés pour différencier humains et robots sur Internet.', true, 'TRUE_FALSE');

-- QUESTION 238
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (238, 'Un tableau Excel ne peut contenir que du texte.',
        'Faux. Excel permet d’insérer du texte, des chiffres, des formules, des images, etc.', false, 'TRUE_FALSE');

-- QUESTION 239
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (239, 'L’icône en forme de poubelle sert à supprimer des fichiers.',
        'Vrai. La corbeille permet de supprimer ou de restaurer des fichiers sur un ordinateur.', true, 'TRUE_FALSE');

-- QUESTION 240
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (240, 'Les réseaux sociaux sont des sites pour partager des contenus.',
        'Vrai. Les réseaux sociaux permettent de publier et partager des textes, photos, vidéos, etc.', true, 'TRUE_FALSE');


-- Insertion des relations entre quiz et questions
INSERT INTO quiz_questions (quiz_id, question_id)
VALUES
(12, 221),
(12, 222),
(12, 223),
(12, 224),
(12, 225),
(12, 226),
(12, 227),
(12, 228),
(12, 229),
(12, 230),
(12, 231),
(12, 232),
(12, 233),
(12, 234),
(12, 235),
(12, 236),
(12, 237),
(12, 238),
(12, 239),
(12, 240);