-- Insertion du quiz
INSERT INTO quizzes (id, title, is_vip_only, id_category, id_theme)
VALUES (9, 'Mythologie nordique : dieux, créatures et légendes vikings', FALSE, 9, 5);

-- QUESTION 161
INSERT INTO questions (id, text, id_quiz)
VALUES (161, 'Quel dieu nordique est surnommé le "Père de tout" et règne sur Asgard ?', 9);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (641, 'Odin', TRUE, 161),
       (642, 'Thor', FALSE, 161),
       (643, 'Loki', FALSE, 161),
       (644, 'Freyja', FALSE, 161);

-- QUESTION 162
INSERT INTO questions (id, text, id_quiz)
VALUES (162, 'Quel dieu est célèbre pour son marteau Mjöllnir et contrôle la foudre ?', 9);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (645, 'Thor', TRUE, 162),
       (646, 'Odin', FALSE, 162),
       (647, 'Tyr', FALSE, 162),
       (648, 'Baldr', FALSE, 162);

-- QUESTION 163
INSERT INTO questions (id, text, id_quiz)
VALUES (163, 'Quel dieu est associé à la ruse, au feu et peut changer de forme ?', 9);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (649, 'Loki', TRUE, 163),
       (650, 'Heimdall', FALSE, 163),
       (651, 'Njörd', FALSE, 163),
       (652, 'Bragi', FALSE, 163);

-- QUESTION 164
INSERT INTO questions (id, text, id_quiz)
VALUES (164, 'Quel animal est le compagnon d’Odin et lui rapporte des nouvelles du monde ?', 9);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (653, 'Le corbeau', TRUE, 164),
       (654, 'Le loup', FALSE, 164),
       (655, 'Le serpent', FALSE, 164),
       (656, 'Le cheval', FALSE, 164);

-- QUESTION 165
INSERT INTO questions (id, text, id_quiz)
VALUES (165, 'Quel est le nom de l’arbre-monde reliant les neuf royaumes ?', 9);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (657, 'Yggdrasil', TRUE, 165),
       (658, 'Valhalla', FALSE, 165),
       (659, 'Bifrost', FALSE, 165),
       (660, 'Midgard', FALSE, 165);

-- QUESTION 166
INSERT INTO questions (id, text, id_quiz)
VALUES (166, 'Quel est le nom du pont arc-en-ciel reliant Asgard à Midgard ?', 9);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (661, 'Bifrost', TRUE, 166),
       (662, 'Valhalla', FALSE, 166),
       (663, 'Helheim', FALSE, 166),
       (664, 'Jotunheim', FALSE, 166);

-- QUESTION 167
INSERT INTO questions (id, text, id_quiz)
VALUES (167, 'Quelle déesse est associée à l’amour, la fertilité et la guerre ?', 9);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (665, 'Freyja', TRUE, 167),
       (666, 'Frigg', FALSE, 167),
       (667, 'Sif', FALSE, 167),
       (668, 'Idunn', FALSE, 167);

-- QUESTION 168
INSERT INTO questions (id, text, id_quiz)
VALUES (168, 'Quel dieu manchot a sacrifié sa main pour enchaîner le loup Fenrir ?', 9);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (669, 'Tyr', TRUE, 168),
       (670, 'Odin', FALSE, 168),
       (671, 'Thor', FALSE, 168),
       (672, 'Baldr', FALSE, 168);

-- QUESTION 169
INSERT INTO questions (id, text, id_quiz)
VALUES (169, 'Quel est le nom du loup géant qui dévore Odin lors du Ragnarök ?', 9);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (673, 'Fenrir', TRUE, 169),
       (674, 'Jörmungand', FALSE, 169),
       (675, 'Sleipnir', FALSE, 169),
       (676, 'Garm', FALSE, 169);

-- QUESTION 170
INSERT INTO questions (id, text, id_quiz)
VALUES (170, 'Quel serpent géant affronte Thor lors du Ragnarök ?', 9);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (677, 'Jörmungand', TRUE, 170),
       (678, 'Fenrir', FALSE, 170),
       (679, 'Nidhogg', FALSE, 170),
       (680, 'Garm', FALSE, 170);

