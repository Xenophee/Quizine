-- Insertion du quiz
INSERT INTO quizzes (id, title, description, category_id, theme_id, quiz_type_code, mastery_level_id)
VALUES (16, 'La langage Java : Concepts et Pratiques',
        'Ce quiz teste vos connaissances sur les concepts fondamentaux du langage Java, y compris la syntaxe, les structures de contrôle, et les bonnes pratiques de programmation.',
        null, 7, 'CLASSIC', 3);


-- QUESTION 281
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (281, 'Qu’est-ce qu’une classe en Java ?',
        'Une classe en Java est un modèle pour créer des objets. Elle définit les attributs et les méthodes que les objets créés à partir de cette classe auront.',
        'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (881, 'C''est un modèle pour créer des objets.', TRUE, 281),
       (882, 'C''est une méthode statique.', FALSE, 281),
       (883, 'C''est une variable globale.', FALSE, 281),
       (884, 'C''est un fichier de configuration.', FALSE, 281);

-- QUESTION 282
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (282, 'Quelle est la syntaxe correcte pour déclarer une variable entière en Java ?',
        'En Java, une variable entière est déclarée en utilisant le mot-clé "int". Par exemple : int nombre = 10;',
        'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (885, 'int nombre = 10;', TRUE, 282),
       (886, 'integer nombre = 10;', FALSE, 282),
       (887, 'var nombre = 10;', FALSE, 282),
       (888, 'nombre : int = 10;', FALSE, 282);

-- QUESTION 283
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (283, 'Qu’est-ce qu’une méthode en Java ?',
        'Une méthode en Java est une fonction définie à l’intérieur d’une classe. Elle peut prendre des paramètres et retourner une valeur.',
        'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (889, 'C''est une fonction définie dans une classe.', TRUE, 283),
       (890, 'C''est un type de variable.', FALSE, 283),
       (891, 'C''est un constructeur de classe.', FALSE, 283),
       (892, 'C''est un fichier de ressources.', FALSE, 283);

-- QUESTION 284
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (284, 'Quelle est la portée d’une variable déclarée à l’intérieur d’une méthode en Java ?',
        'La portée d’une variable déclarée à l’intérieur d’une méthode est limitée à cette méthode. Elle n’est pas accessible en dehors de celle-ci.',
        'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (893, 'Elle est limitée à la méthode dans laquelle elle est déclarée.', TRUE, 284),
       (894, 'Elle est accessible dans toute la classe.', FALSE, 284),
       (895, 'Elle est accessible dans tout le programme.', FALSE, 284),
       (896, 'Elle est accessible uniquement dans le bloc de code où elle est déclarée.', FALSE, 284);

-- QUESTION 285
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (285, 'Qu’est-ce qu’un constructeur en Java ?',
        'Un constructeur en Java est une méthode spéciale qui est appelée lors de la création d’un objet. Il a le même nom que la classe et n’a pas de type de retour.',
        'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (897, 'C''est une méthode spéciale appelée lors de la création d’un objet.', TRUE, 285),
       (898, 'C''est une méthode qui retourne un objet.', FALSE, 285),
       (899, 'C''est une variable de classe.', FALSE, 285),
       (900, 'C''est un type de données.', FALSE, 285);

-- QUESTION 286
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (286, 'Quelle est la différence entre une variable locale et une variable d’instance en Java ?',
        'Une variable locale est déclarée à l’intérieur d’une méthode et n’est accessible que dans cette méthode. Une variable d’instance est déclarée au niveau de la classe et est accessible par tous les objets de cette classe.',
        'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (901,
        'Une variable locale est accessible uniquement dans la méthode où elle est déclarée, tandis qu’une variable d’instance est accessible par tous les objets de la classe.',
        TRUE, 286),
       (902,
        'Une variable locale est accessible dans toute la classe, tandis qu’une variable d’instance est accessible uniquement dans la méthode où elle est déclarée.',
        FALSE, 286),
       (903, 'Une variable locale est une constante, tandis qu’une variable d’instance peut être modifiée.', FALSE,
        286),
       (904,
        'Une variable locale est déclarée avec le mot-clé "static", tandis qu’une variable d’instance ne l’est pas.',
        FALSE, 286);

-- QUESTION 287
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (287, 'Qu’est-ce qu’une exception en Java ?',
        'Une exception en Java est un événement qui se produit pendant l’exécution d’un programme et qui interrompt le flux normal des instructions. Elle peut être gérée à l’aide de blocs try-catch.',
        'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (905, 'C''est un événement qui interrompt le flux normal du programme.', TRUE, 287),
       (906, 'C''est une variable spéciale.', FALSE, 287),
       (907, 'C''est une méthode de la classe Object.', FALSE, 287),
       (908, 'C''est un type de données primitif.', FALSE, 287);

-- QUESTION 288
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (288, 'Comment peut-on gérer une exception en Java ?',
        'On peut gérer une exception en utilisant un bloc try-catch. Le code qui peut générer une exception est placé dans le bloc try, et le bloc catch contient le code pour gérer l’exception.',
        'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (909, 'En utilisant un bloc try-catch.', TRUE, 288),
       (910, 'En déclarant la variable comme "final".', FALSE, 288),
       (911, 'En utilisant le mot-clé "static".', FALSE, 288),
       (912, 'En déclarant la méthode comme "synchronized".', FALSE, 288);

-- QUESTION 289
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (289, 'Qu’est-ce qu’une boucle for en Java ?',
        'Une boucle for en Java est utilisée pour répéter un bloc de code un nombre spécifique de fois. Elle est souvent utilisée pour parcourir des tableaux ou des collections.',
        'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id, disabled_at)
VALUES (913, 'C''est une structure de contrôle qui répète un bloc de code un nombre spécifique de fois.', TRUE, 289,
        null),
       (914, 'C''est une méthode pour déclarer des variables.', FALSE, 289, null),
       (915, 'C''est un type de données.', FALSE, 289, null),
       (916, 'C''est une exception.', FALSE, 289, null),
       (917, 'C''est une classe spéciale.', FALSE, 289, CURRENT_TIMESTAMP);


-- QUESTION 290
INSERT INTO questions (id, text, answer_explanation, question_type_code, disabled_at)
VALUES (290, 'Quelle est la syntaxe correcte pour une boucle for en Java ?',
        'La syntaxe d’une boucle for en Java est : for (initialisation; condition; incrémentation) { // bloc de code }',
        'CLASSIC', CURRENT_TIMESTAMP);
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (918, 'for (int i = 0; i < 10; i++) { // bloc de code }', TRUE, 290),
       (919, 'for int i = 0; i < 10; i++ { // bloc de code }', FALSE, 290),
       (920, 'for (i = 0; i < 10; i++) { // bloc de code }', FALSE, 290),
       (921, 'for (int i : array) { // bloc de code }', FALSE, 290);


-- QUESTION 291
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (291, 'Quels sont les types de boucles disponibles en Java ?',
        'En Java, les types de boucles disponibles sont : for, while, et do-while.',
        'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id, disabled_at)
VALUES (922, 'for', TRUE, 291, null),
       (923, 'while', TRUE, 291, null),
       (924, 'switch', FALSE, 291, null),
       (925, 'stream', FALSE, 291, null),
       (926, 'map', FALSE, 291, null),
       (927, 'do-while', TRUE, 291, current_timestamp),
       (928, 'foreach', FALSE, 291, null);


-- Insertion des relations entre quiz et questions
INSERT INTO quiz_questions (quiz_id, question_id)
VALUES (16, 281),
       (16, 282),
       (16, 283),
       (16, 284),
       (16, 285),
       (16, 286),
       (16, 287),
       (16, 288),
       (16, 289),
       (16, 290),
       (16, 291);