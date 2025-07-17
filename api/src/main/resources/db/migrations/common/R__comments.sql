COMMENT ON TABLE themes
    IS 'Thèmes disponibles pour classifier les quiz. Chaque thème a un nom unique. Un seul thème peut être défini comme thème par défaut.';
COMMENT ON COLUMN themes.is_default
    IS 'Indique si le thème est celui par défaut. Un seul thème peut être défini comme tel à la fois. Cela permet d’empêcher sa suppression et de garantir qu’un thème soit toujours disponible dans le système.';


COMMENT ON TABLE categories
    IS 'Catégories disponibles pour chaque thème. Chaque catégorie a un nom unique et est associée à un thème spécifique. Elles permettent une classification plus fine des quiz.';


COMMENT ON TABLE quiz_types
    IS 'Types de quiz proposés, chacun correspondant à une logique de jeu spécifique. Ils déterminent quels types de questions sont utilisés.';
COMMENT ON COLUMN quiz_types.code
    IS 'Code unique utilisé comme identifiant métier pour le type de quiz. Utilisé dans les relations avec d’autres entités. Par convention, il est écrit en majuscules.';
COMMENT ON COLUMN quiz_types.name
    IS 'Le nom du type de quiz est utilisé pour l’affichage utilisateurs. Il doit être unique pour chaque type de quiz.';


COMMENT ON TABLE question_types
    IS 'Types de questions disponibles pour les quiz. Ils sont utilisés pour déterminer leur comportement dans le jeu.';
COMMENT ON COLUMN question_types.code
    IS 'Code unique utilisé comme identifiant métier pour le type de question. Utilisé dans les relations avec d’autres entités. Par convention, il est écrit en majuscules.';
COMMENT ON COLUMN question_types.name
    IS 'Le nom du type de question est utilisé pour l’affichage utilisateurs. Il doit être unique pour chaque type de question.';
COMMENT ON COLUMN question_types.description
    IS 'Description du type de question afin de clarifier son usage et ses spécificités aux créateurs de quiz.';
COMMENT ON COLUMN question_types.instruction
    IS 'Instruction spécifique au type de question, utilisée pour guider les joueurs sur la manière de répondre.';


COMMENT ON TABLE quiz_type_questions
    IS 'Association entre les types de quiz et les types de questions. Une paire (type de quiz, type de question) ne peut apparaître qu’une seule fois.';


COMMENT ON TABLE mastery_levels
    IS 'Niveaux de maîtrise indiquant la difficulté globale du quiz. Ceux-ci sont uniquement à but informatif et ne sont pas utilisés pour la logique de jeu. Ils permettent de donner une indication sur le niveau de difficulté du quiz aux joueurs.';
COMMENT ON COLUMN mastery_levels.name
    IS 'Nom du niveau de maîtrise, utilisé pour l’affichage aux utilisateurs. Il doit être unique pour chaque niveau de maîtrise. Par convention, il est indiqué en majuscules.';
COMMENT ON COLUMN mastery_levels.rank
    IS 'Rang du niveau de maîtrise, utilisé pour ordonner les niveaux de difficulté. Il doit être unique et supérieur à 0. Les rangs sont utilisés pour trier les niveaux de maîtrise dans l’ordre croissant de difficulté.';


COMMENT ON TABLE quizzes
    IS 'Quiz disponibles dans le système. Chaque quiz est associé à un thème, une catégorie, un type de quiz et un niveau de maîtrise.';


COMMENT ON TABLE questions
    IS 'Questions utilisées dans les quiz. Chaque question est associée à un type de question et peut avoir plusieurs réponses possibles.';
COMMENT ON COLUMN questions.text
    IS 'Texte de la question, utilisé pour l’affichage aux utilisateurs. Il doit être unique pour chaque question dans le contexte d’un quiz (assuré par la table quiz_questions).';
COMMENT ON COLUMN questions.answer_explanation
    IS 'Explication de la réponse, utilisée pour clarifier la bonne réponse aux joueurs après qu’ils aient répondu à la question.';
