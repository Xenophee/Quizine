-- Insertion du quiz
INSERT INTO quizzes (id, title, id_category, id_theme)
VALUES (10, 'Philosophie allemande : courants, penseurs et concepts', 8, 5);

-- QUESTION 181
INSERT INTO questions (id, text, id_quiz)
VALUES (181, 'Quel philosophe est considéré comme le fondateur du criticisme ?', 10);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (721, 'Immanuel Kant', TRUE, 181),
       (722, 'Friedrich Nietzsche', FALSE, 181),
       (723, 'Georg Wilhelm Friedrich Hegel', FALSE, 181),
       (724, 'Arthur Schopenhauer', FALSE, 181);

-- QUESTION 182
INSERT INTO questions (id, text, id_quiz)
VALUES (182, 'Quel courant philosophique regroupe Fichte, Schelling et Hegel ?', 10);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (725, 'L’idéalisme allemand', TRUE, 182),
       (726, 'Le positivisme', FALSE, 182),
       (727, 'L’existentialisme', FALSE, 182),
       (728, 'Le matérialisme', FALSE, 182);

-- QUESTION 183
INSERT INTO questions (id, text, id_quiz)
VALUES (183, 'Quel philosophe a écrit "La Phénoménologie de l’Esprit" ?', 10);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (729, 'Georg Wilhelm Friedrich Hegel', TRUE, 183),
       (730, 'Karl Marx', FALSE, 183),
       (731, 'Martin Heidegger', FALSE, 183),
       (732, 'Friedrich Nietzsche', FALSE, 183);

-- QUESTION 184
INSERT INTO questions (id, text, id_quiz)
VALUES (184, 'Quel philosophe allemand est célèbre pour sa critique de la métaphysique et du nihilisme ?', 10);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (733, 'Friedrich Nietzsche', TRUE, 184),
       (734, 'Immanuel Kant', FALSE, 184),
       (735, 'Edmund Husserl', FALSE, 184),
       (736, 'Theodor W. Adorno', FALSE, 184);

-- QUESTION 185
INSERT INTO questions (id, text, id_quiz)
VALUES (185, 'Quel concept central Kant introduit-il dans sa "Critique de la raison pure" ?', 10);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (737, 'Les catégories a priori', TRUE, 185),
       (738, 'La volonté de puissance', FALSE, 185),
       (739, 'L’aliénation', FALSE, 185),
       (740, 'La dialectique négative', FALSE, 185);

-- QUESTION 186
INSERT INTO questions (id, text, id_quiz)
VALUES (186, 'Quel philosophe est à l’origine du concept de "volonté de puissance" ?', 10);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (741, 'Friedrich Nietzsche', TRUE, 186),
       (742, 'Arthur Schopenhauer', FALSE, 186),
       (743, 'Karl Marx', FALSE, 186),
       (744, 'Martin Heidegger', FALSE, 186);

-- QUESTION 187
INSERT INTO questions (id, text, id_quiz)
VALUES (187, 'Quel philosophe est le fondateur de la phénoménologie ?', 10);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (745, 'Edmund Husserl', TRUE, 187),
       (746, 'Martin Heidegger', FALSE, 187),
       (747, 'Ludwig Wittgenstein', FALSE, 187),
       (748, 'Jürgen Habermas', FALSE, 187);

-- QUESTION 188
INSERT INTO questions (id, text, id_quiz)
VALUES (188, 'Quel philosophe a développé la notion de "dialectique du maître et de l’esclave" ?', 10);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (749, 'Georg Wilhelm Friedrich Hegel', TRUE, 188),
       (750, 'Karl Marx', FALSE, 188),
       (751, 'Immanuel Kant', FALSE, 188),
       (752, 'Arthur Schopenhauer', FALSE, 188);

-- QUESTION 189
INSERT INTO questions (id, text, id_quiz)
VALUES (189, 'Quel penseur allemand est considéré comme le fondateur du matérialisme historique ?', 10);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (753, 'Karl Marx', TRUE, 189),
       (754, 'Friedrich Nietzsche', FALSE, 189),
       (755, 'Martin Heidegger', FALSE, 189),
       (756, 'Edmund Husserl', FALSE, 189);

-- QUESTION 190
INSERT INTO questions (id, text, id_quiz)
VALUES (190, 'Quel philosophe a influencé la psychanalyse par sa réflexion sur l’inconscient ?', 10);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (757, 'Arthur Schopenhauer', TRUE, 190),
       (758, 'Immanuel Kant', FALSE, 190),
       (759, 'Friedrich Nietzsche', FALSE, 190),
       (760, 'Jürgen Habermas', FALSE, 190);

