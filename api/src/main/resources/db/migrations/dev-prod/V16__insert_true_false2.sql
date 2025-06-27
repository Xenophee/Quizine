-- Insertion du quiz
INSERT INTO quizzes (id, title, description, category_id, theme_id, quiz_type_code, mastery_level_id)
VALUES (13, 'Testez vos Connaissances Techniques en informatique',
            'Évaluez vos compétences en informatique avec ce quiz technique.',
        null, 7, 'TRUE_FALSE', 3);


-- QUESTION 241
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (241, 'Le protocole BGP est principalement utilisé pour le routage interne (IGP) dans les réseaux d’entreprise.',
        'Faux. BGP (Border Gateway Protocol) est un protocole de routage externe (EGP) utilisé principalement entre systèmes autonomes sur Internet.', false, 'TRUE_FALSE');

-- QUESTION 242
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (242, 'Le modèle OSI comporte 7 couches, dont la couche session située entre la couche transport et la couche présentation.',
        'Vrai. La couche session (5) se situe entre la couche transport (4) et présentation (6) dans le modèle OSI.', true, 'TRUE_FALSE');

-- QUESTION 243
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (243, 'En C, la fonction malloc() initialise à zéro la mémoire allouée.',
        'Faux. malloc() alloue de la mémoire non initialisée. Pour une mémoire initialisée à zéro, il faut utiliser calloc().', false, 'TRUE_FALSE');

-- QUESTION 244
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (244, 'Le RAID 10 combine à la fois la tolérance aux pannes du RAID 1 et la performance du RAID 0.',
        'Vrai. RAID 10 (ou 1+0) combine la mise en miroir (RAID 1) et le striping (RAID 0), offrant tolérance aux pannes et performance.', true, 'TRUE_FALSE');

-- QUESTION 245
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (245, 'L’algorithme de chiffrement AES est vulnérable aux attaques par force brute avec des clés de 256 bits.',
        'Faux. AES-256 est considéré comme extrêmement sûr contre les attaques par force brute avec l’état actuel de la technologie.', false, 'TRUE_FALSE');

-- QUESTION 246
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (246, 'En SQL, la clause HAVING s’utilise uniquement avec GROUP BY.',
        'Vrai. HAVING filtre les résultats d’un GROUP BY, contrairement à WHERE qui filtre avant l’agrégation.', true, 'TRUE_FALSE');

-- QUESTION 247
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (247, 'L’adresse IPv6 fe80::/10 est une adresse de lien local (link-local).',
        'Vrai. Les adresses IPv6 commençant par fe80::/10 sont réservées au lien local.', true, 'TRUE_FALSE');

-- QUESTION 248
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (248, 'Le garbage collector de la JVM garantit l’absence totale de fuite mémoire dans une application Java.',
        'Faux. Le garbage collector aide à gérer la mémoire, mais des fuites mémoire logiques peuvent subsister si des références inutiles sont conservées.', false, 'TRUE_FALSE');

-- QUESTION 249
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (249, 'Le protocole HTTPS chiffre à la fois les données et les adresses IP lors du transport.',
        'Faux. HTTPS chiffre les données échangées mais pas les adresses IP, qui restent visibles au niveau du réseau.', false, 'TRUE_FALSE');

-- QUESTION 250
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (250, 'Le principe ACID d’une base de données garantit notamment l’isolation des transactions.',
        'Vrai. ACID signifie Atomicité, Cohérence, Isolation, Durabilité.', true, 'TRUE_FALSE');

-- QUESTION 251
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (251, 'En Python, une fonction décorée avec @staticmethod peut accéder à l’attribut self.',
        'Faux. @staticmethod ne reçoit ni self ni cls et ne peut donc accéder aux attributs d’instance.', false, 'TRUE_FALSE');

-- QUESTION 252
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (252, 'L’algorithme SHA-1 est toujours recommandé pour les signatures numériques en 2025.',
        'Faux. SHA-1 est obsolète et vulnérable aux collisions, il n’est plus recommandé pour la sécurité.', false, 'TRUE_FALSE');

-- QUESTION 253
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (253, 'L’utilisation de Docker garantit l’isolation complète du noyau pour chaque conteneur.',
        'Faux. Les conteneurs partagent le même noyau ; seule la virtualisation complète (VM) offre une isolation totale du noyau.', false, 'TRUE_FALSE');

-- QUESTION 254
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (254, 'Un commit Git peut être annulé avec la commande git revert.',
        'Vrai. git revert crée un nouveau commit qui annule les modifications du commit ciblé.', true, 'TRUE_FALSE');

-- QUESTION 255
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (255, 'L’algorithme de Dijkstra permet de calculer le plus court chemin dans un graphe pondéré sans arêtes négatives.',
        'Vrai. Dijkstra fonctionne uniquement si toutes les arêtes ont des poids non négatifs.', true, 'TRUE_FALSE');

-- QUESTION 256
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (256, 'L’instruction SELECT ... FOR UPDATE en SQL bloque les lignes sélectionnées contre toute lecture concurrente.',
        'Faux. FOR UPDATE bloque les écritures concurrentes, mais pas les lectures.', false, 'TRUE_FALSE');

-- QUESTION 257
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (257, 'Le protocole DNSSEC ajoute une couche de sécurité au DNS en signant les enregistrements DNS.',
        'Vrai. DNSSEC permet de vérifier l’authenticité des enregistrements DNS grâce à la signature numérique.', true, 'TRUE_FALSE');

-- QUESTION 258
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (258, 'L’algorithme de tri rapide (quicksort) a une complexité moyenne en O(n log n).',
        'Vrai. La complexité moyenne de quicksort est O(n log n), mais le pire cas est O(n²).', true, 'TRUE_FALSE');

-- QUESTION 259
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (259, 'En IPv4, une adresse de classe C commence toujours par 192.',
        'Faux. Les adresses de classe C vont de 192.0.0.0 à 223.255.255.255, donc pas toutes ne commencent par 192.', false, 'TRUE_FALSE');

-- QUESTION 260
INSERT INTO questions (id, text, answer_explanation, answer_if_true_false, question_type_code)
VALUES (260, 'Une attaque XSS (Cross Site Scripting) permet d’exécuter du code JavaScript malveillant dans le navigateur de la victime.',
        'Vrai. XSS permet à un attaquant d’injecter et d’exécuter du code JavaScript dans le navigateur d’un utilisateur.', true, 'TRUE_FALSE');



-- Insertion des relations entre quiz et questions
INSERT INTO quiz_questions (quiz_id, question_id)
VALUES
(13, 241),
(13, 242),
(13, 243),
(13, 244),
(13, 245),
(13, 246),
(13, 247),
(13, 248),
(13, 249),
(13, 250),
(13, 251),
(13, 252),
(13, 253),
(13, 254),
(13, 255),
(13, 256),
(13, 257),
(13, 258),
(13, 259),
(13, 260);