COMMENT ON COLUMN questions.answer_if_true_false
    IS 'Réponse à fournir si la question est de type Vrai/Faux. Elle est utilisée pour valider la réponse du joueur. Si la question n’est pas de ce type, cette colonne doit être NULL. Cette approche évite la surnormalisation.';


COMMENT ON TABLE quiz_questions
    IS 'Association entre les quiz et les questions. Elle permet de lier une question à plusieurs quiz et vice versa. Ces cas de figure ne doivent survenir que pour les cas de mixages automatique (pour des quiz bilan, des quiz du jour, etc). Chaque association est unique pour un quiz et une question donnés.';


COMMENT ON TABLE classic_answers
    IS 'Réponses possibles pour les questions de type Classique. Chaque réponse est associée à une question spécifique et peut être correcte ou non.';
COMMENT ON COLUMN classic_answers.text
    IS 'Texte de la réponse, utilisé pour l’affichage aux utilisateurs. Il doit être unique pour chaque réponse dans le contexte d’une question.';
COMMENT ON COLUMN classic_answers.is_correct
    IS 'Indique si la réponse est correcte ou non. Elle permet de valider la réponse du joueur.';


COMMENT ON TABLE difficulty_levels
    IS 'Niveaux de difficulté disponibles pour les types de questions. Quatre niveaux génériques sont définis : FACILE, MOYEN, DIFFICILE et EXPERT. Des niveaux d''exception peuvent être ajoutés pour des évènements.';
COMMENT ON COLUMN difficulty_levels.name
    IS 'Nom thématique du niveau de difficulté, utilisé pour l’affichage aux utilisateurs. Il doit être unique pour chaque niveau de difficulté.';
COMMENT ON COLUMN difficulty_levels.is_reference
    IS 'Indique si le niveau de difficulté est une référence afin d''empêcher la désactivation et de garantir qu''un niveau de difficulté soit toujours disponible dans le système. Un seul niveau de difficulté peut être défini comme référence à la fois';
COMMENT ON COLUMN difficulty_levels.label
    IS 'Label utilisé pour représenter explicitement le niveau de difficulté (ex. FACILE, MOYEN). Contrairement au name, il est standardisé pour l’interface utilisateur. Par convention, il est écrit en majuscules et doit être unique.';
COMMENT ON COLUMN difficulty_levels.rank
    IS 'Rang numérique pour trier les niveaux de difficulté génériques (1 = FACILE, ..., 4 = EXPERT). Doit être NULL pour les niveaux d’exception.';
COMMENT ON COLUMN difficulty_levels.starts_at
    IS 'Date de début de validité, utilisée uniquement pour les niveaux de difficulté d’exception (et non les niveaux génériques).';
COMMENT ON COLUMN difficulty_levels.ends_at
    IS 'Date de fin de validité, utilisée uniquement pour les niveaux de difficulté d’exception (et non les niveaux génériques).';
COMMENT ON COLUMN difficulty_levels.is_recurring
    IS 'Indique si le niveau d’exception est récurrent (ex. évènements saisonniers). Toujours FALSE pour les niveaux génériques.';


COMMENT ON TABLE question_type_difficulty
    IS 'Association entre les types de questions et les niveaux de difficulté. Elle permet de lier un type de question à plusieurs niveaux de difficulté et vice versa. Chaque association est unique pour un type de question et un niveau de difficulté donnés.';


COMMENT ON TABLE game_rules
    IS 'Définit les règles de barème (temps, bonus, pénalités) pour chaque combinaison de type de question et niveau de difficulté.';
COMMENT ON COLUMN game_rules.answer_options_count
    IS 'Nombre de réponses proposées pour une question. Pour les questions de type Vrai/Faux, ce nombre est fixé à 0 car les réponses sont implicites.';
COMMENT ON COLUMN game_rules.points_per_good_answer
    IS 'Points attribués pour une réponse correcte. Doit être supérieur ou égal à 0.';
COMMENT ON COLUMN game_rules.points_penalty_per_wrong_answer
    IS 'Pénalité appliquée en cas de mauvaise réponse. Doit être supérieur ou égal à 1 si les pénalités sont activées.';
COMMENT ON COLUMN game_rules.timer_seconds
    IS 'Durée du timer en secondes pour répondre à la question. Doit être supérieur ou égal à 0.';
