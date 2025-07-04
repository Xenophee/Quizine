-- Insertion du quiz
INSERT INTO quizzes (id, title, description, category_id, theme_id, quiz_type_code, mastery_level_id, created_at,
                     updated_at, disabled_at)
VALUES (11, 'Philosophie française : figures, courants et concepts majeurs',
        'Un voyage à travers les penseurs et les idées qui ont façonné la philosophie française, de Descartes à Bourdieu.',
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



-- QUESTION 201
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (201, 'Quel philosophe français est l’auteur du cogito, "Je pense, donc je suis" ?',
        'René Descartes a formulé cette célèbre maxime dans son "Discours de la méthode", marquant le fondement de la pensée moderne et du rationalisme.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (801, 'René Descartes', TRUE, 201, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (802, 'Blaise Pascal', FALSE, 201, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (803, 'Michel de Montaigne', FALSE, 201, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (804, 'Jean-Jacques Rousseau', FALSE, 201, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 202
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (202, 'Quel philosophe est célèbre pour ses "Essais" et le scepticisme humaniste ?',
        'Michel de Montaigne, à travers ses "Essais", explore la nature humaine avec un scepticisme modéré, influençant profondément la pensée humaniste.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (805, 'Michel de Montaigne', TRUE, 202, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (806, 'Voltaire', FALSE, 202, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (807, 'Descartes', FALSE, 202, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (808, 'Diderot', FALSE, 202, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 203
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (203, 'Quel philosophe des Lumières a écrit "Candide" et défendu la liberté d’expression ?',
        'Voltaire, figure majeure des Lumières, critique l’optimisme de Leibniz dans "Candide" et milite ardemment pour la tolérance et la liberté d’expression.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (809, 'Voltaire', TRUE, 203, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (810, 'Montesquieu', FALSE, 203, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (811, 'Rousseau', FALSE, 203, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (812, 'Diderot', FALSE, 203, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 204
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (204, 'Qui est l’auteur du "Contrat social" et du concept de volonté générale ?',
        'Jean-Jacques Rousseau, dans "Du contrat social", développe l’idée de volonté générale comme fondement de la légitimité politique démocratique.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (813, 'Jean-Jacques Rousseau', TRUE, 204, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (814, 'Voltaire', FALSE, 204, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (815, 'Montesquieu', FALSE, 204, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (816, 'Sartre', FALSE, 204, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 205
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (205, 'Quel philosophe a dirigé la rédaction de l’Encyclopédie au XVIIIe siècle ?',
        'Denis Diderot a été l’un des principaux rédacteurs et coordinateurs de l’Encyclopédie, œuvre phare des Lumières visant à diffuser le savoir.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (817, 'Denis Diderot', TRUE, 205, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (818, 'Jean le Rond d’Alembert', FALSE, 205, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (819, 'Voltaire', FALSE, 205, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (820, 'Montesquieu', FALSE, 205, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 206
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (206, 'Quel philosophe a théorisé la séparation des pouvoirs ?',
        'Montesquieu a développé la théorie de la séparation des pouvoirs dans "De l’esprit des lois", influençant fortement les constitutions modernes.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (821, 'Montesquieu', TRUE, 206, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (822, 'Rousseau', FALSE, 206, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (823, 'Voltaire', FALSE, 206, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (824, 'Diderot', FALSE, 206, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 207
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (207, 'Quel philosophe a écrit "Pensées" et a réfléchi sur le pari de l’existence de Dieu ?',
        'Blaise Pascal, dans ses "Pensées", propose le pari de croire en Dieu comme un choix rationnel face à l’incertitude métaphysique.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (825, 'Blaise Pascal', TRUE, 207, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (826, 'Descartes', FALSE, 207, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (827, 'Montaigne', FALSE, 207, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (828, 'Sartre', FALSE, 207, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 208
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (208, 'Quel philosophe est à l’origine du concept d’existentialisme en France ?',
        'Jean-Paul Sartre est le principal représentant de l’existentialisme athée, développant l’idée que l’existence précède l’essence.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (829, 'Jean-Paul Sartre', TRUE, 208, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (830, 'Albert Camus', FALSE, 208, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (831, 'Simone de Beauvoir', FALSE, 208, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (832, 'Foucault', FALSE, 208, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 209
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (209, 'Quel philosophe a développé la notion d’absurde dans "Le Mythe de Sisyphe" ?',
        'Albert Camus y explore l’absurde, né du conflit entre la quête de sens humaine et le silence du monde.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (833, 'Albert Camus', TRUE, 209, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (834, 'Sartre', FALSE, 209, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (835, 'Derrida', FALSE, 209, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (836, 'Deleuze', FALSE, 209, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 210
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (210, 'Qui a écrit "Le Deuxième Sexe" et analysé la condition féminine ?',
        'Simone de Beauvoir y développe une analyse existentialiste de la condition féminine et la célèbre formule "On ne naît pas femme, on le devient."',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (837, 'Simone de Beauvoir', TRUE, 210, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (838, 'Julia Kristeva', FALSE, 210, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (839, 'Sartre', FALSE, 210, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (840, 'Camus', FALSE, 210, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 211
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (211, 'Quel philosophe a introduit la "déconstruction" dans la philosophie ?',
        'Jacques Derrida a introduit la déconstruction comme méthode d’analyse critique des textes et des structures de pensée occidentale.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (841, 'Jacques Derrida', TRUE, 211, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (842, 'Foucault', FALSE, 211, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (843, 'Deleuze', FALSE, 211, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (844, 'Sartre', FALSE, 211, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 212
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (212, 'Quel philosophe a analysé le pouvoir et les institutions dans "Surveiller et punir" ?',
        'Michel Foucault y analyse les dispositifs disciplinaires modernes et l’évolution des pratiques de surveillance et de contrôle.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (845, 'Michel Foucault', TRUE, 212, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (846, 'Derrida', FALSE, 212, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (847, 'Bourdieu', FALSE, 212, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (848, 'Sartre', FALSE, 212, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 213
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (213, 'Quel philosophe est associé à la phénoménologie française et à "La Structure du comportement" ?',
        'Maurice Merleau-Ponty développe une phénoménologie du corps et de la perception dans ses travaux majeurs comme "Phénoménologie de la perception".',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (849, 'Maurice Merleau-Ponty', TRUE, 213, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (850, 'Paul Ricoeur', FALSE, 213, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (851, 'Bachelard', FALSE, 213, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (852, 'Levinas', FALSE, 213, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 214
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (214, 'Quel philosophe a fondé la sociologie française et réfléchi sur le fait social ?',
        'Émile Durkheim pose les bases de la sociologie moderne et définit le fait social comme extérieur et contraignant pour l’individu.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (853, 'Émile Durkheim', TRUE, 214, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (854, 'Bourdieu', FALSE, 214, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (855, 'Comte', FALSE, 214, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (856, 'Sartre', FALSE, 214, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 215
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (215, 'Quel philosophe a développé la théorie de la mémoire involontaire dans "À la recherche du temps perdu" ?',
        'Marcel Proust illustre la mémoire involontaire à travers des expériences sensorielles qui déclenchent des souvenirs enfouis.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (857, 'Marcel Proust', TRUE, 215, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (858, 'Paul Ricoeur', FALSE, 215, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (859, 'Bergson', FALSE, 215, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (860, 'Camus', FALSE, 215, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 216
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (216, 'Quel philosophe a introduit la notion d’élan vital ?',
        'Henri Bergson conçoit l’élan vital comme une force créatrice qui traverse et anime la vie, au-delà des mécanismes biologiques.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (861, 'Henri Bergson', TRUE, 216, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (862, 'Paul Ricoeur', FALSE, 216, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (863, 'Bachelard', FALSE, 216, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (864, 'Deleuze', FALSE, 216, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 217
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (217, 'Quel philosophe a écrit "La Distinction" et analysé la société à travers la notion d’habitus ?',
        'Pierre Bourdieu y introduit la notion d’habitus pour expliquer comment les pratiques sociales sont structurées par des dispositions durables.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (865, 'Pierre Bourdieu', TRUE, 217, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (866, 'Durkheim', FALSE, 217, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (867, 'Sartre', FALSE, 217, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (868, 'Derrida', FALSE, 217, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 218
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (218, 'Quel philosophe a développé la théorie de la multiplicité et du "rhizome" ?',
        'Gilles Deleuze, avec Félix Guattari, propose le "rhizome" comme modèle non hiérarchique de connaissance et de pensée.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (869, 'Gilles Deleuze', TRUE, 218, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (870, 'Foucault', FALSE, 218, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (871, 'Lyotard', FALSE, 218, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (872, 'Badiou', FALSE, 218, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 219
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (219, 'Quel philosophe a introduit la notion de "différend" et a écrit "La Condition postmoderne" ?',
        'Jean-François Lyotard critique les grands récits modernes et introduit le "différend" pour désigner des conflits de langage irréconciliables.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (873, 'Jean-François Lyotard', TRUE, 219, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (874, 'Derrida', FALSE, 219, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (875, 'Deleuze', FALSE, 219, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (876, 'Badiou', FALSE, 219, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;

-- QUESTION 220
INSERT INTO questions (id, text, answer_explanation, question_type_code, created_at, updated_at, disabled_at)
VALUES (220, 'Quel philosophe contemporain est connu pour sa réflexion sur l’événement et les mathématiques ?',
        'Alain Badiou articule philosophie et mathématiques en conceptualisant l’événement comme rupture dans l’ordre de la connaissance.',
        'CLASSIC',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text               = EXCLUDED.text,
                               answer_explanation = EXCLUDED.answer_explanation,
                               created_at         = EXCLUDED.created_at,
                               updated_at         = EXCLUDED.updated_at,
                               disabled_at        = EXCLUDED.disabled_at;

INSERT INTO classic_answers (id, text, is_correct, question_id, created_at, updated_at, disabled_at)
VALUES (877, 'Alain Badiou', TRUE, 220, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (878, 'Julia Kristeva', FALSE, 220, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (879, 'Paul Ricoeur', FALSE, 220, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (880, 'Simone Weil', FALSE, 220, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null)
ON CONFLICT (id) DO UPDATE SET text        = EXCLUDED.text,
                               is_correct  = EXCLUDED.is_correct,
                               created_at  = EXCLUDED.created_at,
                               updated_at  = EXCLUDED.updated_at,
                               disabled_at = EXCLUDED.disabled_at;



-- Insertion des relations entre quiz et questions
INSERT INTO quiz_questions (quiz_id, question_id)
VALUES (11, 201),
       (11, 202),
       (11, 203),
       (11, 204),
       (11, 205),
       (11, 206),
       (11, 207),
       (11, 208),
       (11, 209),
       (11, 210),
       (11, 211),
       (11, 212),
       (11, 213),
       (11, 214),
       (11, 215),
       (11, 216),
       (11, 217),
       (11, 218),
       (11, 219),
       (11, 220)
ON CONFLICT (quiz_id, question_id) DO NOTHING;


-- Mise à jour de la séquence pour les quiz
SELECT setval('quizzes_id_seq', (SELECT MAX(id) FROM quizzes));
-- Mise à jour de la séquence pour les questions
SELECT setval('questions_id_seq', (SELECT MAX(id) FROM questions));
-- Mise à jour de la séquence pour les réponses
SELECT setval('classic_answers_id_seq', (SELECT MAX(id) FROM classic_answers));