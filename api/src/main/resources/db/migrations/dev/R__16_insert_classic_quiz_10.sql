-- Insertion du quiz
INSERT INTO quizzes (id, title, description, category_id, theme_id, quiz_type_code, mastery_level_id, created_at,
                     updated_at, disabled_at)
VALUES (10, 'Philosophie allemande : courants, penseurs et concepts',
        'Un voyage à travers les courants philosophiques allemands, de Kant à Nietzsche, en passant par Hegel et Marx.',
        7, 4, 'CLASSIC', 1,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)

ON CONFLICT (id) DO UPDATE SET title            = EXCLUDED.title,
                               description      = EXCLUDED.description,
                               category_id      = EXCLUDED.category_id,
                               theme_id         = EXCLUDED.theme_id,
                               mastery_level_id = EXCLUDED.mastery_level_id,
                               created_at       = EXCLUDED.created_at,
                               updated_at       = EXCLUDED.updated_at,
                               disabled_at      = EXCLUDED.disabled_at;

-- QUESTION 181
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (181, 'Quel philosophe est considéré comme le fondateur du criticisme ?',
        'Immanuel Kant est le fondateur du criticisme, une doctrine qui affirme que la connaissance doit être limitée par les capacités de la raison humaine.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (721, 'Immanuel Kant', TRUE, 181, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (722, 'Friedrich Nietzsche', FALSE, 181, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (723, 'Georg Wilhelm Friedrich Hegel', FALSE, 181, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (724, 'Arthur Schopenhauer', FALSE, 181, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 182
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (182, 'Quel courant philosophique regroupe Fichte, Schelling et Hegel ?',
        'L’idéalisme allemand est un courant philosophique qui comprend notamment Fichte, Schelling et Hegel, centré sur l’idée que la réalité est fondamentalement liée à la pensée.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (725, 'L’idéalisme allemand', TRUE, 182, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (726, 'Le positivisme', FALSE, 182, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (727, 'L’existentialisme', FALSE, 182, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (728, 'Le matérialisme', FALSE, 182, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 183
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (183, 'Quel philosophe a écrit "La Phénoménologie de l’Esprit" ?',
        '"La Phénoménologie de l’Esprit" est une œuvre majeure de Hegel, où il explore la progression de la conscience vers le savoir absolu.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (729, 'Georg Wilhelm Friedrich Hegel', TRUE, 183, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (730, 'Karl Marx', FALSE, 183, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (731, 'Martin Heidegger', FALSE, 183, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (732, 'Friedrich Nietzsche', FALSE, 183, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 184
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (184, 'Quel philosophe allemand est célèbre pour sa critique de la métaphysique et du nihilisme ?',
        'Nietzsche a profondément critiqué la métaphysique occidentale et introduit la notion de nihilisme, liée à la perte de sens dans la modernité.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (733, 'Friedrich Nietzsche', TRUE, 184, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (734, 'Immanuel Kant', FALSE, 184, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (735, 'Edmund Husserl', FALSE, 184, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (736, 'Theodor W. Adorno', FALSE, 184, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 185
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (185, 'Quel concept central Kant introduit-il dans sa "Critique de la raison pure" ?',
        'Dans la "Critique de la raison pure", Kant introduit les catégories a priori comme structures fondamentales de la pensée qui rendent l’expérience possible.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (737, 'Les catégories a priori', TRUE, 185, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (738, 'La volonté de puissance', FALSE, 185, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (739, 'L’aliénation', FALSE, 185, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (740, 'La dialectique négative', FALSE, 185, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 186
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (186, 'Quel philosophe est à l’origine du concept de "volonté de puissance" ?',
        'La "volonté de puissance" est un concept central de la philosophie de Nietzsche, exprimant une force fondamentale animant la vie et la création de valeurs.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (741, 'Friedrich Nietzsche', TRUE, 186, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (742, 'Arthur Schopenhauer', FALSE, 186, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (743, 'Karl Marx', FALSE, 186, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (744, 'Martin Heidegger', FALSE, 186, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 187
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (187, 'Quel philosophe est le fondateur de la phénoménologie ?',
        'Edmund Husserl est considéré comme le fondateur de la phénoménologie, une méthode philosophique centrée sur l’étude des structures de la conscience.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (745, 'Edmund Husserl', TRUE, 187, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (746, 'Martin Heidegger', FALSE, 187, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (747, 'Ludwig Wittgenstein', FALSE, 187, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (748, 'Jürgen Habermas', FALSE, 187, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 188
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (188, 'Quel philosophe a développé la notion de "dialectique du maître et de l’esclave" ?',
        'Hegel développe cette notion dans sa "Phénoménologie de l’Esprit" pour illustrer la dynamique de reconnaissance et de développement de la conscience de soi.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (749, 'Georg Wilhelm Friedrich Hegel', TRUE, 188, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (750, 'Karl Marx', FALSE, 188, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (751, 'Immanuel Kant', FALSE, 188, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (752, 'Arthur Schopenhauer', FALSE, 188, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 189
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (189, 'Quel penseur allemand est considéré comme le fondateur du matérialisme historique ?',
        'Karl Marx est le fondateur du matérialisme historique, une méthode d’analyse des sociétés basée sur les conditions matérielles et économiques.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (753, 'Karl Marx', TRUE, 189, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (754, 'Friedrich Nietzsche', FALSE, 189, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (755, 'Martin Heidegger', FALSE, 189, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (756, 'Edmund Husserl', FALSE, 189, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 190
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (190, 'Quel philosophe a influencé la psychanalyse par sa réflexion sur l’inconscient ?',
        'Arthur Schopenhauer, avant Freud, a exploré l’idée d’un inconscient irrationnel à l’œuvre dans la volonté humaine.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (757, 'Arthur Schopenhauer', TRUE, 190, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (758, 'Immanuel Kant', FALSE, 190, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (759, 'Friedrich Nietzsche', FALSE, 190, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (760, 'Jürgen Habermas', FALSE, 190, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 191
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (191, 'Quel philosophe a écrit "L’Être et le Temps" ?',
        '"L’Être et le Temps" est l’œuvre majeure de Martin Heidegger, dans laquelle il développe une ontologie fondamentale centrée sur la question de l’être.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (761, 'Martin Heidegger', TRUE, 191, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (762, 'Edmund Husserl', FALSE, 191, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (763, 'Walter Benjamin', FALSE, 191, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (764, 'Karl Jaspers', FALSE, 191, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 192
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (192, 'Quel courant philosophique est associé à Adorno et Horkheimer ?',
        'Adorno et Horkheimer sont des figures centrales de l’École de Francfort, connue pour sa "théorie critique" de la société moderne.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (765, 'L’École de Francfort', TRUE, 192, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (766, 'L’idéalisme allemand', FALSE, 192, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (767, 'La phénoménologie', FALSE, 192, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (768, 'Le positivisme logique', FALSE, 192, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 193
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (193, 'Quel philosophe est célèbre pour sa réflexion sur la modernité et la "raison communicationnelle" ?',
        'Jürgen Habermas a développé le concept de "raison communicationnelle", pierre angulaire de sa critique de la modernité et de la démocratie.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (769, 'Jürgen Habermas', TRUE, 193, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (770, 'Walter Benjamin', FALSE, 193, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (771, 'Hannah Arendt', FALSE, 193, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (772, 'Karl Marx', FALSE, 193, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 194
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (194, 'Quel philosophe a introduit la notion de "monde comme volonté et comme représentation" ?',
        'Arthur Schopenhauer développe cette idée selon laquelle le monde est d’une part représentation (pour le sujet, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null), et d’autre part volonté irrationnelle.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (773, 'Arthur Schopenhauer', TRUE, 194, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (774, 'Friedrich Nietzsche', FALSE, 194, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (775, 'Immanuel Kant', FALSE, 194, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (776, 'Georg Wilhelm Friedrich Hegel', FALSE, 194, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 195
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (195, 'Quel philosophe a développé la "théorie critique" ?',
        'Theodor W. Adorno, avec l’École de Francfort, a développé la "théorie critique", qui vise à analyser et transformer les structures sociales injustes.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (777, 'Theodor W. Adorno', TRUE, 195, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (778, 'Martin Heidegger', FALSE, 195, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (779, 'Friedrich Nietzsche', FALSE, 195, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (780, 'Ludwig Wittgenstein', FALSE, 195, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 196
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (196, 'Quel philosophe a écrit "La naissance de la tragédie" ?',
        '"La Naissance de la tragédie" est une œuvre de jeunesse de Nietzsche, dans laquelle il analyse la culture grecque et le rôle de l’art.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (781, 'Friedrich Nietzsche', TRUE, 196, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (782, 'Immanuel Kant', FALSE, 196, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (783, 'Arthur Schopenhauer', FALSE, 196, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (784, 'Walter Benjamin', FALSE, 196, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 197
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (197, 'Quel philosophe a élaboré la "philosophie de la conscience pure" ?',
        'Johann Gottlieb Fichte a élaboré une philosophie centrée sur la conscience pure, posant le "Moi" comme principe fondamental.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (785, 'Johann Gottlieb Fichte', TRUE, 197, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (786, 'Georg Wilhelm Friedrich Hegel', FALSE, 197, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (787, 'Immanuel Kant', FALSE, 197, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (788, 'Edmund Husserl', FALSE, 197, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 198
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (198, 'Quel philosophe a influencé la pensée politique moderne avec sa réflexion sur le totalitarisme ?',
        'Hannah Arendt, notamment dans "Les Origines du totalitarisme", a profondément influencé la compréhension moderne des régimes totalitaires.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (789, 'Hannah Arendt', TRUE, 198, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (790, 'Martin Heidegger', FALSE, 198, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (791, 'Karl Marx', FALSE, 198, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (792, 'Theodor W. Adorno', FALSE, 198, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 199
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (199, 'Quel philosophe est à l’origine de la notion de "surhomme" ?',
        'Nietzsche introduit le concept de "surhomme" dans "Ainsi parlait Zarathoustra", représentant un idéal d’émancipation et de dépassement des valeurs traditionnelles.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (793, 'Friedrich Nietzsche', TRUE, 199, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (794, 'Immanuel Kant', FALSE, 199, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (795, 'Arthur Schopenhauer', FALSE, 199, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (796, 'Karl Marx', FALSE, 199, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 200
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (200, 'Quel philosophe a influencé la logique avec son "Tractatus logico-philosophicus" ?',
        'Ludwig Wittgenstein, dans son "Tractatus", propose une théorie du langage et de la logique influente au XXe siècle.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (797, 'Ludwig Wittgenstein', TRUE, 200, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (798, 'Edmund Husserl', FALSE, 200, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (799, 'Theodor W. Adorno', FALSE, 200, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (800, 'Martin Heidegger', FALSE, 200, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;



-- Insertion des relations entre quiz et questions
INSERT INTO quiz_questions (quiz_id, question_id)
VALUES (10, 181),
       (10, 182),
       (10, 183),
       (10, 184),
       (10, 185),
       (10, 186),
       (10, 187),
       (10, 188),
       (10, 189),
       (10, 190),
       (10, 191),
       (10, 192),
       (10, 193),
       (10, 194),
       (10, 195),
       (10, 196),
       (10, 197),
       (10, 198),
       (10, 199),
       (10, 200)
ON CONFLICT (quiz_id, question_id) DO NOTHING;


-- Mise à jour de la séquence pour les quiz
SELECT setval('quizzes_id_seq', (SELECT MAX(id) FROM quizzes));
-- Mise à jour de la séquence pour les questions
SELECT setval('questions_id_seq', (SELECT MAX(id) FROM questions));
-- Mise à jour de la séquence pour les réponses
SELECT setval('classic_answers_id_seq', (SELECT MAX(id) FROM classic_answers));