-- QUESTION 191
INSERT INTO questions (id, text, id_quiz)
VALUES (191, 'Quel philosophe a écrit "L’Être et le Temps" ?', 10);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (761, 'Martin Heidegger', TRUE, 191),
       (762, 'Edmund Husserl', FALSE, 191),
       (763, 'Walter Benjamin', FALSE, 191),
       (764, 'Karl Jaspers', FALSE, 191);

-- QUESTION 192
INSERT INTO questions (id, text, id_quiz)
VALUES (192, 'Quel courant philosophique est associé à Adorno et Horkheimer ?', 10);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (765, 'L’École de Francfort', TRUE, 192),
       (766, 'L’idéalisme allemand', FALSE, 192),
       (767, 'La phénoménologie', FALSE, 192),
       (768, 'Le positivisme logique', FALSE, 192);

-- QUESTION 193
INSERT INTO questions (id, text, id_quiz)
VALUES (193, 'Quel philosophe est célèbre pour sa réflexion sur la modernité et la "raison communicationnelle" ?', 10);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (769, 'Jürgen Habermas', TRUE, 193),
       (770, 'Walter Benjamin', FALSE, 193),
       (771, 'Hannah Arendt', FALSE, 193),
       (772, 'Karl Marx', FALSE, 193);

-- QUESTION 194
INSERT INTO questions (id, text, id_quiz)
VALUES (194, 'Quel philosophe a introduit la notion de "monde comme volonté et comme représentation" ?', 10);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (773, 'Arthur Schopenhauer', TRUE, 194),
       (774, 'Friedrich Nietzsche', FALSE, 194),
       (775, 'Immanuel Kant', FALSE, 194),
       (776, 'Georg Wilhelm Friedrich Hegel', FALSE, 194);

-- QUESTION 195
INSERT INTO questions (id, text, id_quiz)
VALUES (195, 'Quel philosophe a développé la "théorie critique" ?', 10);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (777, 'Theodor W. Adorno', TRUE, 195),
       (778, 'Martin Heidegger', FALSE, 195),
       (779, 'Friedrich Nietzsche', FALSE, 195),
       (780, 'Ludwig Wittgenstein', FALSE, 195);

-- QUESTION 196
INSERT INTO questions (id, text, id_quiz)
VALUES (196, 'Quel philosophe a écrit "La naissance de la tragédie" ?', 10);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (781, 'Friedrich Nietzsche', TRUE, 196),
       (782, 'Immanuel Kant', FALSE, 196),
       (783, 'Arthur Schopenhauer', FALSE, 196),
       (784, 'Walter Benjamin', FALSE, 196);

-- QUESTION 197
INSERT INTO questions (id, text, id_quiz)
VALUES (197, 'Quel philosophe a élaboré la "philosophie de la conscience pure" ?', 10);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (785, 'Johann Gottlieb Fichte', TRUE, 197),
       (786, 'Georg Wilhelm Friedrich Hegel', FALSE, 197),
       (787, 'Immanuel Kant', FALSE, 197),
       (788, 'Edmund Husserl', FALSE, 197);

-- QUESTION 198
INSERT INTO questions (id, text, id_quiz)
VALUES (198, 'Quel philosophe a influencé la pensée politique moderne avec sa réflexion sur le totalitarisme ?', 10);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (789, 'Hannah Arendt', TRUE, 198),
       (790, 'Martin Heidegger', FALSE, 198),
       (791, 'Karl Marx', FALSE, 198),
       (792, 'Theodor W. Adorno', FALSE, 198);

-- QUESTION 199
INSERT INTO questions (id, text, id_quiz)
VALUES (199, 'Quel philosophe est à l’origine de la notion de "surhomme" ?', 10);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (793, 'Friedrich Nietzsche', TRUE, 199),
       (794, 'Immanuel Kant', FALSE, 199),
       (795, 'Arthur Schopenhauer', FALSE, 199),
       (796, 'Karl Marx', FALSE, 199);

-- QUESTION 200
INSERT INTO questions (id, text, id_quiz)
VALUES (200, 'Quel philosophe a influencé la logique avec son "Tractatus logico-philosophicus" ?', 10);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (797, 'Ludwig Wittgenstein', TRUE, 200),
       (798, 'Edmund Husserl', FALSE, 200),
       (799, 'Theodor W. Adorno', FALSE, 200),
       (800, 'Martin Heidegger', FALSE, 200);
