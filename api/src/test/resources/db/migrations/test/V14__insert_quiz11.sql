-- Insertion du quiz
INSERT INTO quizzes (id, title, id_category, id_theme)
VALUES (11, 'Philosophie française : figures, courants et concepts majeurs', null, 1);

-- QUESTION 201
INSERT INTO questions (id, text, id_quiz)
VALUES (201, 'Quel philosophe français est l’auteur du cogito, "Je pense, donc je suis" ?', 11);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (801, 'René Descartes', TRUE, 201),
       (802, 'Blaise Pascal', FALSE, 201),
       (803, 'Michel de Montaigne', FALSE, 201),
       (804, 'Jean-Jacques Rousseau', FALSE, 201);

-- QUESTION 202
INSERT INTO questions (id, text, id_quiz)
VALUES (202, 'Quel philosophe est célèbre pour ses "Essais" et le scepticisme humaniste ?', 11);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (805, 'Michel de Montaigne', TRUE, 202),
       (806, 'Voltaire', FALSE, 202),
       (807, 'Descartes', FALSE, 202),
       (808, 'Diderot', FALSE, 202);

-- QUESTION 203
INSERT INTO questions (id, text, id_quiz)
VALUES (203, 'Quel philosophe des Lumières a écrit "Candide" et défendu la liberté d’expression ?', 11);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (809, 'Voltaire', TRUE, 203),
       (810, 'Montesquieu', FALSE, 203),
       (811, 'Rousseau', FALSE, 203),
       (812, 'Diderot', FALSE, 203);

-- QUESTION 204
INSERT INTO questions (id, text, id_quiz)
VALUES (204, 'Qui est l’auteur du "Contrat social" et du concept de volonté générale ?', 11);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (813, 'Jean-Jacques Rousseau', TRUE, 204),
       (814, 'Voltaire', FALSE, 204),
       (815, 'Montesquieu', FALSE, 204),
       (816, 'Sartre', FALSE, 204);

-- QUESTION 205
INSERT INTO questions (id, text, id_quiz)
VALUES (205, 'Quel philosophe a dirigé la rédaction de l’Encyclopédie au XVIIIe siècle ?', 11);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (817, 'Denis Diderot', TRUE, 205),
       (818, 'Jean le Rond d’Alembert', FALSE, 205),
       (819, 'Voltaire', FALSE, 205),
       (820, 'Montesquieu', FALSE, 205);

-- QUESTION 206
INSERT INTO questions (id, text, id_quiz)
VALUES (206, 'Quel philosophe a théorisé la séparation des pouvoirs ?', 11);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (821, 'Montesquieu', TRUE, 206),
       (822, 'Rousseau', FALSE, 206),
       (823, 'Voltaire', FALSE, 206),
       (824, 'Diderot', FALSE, 206);

-- QUESTION 207
INSERT INTO questions (id, text, id_quiz)
VALUES (207, 'Quel philosophe a écrit "Pensées" et a réfléchi sur le pari de l’existence de Dieu ?', 11);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (825, 'Blaise Pascal', TRUE, 207),
       (826, 'Descartes', FALSE, 207),
       (827, 'Montaigne', FALSE, 207),
       (828, 'Sartre', FALSE, 207);

-- QUESTION 208
INSERT INTO questions (id, text, id_quiz)
VALUES (208, 'Quel philosophe est à l’origine du concept d’existentialisme en France ?', 11);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (829, 'Jean-Paul Sartre', TRUE, 208),
       (830, 'Albert Camus', FALSE, 208),
       (831, 'Simone de Beauvoir', FALSE, 208),
       (832, 'Foucault', FALSE, 208);

-- QUESTION 209
INSERT INTO questions (id, text, id_quiz)
VALUES (209, 'Quel philosophe a développé la notion d’absurde dans "Le Mythe de Sisyphe" ?', 11);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (833, 'Albert Camus', TRUE, 209),
       (834, 'Sartre', FALSE, 209),
       (835, 'Derrida', FALSE, 209),
       (836, 'Deleuze', FALSE, 209);

-- QUESTION 210
INSERT INTO questions (id, text, id_quiz)
VALUES (210, 'Qui a écrit "Le Deuxième Sexe" et analysé la condition féminine ?', 11);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (837, 'Simone de Beauvoir', TRUE, 210),
       (838, 'Julia Kristeva', FALSE, 210),
       (839, 'Sartre', FALSE, 210),
       (840, 'Camus', FALSE, 210);

-- QUESTION 211
INSERT INTO questions (id, text, id_quiz)
VALUES (211, 'Quel philosophe a introduit la "déconstruction" dans la philosophie ?', 11);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (841, 'Jacques Derrida', TRUE, 211),
       (842, 'Foucault', FALSE, 211),
       (843, 'Deleuze', FALSE, 211),
       (844, 'Sartre', FALSE, 211);