-- QUESTION 171
INSERT INTO questions (id, text, id_quiz)
VALUES (171, 'Quel est le nom du paradis des guerriers morts au combat ?', 9);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (681, 'Valhalla', TRUE, 171),
       (682, 'Helheim', FALSE, 171),
       (683, 'Asgard', FALSE, 171),
       (684, 'Vanaheim', FALSE, 171);

-- QUESTION 172
INSERT INTO questions (id, text, id_quiz)
VALUES (172, 'Quelle prophétie annonce la fin du monde et la mort de nombreux dieux ?', 9);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (685, 'Ragnarök', TRUE, 172),
       (686, 'Edda', FALSE, 172),
       (687, 'Yule', FALSE, 172),
       (688, 'Fimbulvetr', FALSE, 172);

-- QUESTION 173
INSERT INTO questions (id, text, id_quiz)
VALUES (173, 'Quel dieu est le gardien du Bifrost et veille sur Asgard ?', 9);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (689, 'Heimdall', TRUE, 173),
       (690, 'Bragi', FALSE, 173),
       (691, 'Njörd', FALSE, 173),
       (692, 'Baldr', FALSE, 173);

-- QUESTION 174
INSERT INTO questions (id, text, id_quiz)
VALUES (174, 'Quel est le nom du cheval à huit pattes d’Odin ?', 9);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (693, 'Sleipnir', TRUE, 174),
       (694, 'Gullinbursti', FALSE, 174),
       (695, 'Fenrir', FALSE, 174),
       (696, 'Grani', FALSE, 174);

-- QUESTION 175
INSERT INTO questions (id, text, id_quiz)
VALUES (175, 'Quel dieu est tué par une flèche de gui lancée par son frère Höd, manipulé par Loki ?', 9);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (697, 'Baldr', TRUE, 175),
       (698, 'Thor', FALSE, 175),
       (699, 'Tyr', FALSE, 175),
       (700, 'Vidar', FALSE, 175);

-- QUESTION 176
INSERT INTO questions (id, text, id_quiz)
VALUES (176, 'Quel peuple mythique fabrique les armes magiques des dieux nordiques ?', 9);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (701, 'Les nains', TRUE, 176),
       (702, 'Les géants', FALSE, 176),
       (703, 'Les elfes', FALSE, 176),
       (704, 'Les Vanes', FALSE, 176);

-- QUESTION 177
INSERT INTO questions (id, text, id_quiz)
VALUES (177, 'Quel dieu est connu pour sa sagesse, la poésie et le sacrifice de son œil ?', 9);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (705, 'Odin', TRUE, 177),
       (706, 'Bragi', FALSE, 177),
       (707, 'Tyr', FALSE, 177),
       (708, 'Njörd', FALSE, 177);

-- QUESTION 178
INSERT INTO questions (id, text, id_quiz)
VALUES (178, 'Quel est le nom de la déesse de la jeunesse, gardienne des pommes d’immortalité ?', 9);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (709, 'Idunn', TRUE, 178),
       (710, 'Freyja', FALSE, 178),
       (711, 'Hel', FALSE, 178),
       (712, 'Sif', FALSE, 178);

-- QUESTION 179
INSERT INTO questions (id, text, id_quiz)
VALUES (179, 'Quel royaume est le monde des morts dans la mythologie nordique ?', 9);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (713, 'Helheim', TRUE, 179),
       (714, 'Asgard', FALSE, 179),
       (715, 'Vanaheim', FALSE, 179),
       (716, 'Midgard', FALSE, 179);

-- QUESTION 180
INSERT INTO questions (id, text, id_quiz)
VALUES (180, 'Quel dragon ronge les racines d’Yggdrasil ?', 9);
INSERT INTO answers (id, text, is_correct, id_question)
VALUES (717, 'Nidhogg', TRUE, 180),
       (718, 'Jörmungand', FALSE, 180),
       (719, 'Fenrir', FALSE, 180),
       (720, 'Gullinbursti', FALSE, 180);