COMMENT ON COLUMN game_rules.points_timer_multiplier
    IS 'Multiplicateur des points attribués en cas de bonne réponse si le timer est activé. Doit être supérieur ou égal à 1.0. Un multiplicateur de 1 signifie que le temps n''a pas d''impact sur les points.';
COMMENT ON COLUMN game_rules.points_penalty_multiplier
    IS 'Multiplicateur des points attribués en cas de bonne réponse si les pénalités sont activé. Doit être supérieur ou égal à 1.0. Un multiplicateur de 1 signifie que les pénalités n''ont pas d''impact sur les points.';
COMMENT ON COLUMN game_rules.combo_2_bonus
    IS 'Bonus de points pour 2 bonnes réponses consécutives. Doit être supérieur ou égal à 0.';
COMMENT ON COLUMN game_rules.combo_3_bonus
    IS 'Bonus de points pour 3 bonnes réponses consécutives. Doit être supérieur ou égal à 0.';
COMMENT ON COLUMN game_rules.combo_4_bonus
    IS 'Bonus de points pour 4 bonnes réponses consécutives. Doit être supérieur ou égal à 0.';
COMMENT ON COLUMN game_rules.combo_5_bonus
    IS 'Bonus de points pour 5 bonnes réponses consécutives. Doit être supérieur ou égal à 0.';





COMMENT ON TRIGGER check_category_theme ON quizzes
    IS 'Vérifie que la catégorie associée au quiz correspond au thème du quiz lors de l’insertion ou de la mise à jour d’un quiz.';
COMMENT ON TRIGGER trg_forbid_delete_default_theme ON themes
    IS 'Empêche la suppression du thème par défaut.';
COMMENT ON TRIGGER trg_forbid_delete_difficulty ON difficulty_levels
    IS 'Empêche la suppression des niveaux de difficulté sauf pour les niveaux dont le label est "SPÉCIAL".';
COMMENT ON TRIGGER trg_forbid_delete_reference_rule ON game_rules
    IS 'Empêche la suppression des règles de jeu liées à une difficulté de référence.';
COMMENT ON TRIGGER trg_forbid_disable_reference_rule ON game_rules
    IS 'Empêche la désactivation d’une règle liée à une difficulté de référence.';
COMMENT ON TRIGGER trg_check_game_rule_exists ON question_type_difficulty
    IS 'Vérifie l’existence d’une règle de jeu avant d’insérer une relation entre type de question et niveau de difficulté.';
COMMENT ON TRIGGER trg_check_theme_on_quiz_removed ON quizzes
    IS 'Désactive automatiquement le thème si tous les quiz associés sont désactivés après la suppression d’un quiz.';
COMMENT ON TRIGGER trg_check_theme_on_quiz_disabled ON quizzes
    IS 'Désactive automatiquement le thème si tous les quiz associés sont désactivés après la désactivation d’un quiz.';
COMMENT ON TRIGGER trg_auto_disable_quiz_on_link_delete ON quiz_questions
    IS 'Désactive automatiquement un quiz si toutes les questions associées sont désactivées après la suppression d’une question d’un quiz.';
COMMENT ON TRIGGER trg_create_game_rule_on_question_type_insert ON question_types
    IS 'Crée automatiquement une règle de jeu par défaut lors de l’insertion d’un type de question.';
COMMENT ON TRIGGER trg_sync_question_type_difficulty_on_game_rule_insert ON game_rules
    IS 'Synchronise la table question_type_difficulty lors de l’insertion d’une règle de jeu. Si l’association n’existe pas, elle est créée automatiquement.';
COMMENT ON TRIGGER trg_cleanup_question_type_difficulty_on_game_rule_delete ON game_rules
    IS 'Supprime une association type de question / difficulté de la table question_type_difficulty s’il n’existe plus de règle de jeu correspondante après suppression.';
COMMENT ON TRIGGER trg_auto_disable_quiz_on_question_disable ON questions
    IS 'Désactive automatiquement un quiz si une question associée est désactivée et que le quiz n’a plus assez de questions actives.';