-- QUESTION 212
INSERT INTO questions (id, text, id_quiz)
VALUES (212, 'Quel philosophe a analysé le pouvoir et les institutions dans "Surveiller et punir" ?', 11);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (845, 'Michel Foucault', TRUE, 212),
       (846, 'Derrida', FALSE, 212),
       (847, 'Bourdieu', FALSE, 212),
       (848, 'Sartre', FALSE, 212);

-- QUESTION 213
INSERT INTO questions (id, text, id_quiz)
VALUES (213, 'Quel philosophe est associé à la phénoménologie française et à "La Structure du comportement" ?', 11);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (849, 'Maurice Merleau-Ponty', TRUE, 213),
       (850, 'Paul Ricoeur', FALSE, 213),
       (851, 'Bachelard', FALSE, 213),
       (852, 'Levinas', FALSE, 213);

-- QUESTION 214
INSERT INTO questions (id, text, id_quiz)
VALUES (214, 'Quel philosophe a fondé la sociologie française et réfléchi sur le fait social ?', 11);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (853, 'Émile Durkheim', TRUE, 214),
       (854, 'Bourdieu', FALSE, 214),
       (855, 'Comte', FALSE, 214),
       (856, 'Sartre', FALSE, 214);

-- QUESTION 215
INSERT INTO questions (id, text, id_quiz)
VALUES (215, 'Quel philosophe a développé la théorie de la mémoire involontaire dans "À la recherche du temps perdu" ?',
        11);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (857, 'Marcel Proust', TRUE, 215),
       (858, 'Paul Ricoeur', FALSE, 215),
       (859, 'Bergson', FALSE, 215),
       (860, 'Camus', FALSE, 215);

-- QUESTION 216
INSERT INTO questions (id, text, id_quiz)
VALUES (216, 'Quel philosophe a introduit la notion d’élan vital ?', 11);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (861, 'Henri Bergson', TRUE, 216),
       (862, 'Paul Ricoeur', FALSE, 216),
       (863, 'Bachelard', FALSE, 216),
       (864, 'Deleuze', FALSE, 216);

-- QUESTION 217
INSERT INTO questions (id, text, id_quiz)
VALUES (217, 'Quel philosophe a écrit "La Distinction" et analysé la société à travers la notion d’habitus ?', 11);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (865, 'Pierre Bourdieu', TRUE, 217),
       (866, 'Durkheim', FALSE, 217),
       (867, 'Sartre', FALSE, 217),
       (868, 'Derrida', FALSE, 217);

-- QUESTION 218
INSERT INTO questions (id, text, id_quiz)
VALUES (218, 'Quel philosophe a développé la théorie de la multiplicité et du "rhizome" ?', 11);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (869, 'Gilles Deleuze', TRUE, 218),
       (870, 'Foucault', FALSE, 218),
       (871, 'Lyotard', FALSE, 218),
       (872, 'Badiou', FALSE, 218);

-- QUESTION 219
INSERT INTO questions (id, text, id_quiz, disabled_at)
VALUES (219, 'Quel philosophe a introduit la notion de "différend" et a écrit "La Condition postmoderne" ?', 11,
        CURRENT_TIMESTAMP);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (873, 'Jean-François Lyotard', TRUE, 219),
       (874, 'Derrida', FALSE, 219),
       (875, 'Deleuze', FALSE, 219),
       (876, 'Badiou', FALSE, 219);

-- QUESTION 220
INSERT INTO questions (id, text, id_quiz)
VALUES (220, 'Quel philosophe contemporain est connu pour sa réflexion sur l’événement et les mathématiques ?', 11);
INSERT INTO answers (id, text, is_correct, id_question, disabled_at)
VALUES (877, 'Alain Badiou', TRUE, 220, null),
       (878, 'Julia Kristeva', FALSE, 220, null),
       (879, 'Paul Ricoeur', FALSE, 220, null),
       (880, 'Simone Weil', FALSE, 220, null),
       (881, 'Gilles Deleuze', FALSE, 220, CURRENT_TIMESTAMP);

-- QUESTION 221
INSERT INTO questions (id, text, id_quiz)
VALUES (221, 'Question au pif juste pour les tests', 11);
INSERT INTO answers (id, text, is_correct, id_question, disabled_at)
VALUES (882, 'Réponse 1', TRUE, 221, null),
       (883, 'Réponçe, 2!', TRUE, 221, null),
       (884, 'Réponse 3', FALSE, 221, null),
       (885, 'Réponse 4', FALSE, 221, null),
       (886, 'Réponse 5', FALSE, 221, null),
       (887, 'Réponse 6', TRUE, 221, CURRENT_TIMESTAMP);


-- QUESTION 222
INSERT INTO questions (id, text, id_quiz)
VALUES (222, 'Question avec que des true', 11);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (888, 'Réponse 1', TRUE, 222),
       (889, 'Réponse 2', TRUE, 222),
       (890, 'Réponse 3', TRUE, 222),
       (891, 'Réponse 4', TRUE, 222),
       (892, 'Réponse 5', TRUE, 222);
