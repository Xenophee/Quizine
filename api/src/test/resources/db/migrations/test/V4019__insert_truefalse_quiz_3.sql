INSERT INTO quizzes (id, title, description, category_id, theme_id, quiz_type_code, mastery_level_id)
VALUES (14, 'Testez vos Connaissances Informatiques',
            'Évaluez vos connaissances sur les concepts de base de l’informatique.',
        null, 7, 'TRUE_FALSE', 2);


-- QUESTION 261
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (261, 'La commande “ping” permet de tester la connectivité réseau entre deux machines.',
        'Vrai. “ping” envoie des paquets ICMP pour vérifier si une machine distante répond sur le réseau.', true, 'TRUE_FALSE');

-- QUESTION 262
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (262, 'Une adresse IPv4 est composée de 8 octets.',
        'Faux. Une adresse IPv4 est composée de 4 octets, soit 32 bits.', false, 'TRUE_FALSE');

-- QUESTION 263
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (263, 'Le protocole FTP permet de transférer des fichiers entre un client et un serveur.',
        'Vrai. FTP (File Transfer Protocol) sert à transférer des fichiers sur un réseau.', true, 'TRUE_FALSE');

-- QUESTION 264
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (264, 'Dans Excel, la fonction SUM permet de calculer la moyenne d’une plage de cellules.',
        'Faux. SUM additionne les valeurs, la moyenne s’obtient avec AVERAGE.', false, 'TRUE_FALSE');

-- QUESTION 265
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (265, 'Le système de fichiers NTFS est utilisé par défaut sur Windows 10.',
        'Vrai. NTFS est le système de fichiers par défaut des versions récentes de Windows.', true, 'TRUE_FALSE');

-- QUESTION 266
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (266, 'La commande “ls” sous Linux permet de lister le contenu d’un dossier.',
        'Vrai. “ls” affiche la liste des fichiers et dossiers présents dans un répertoire.', true, 'TRUE_FALSE');

-- QUESTION 267
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (267, 'Un fichier avec l’extension .exe est généralement un exécutable sous Windows.',
        'Vrai. Les fichiers .exe sont des exécutables sur les systèmes Windows.', true, 'TRUE_FALSE');

-- QUESTION 268
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (268, 'Un cookie permet de stocker des informations côté serveur.',
        'Faux. Un cookie est stocké côté client, dans le navigateur.', false, 'TRUE_FALSE');

-- QUESTION 269
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (269, 'Le protocole HTTPS chiffre les échanges entre le navigateur et le serveur.',
        'Vrai. HTTPS (HTTP Secure) utilise TLS/SSL pour chiffrer les données échangées.', true, 'TRUE_FALSE');

-- QUESTION 270
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (270, 'La mémoire cache d’un processeur sert à stocker les données de manière permanente.',
        'Faux. La mémoire cache est très rapide mais volatile, elle sert à accélérer l’accès aux données temporaires.', false, 'TRUE_FALSE');

-- QUESTION 271
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (271, 'Le langage CSS permet de structurer le contenu d’une page web.',
        'Faux. CSS sert à la mise en forme, la structure est assurée par HTML.', false, 'TRUE_FALSE');

-- QUESTION 272
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (272, 'SSH est un protocole permettant de se connecter à distance de façon sécurisée.',
        'Vrai. SSH (Secure Shell) permet l’accès sécurisé à un système distant.', true, 'TRUE_FALSE');

-- QUESTION 273
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (273, 'Un SSD offre généralement de meilleures performances qu’un disque dur mécanique (HDD).',
        'Vrai. Les SSD sont plus rapides que les HDD pour la lecture et l’écriture de données.', true, 'TRUE_FALSE');

-- QUESTION 274
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (274, 'Le langage SQL permet uniquement de lire des données dans une base.',
        'Faux. SQL permet de lire, modifier, ajouter ou supprimer des données.', false, 'TRUE_FALSE');

-- QUESTION 275
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (275, 'Le système d’exploitation Android est basé sur le noyau Linux.',
        'Vrai. Android est construit à partir du noyau Linux.', true, 'TRUE_FALSE');

-- QUESTION 276
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (276, 'Le DNS permet de transformer une adresse IP en nom de domaine.',
        'Faux. Le DNS fait l’inverse : il traduit un nom de domaine en adresse IP.', false, 'TRUE_FALSE');

-- QUESTION 277
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (277, 'La virtualisation permet d’exécuter plusieurs systèmes d’exploitation sur une même machine physique.',
        'Vrai. La virtualisation permet d’isoler plusieurs environnements sur un seul matériel.', true, 'TRUE_FALSE');

-- QUESTION 278
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (278, 'Une clé RSA de 1024 bits est aujourd’hui considérée comme suffisamment sécurisée pour tous les usages.',
        'Faux. Les clés RSA de 1024 bits sont désormais considérées comme vulnérables et il est recommandé d’utiliser au moins 2048 bits.', false, 'TRUE_FALSE');

-- QUESTION 279
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (279, 'La commande “Ctrl + Z” permet d’annuler la dernière action dans la plupart des logiciels.',
        'Vrai. “Ctrl + Z” est le raccourci standard pour annuler une action.', true, 'TRUE_FALSE');

-- QUESTION 280
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (280, 'Un fichier CSV peut contenir des images.',
        'Faux. Un fichier CSV (Comma-Separated Values) ne contient que du texte, pas d’images.', false, 'TRUE_FALSE');



-- Insertion des relations entre quiz et questions
INSERT INTO quiz_questions (quiz_id, question_id)
VALUES
(14, 261),
(14, 262),
(14, 263),
(14, 264),
(14, 265),
(14, 266),
(14, 267),
(14, 268),
(14, 269),
(14, 270),
(14, 271),
(14, 272),
(14, 273),
(14, 274),
(14, 275),
(14, 276),
(14, 277),
(14, 278),
(14, 279),
(14, 280);