-- Insertion du quiz
INSERT INTO quizzes (id, title, description, category_id, theme_id, quiz_type_code, mastery_level_id)
VALUES (3, 'Littérature gothique : motifs, personnages et influences',
        'Un voyage dans l’univers sombre et mystérieux de la littérature gothique, explorant ses motifs, personnages emblématiques et influences culturelles.',
        6, 5, 'CLASSIC', 3);

-- QUESTION 41
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (41, 'Quel roman gothique met en scène la famille Usher et une mystérieuse maison ?',
        'La Chute de la maison Usher est un roman emblématique du genre gothique, écrit par Edgar Allan Poe. Il explore les thèmes de la décadence, de la folie et de la mort à travers l’histoire d’une famille maudite et d’une maison hantée.', 'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (161, 'La Chute de la maison Usher', TRUE, 41),
       (162, 'Le Moine', FALSE, 41),
       (163, 'Frankenstein', FALSE, 41),
       (164, 'Le Château d''Otrante', FALSE, 41);

-- QUESTION 42
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (42, 'Quel personnage féminin est souvent victime dans le roman gothique ?',
        'Dans la littérature gothique, la demoiselle en détresse est un personnage classique, souvent représentée comme une jeune femme vulnérable, enlevée ou menacée par des forces maléfiques. Ce motif est récurrent dans de nombreux romans gothiques.', 'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (165, 'La demoiselle en détresse', TRUE, 42),
       (166, 'La sorcière', FALSE, 42),
       (167, 'La reine', FALSE, 42),
       (168, 'La servante', FALSE, 42);

-- QUESTION 43
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (43, 'Quel poème d’Edgar Allan Poe est emblématique de l’atmosphère gothique ?',
        'Le Corbeau est un poème célèbre d’Edgar Allan Poe, publié en 1845. Il évoque la mélancolie, la perte et le surnaturel, caractéristiques de la littérature gothique. Le poème raconte la visite d’un mystérieux corbeau qui apporte un message sinistre au narrateur.', 'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (169, 'Le Corbeau', TRUE, 43),
       (170, 'Ode à un rossignol', FALSE, 43),
       (171, 'L’Albatros', FALSE, 43),
       (172, 'La Belle Dame sans Merci', FALSE, 43);

-- QUESTION 44
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (44, 'Quel décor naturel est souvent associé à la littérature gothique ?',
        'La forêt sombre est un décor classique de la littérature gothique, symbolisant le mystère, le danger et l’inconnu. Elle est souvent utilisée pour créer une atmosphère inquiétante et pour représenter les peurs intérieures des personnages.', 'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (173, 'La forêt sombre', TRUE, 44),
       (174, 'La plage', FALSE, 44),
       (175, 'Le désert', FALSE, 44),
       (176, 'La prairie', FALSE, 44);

-- QUESTION 45
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (45, 'Quel sentiment domine l’ambiance des romans gothiques ?',
        'L’angoisse est un sentiment central dans la littérature gothique, souvent utilisé pour créer une atmosphère de tension et de suspense. Les personnages sont fréquemment confrontés à des situations terrifiantes et à des forces mystérieuses qui exacerbent leur peur.', 'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (177, 'L’angoisse', TRUE, 45),
       (178, 'La joie', FALSE, 45),
       (179, 'L’espoir', FALSE, 45),
       (180, 'La nostalgie', FALSE, 45);

-- QUESTION 46
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (46, 'Quel roman gothique anglais met en scène un pacte avec le diable ?',
        'Le Moine est un roman gothique écrit par Matthew Gregory Lewis, publié en 1796. Il raconte l’histoire d’un moine qui succombe à la tentation et conclut un pacte avec le diable, explorant les thèmes de la corruption, de la damnation et de la rédemption.', 'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (181, 'Le Moine', TRUE, 46),
       (182, 'Dracula', FALSE, 46),
       (183, 'Northanger Abbey', FALSE, 46),
       (184, 'Le Portrait de Dorian Gray', FALSE, 46);

-- QUESTION 47
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (47, 'Quel personnage est une figure récurrente du roman gothique ?',
        'Le spectre est une figure emblématique du roman gothique, souvent représenté comme un fantôme ou une apparition surnaturelle. Il incarne les thèmes de la mort, de la culpabilité et des secrets inavoués, et joue un rôle crucial dans l’atmosphère mystérieuse et inquiétante des récits gothiques.', 'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (185, 'Le spectre', TRUE, 47),
       (186, 'Le détective', FALSE, 47),
       (187, 'Le pirate', FALSE, 47),
       (188, 'Le chevalier', FALSE, 47);

-- QUESTION 48
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (48, 'Quel roman gothique a pour cadre principal l’abbaye de Northanger ?',
        'Northanger Abbey est un roman de Jane Austen, publié en 1817. Bien qu’il soit une satire des romans gothiques, il met en scène une jeune héroïne qui s’imagine vivre dans un monde gothique, explorant les thèmes de l’imagination et de la réalité.', 'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (189, 'Northanger Abbey', TRUE, 48),
       (190, 'Le Moine', FALSE, 48),
       (191, 'Les Mystères d''Udolphe', FALSE, 48),
       (192, 'Vathek', FALSE, 48);

-- QUESTION 49
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (49, 'Quel écrivain français du XIXe siècle a été influencé par le gothique anglais ?',
        'Charles Nodier est un écrivain français du XIXe siècle qui a été influencé par la littérature gothique anglaise. Il a écrit plusieurs œuvres qui intègrent des éléments gothiques, contribuant à populariser le genre en France.', 'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (193, 'Charles Nodier', TRUE, 49),
       (194, 'Victor Hugo', FALSE, 49),
       (195, 'Gustave Flaubert', FALSE, 49),
       (196, 'Émile Zola', FALSE, 49);

-- QUESTION 50
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (50, 'Quel roman gothique est centré sur la malédiction d’une famille ?',
        'La Chute de la maison Usher est un roman d’Edgar Allan Poe qui explore la malédiction d’une famille maudite, symbolisée par la maison elle-même. Il traite des thèmes de la folie, de la mort et de l’hérédité, caractéristiques du genre gothique.', 'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (197, 'La Chute de la maison Usher', TRUE, 50),
       (198, 'Frankenstein', FALSE, 50),
       (199, 'Le Moine', FALSE, 50),
       (200, 'Vathek', FALSE, 50);

-- QUESTION 51
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (51, 'Quel rôle joue la météo dans la littérature gothique ?',
        'La météo, en particulier les tempêtes et les nuits sombres, joue un rôle crucial dans la création de l’atmosphère gothique. Elle accentue le suspense et la peur, renforçant l’ambiance mystérieuse et inquiétante des récits.', 'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (201, 'Elle accentue le suspense et la peur', TRUE, 51),
       (202, 'Elle apporte de la légèreté', FALSE, 51),
       (203, 'Elle est toujours ensoleillée', FALSE, 51),
       (204, 'Elle n’a aucun impact', FALSE, 51);

-- QUESTION 52
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (52, 'Quel roman gothique est inspiré par les légendes orientales ?',
        'Vathek est un roman gothique écrit par William Beckford, publié en 1786. Il s’inspire des légendes orientales et raconte l’histoire d’un calife qui cherche à acquérir des pouvoirs surnaturels, explorant les thèmes de l’ambition, de la décadence et du surnaturel.', 'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (205, 'Vathek', TRUE, 52),
       (206, 'Le Château d''Otrante', FALSE, 52),
       (207, 'Dracula', FALSE, 52),
       (208, 'Frankenstein', FALSE, 52);

-- QUESTION 53
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (53, 'Quel sentiment est souvent ressenti par les héroïnes gothiques ?',
        'Dans la littérature gothique, la terreur est un sentiment prédominant, souvent ressenti par les héroïnes confrontées à des situations effrayantes et mystérieuses. Ce sentiment est central pour créer l’atmosphère inquiétante et captivante des récits gothiques.', 'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (209, 'La terreur', TRUE, 53),
       (210, 'L’ennui', FALSE, 53),
       (211, 'La fierté', FALSE, 53),
       (212, 'L’indifférence', FALSE, 53);

-- QUESTION 54
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (54, 'Quel animal est fréquemment associé au surnaturel dans le gothique ?',
        'Dans la littérature gothique, le corbeau est souvent associé au surnaturel et à la mort. Il est un symbole de présage et de mystère, apparaissant fréquemment dans les récits pour accentuer l’atmosphère sombre et inquiétante.', 'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (213, 'Le corbeau', TRUE, 54),
       (214, 'Le cheval', FALSE, 54),
       (215, 'Le chien', FALSE, 54),
       (216, 'Le serpent', FALSE, 54);

-- QUESTION 55
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (55, 'Quel écrivain britannique a écrit de nombreux contes gothiques pour enfants ?',
        'M. R. James est un écrivain britannique connu pour ses contes gothiques, souvent destinés à un public plus jeune. Ses histoires sont caractérisées par une atmosphère mystérieuse et des éléments surnaturels, captivant les lecteurs avec des récits d’horreur et de suspense.', 'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (217, 'M. R. James', TRUE, 55),
       (218, 'Jane Austen', FALSE, 55),
       (219, 'Oscar Wilde', FALSE, 55),
       (220, 'Emily Brontë', FALSE, 55);

-- QUESTION 56
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (56, 'Quel motif symbolise la frontière entre le monde des vivants et celui des morts ?',
        'Dans la littérature gothique, le cimetière est un motif récurrent qui symbolise la frontière entre le monde des vivants et celui des morts. Il est souvent le lieu de rencontres surnaturelles et d’apparitions spectrales, renforçant l’atmosphère sombre et mystérieuse des récits.', 'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (221, 'Le cimetière', TRUE, 56),
       (222, 'Le théâtre', FALSE, 56),
       (223, 'La bibliothèque', FALSE, 56),
       (224, 'La taverne', FALSE, 56);

-- QUESTION 57
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (57, 'Quel roman gothique met en scène un tableau maléfique ?',
        'Le Portrait de Dorian Gray est un roman d’Oscar Wilde publié en 1890. Il raconte l’histoire d’un jeune homme dont le portrait vieillit à sa place, reflétant ses actions immorales et sa décadence. Ce roman explore les thèmes de la beauté, de l’art et de la moralité, caractéristiques du genre gothique.', 'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (225, 'Le Portrait de Dorian Gray', TRUE, 57),
       (226, 'Frankenstein', FALSE, 57),
       (227, 'Le Moine', FALSE, 57),
       (228, 'Les Mystères d''Udolphe', FALSE, 57);

-- QUESTION 58
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (58, 'Quel élément de décor est souvent le théâtre d’apparitions spectrales ?',
        'Dans la littérature gothique, le couloir sombre est souvent le théâtre d’apparitions spectrales et de rencontres mystérieuses. Il symbolise l’inconnu et le danger, créant une atmosphère inquiétante et propice aux événements surnaturels.', 'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (229, 'Le couloir sombre', TRUE, 58),
       (230, 'Le grenier lumineux', FALSE, 58),
       (231, 'La cuisine', FALSE, 58),
       (232, 'Le jardin potager', FALSE, 58);

-- QUESTION 59
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (59, 'Quel roman gothique a pour thème la quête de l’immortalité ?',
        'Le Portrait de Dorian Gray est un roman d’Oscar Wilde qui aborde le thème de la quête de l’immortalité à travers le personnage de Dorian Gray, dont le portrait vieillit à sa place. Ce roman explore les conséquences de la recherche de la beauté éternelle et de l’immoralité, caractéristiques du genre gothique.', 'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (233, 'Le Portrait de Dorian Gray', TRUE, 59),
       (234, 'Le Moine', FALSE, 59),
       (235, 'Dracula', FALSE, 59),
       (236, 'Northanger Abbey', FALSE, 59);

-- QUESTION 60
INSERT INTO questions (id, text, answer_explanation, question_type_code)
VALUES (60, 'Quel est l’effet recherché par l’utilisation du surnaturel dans le roman gothique ?',
        'Dans la littérature gothique, l’utilisation du surnaturel vise à troubler et fasciner le lecteur, en créant une atmosphère de mystère et de suspense. Les éléments surnaturels sont souvent utilisés pour explorer les peurs intérieures des personnages et pour renforcer l’ambiance inquiétante des récits.', 'CLASSIC');
INSERT INTO classic_answers (id, text, is_correct, question_id)
VALUES (237, 'Troubler et fasciner le lecteur', TRUE, 60),
       (238, 'Rassurer le lecteur', FALSE, 60),
       (239, 'Expliquer la science', FALSE, 60),
       (240, 'Faire rire', FALSE, 60);


-- Insertion des relations entre quiz et questions
INSERT INTO quiz_questions (quiz_id, question_id)
VALUES (3, 41),
       (3, 42),
       (3, 43),
       (3, 44),
       (3, 45),
       (3, 46),
       (3, 47),
       (3, 48),
       (3, 49),
       (3, 50),
       (3, 51),
       (3, 52),
       (3, 53),
       (3, 54),
       (3, 55),
       (3, 56),
       (3, 57),
       (3, 58),
       (3, 59),
       (3, 60);