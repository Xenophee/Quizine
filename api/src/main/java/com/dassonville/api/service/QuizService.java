package com.dassonville.api.service;


import com.dassonville.api.dto.*;
import com.dassonville.api.exception.ActionNotAllowedException;
import com.dassonville.api.exception.AlreadyExistException;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.mapper.QuizMapper;
import com.dassonville.api.model.Quiz;
import com.dassonville.api.projection.QuestionForPlayProjection;
import com.dassonville.api.repository.DifficultyLevelRepository;
import com.dassonville.api.repository.QuestionRepository;
import com.dassonville.api.repository.QuizRepository;
import com.dassonville.api.repository.ThemeRepository;
import com.dassonville.api.util.TextUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.dassonville.api.constant.AppConstants.MINIMUM_QUIZ_QUESTIONS;

@Service
@RequiredArgsConstructor
public class QuizService {

    private static final Logger logger = LoggerFactory.getLogger(QuizService.class);

    private final QuizRepository quizRepository;
    private final QuizMapper quizMapper;

    private final ThemeService themeService;

    private final DifficultyLevelRepository difficultyLevelRepository;
    private final QuestionRepository questionRepository;
    private final ThemeRepository themeRepository;



    /**
     * Récupère tous les quiz actifs (non désactivés) pour l'utilisateur final, avec possibilité de filtrer par thème.
     *
     * <p>Cette méthode permet de récupérer tous les quiz qui ne sont pas désactivés
     * (champ {@code disabledAt} à {@code null}). Si des IDs de thèmes sont fournis, elle filtre
     * les quiz en fonction de ces thèmes. Les résultats sont paginés et mappés vers des DTOs
     * adaptés pour l'affichage public.</p>
     *
     * @param themeIds une liste d'IDs de thèmes pour filtrer les quiz, peut être vide ou {@code null}
     * @param pageable les informations de pagination et de tri
     * @return une page de {@link QuizPublicDTO} contenant les quiz actifs
     */
    public Page<QuizPublicDTO> findAllByThemeIdsForUser(List<Long> themeIds, Pageable pageable) {

        if (CollectionUtils.isEmpty(themeIds)) {
            return quizRepository.findAllByDisabledAtIsNull(pageable)
                    .map(quizMapper::toPublicDTO);
        }

        boolean hasValidThemeIds = themeRepository.existsByDisabledAtIsNullAndIdIn(themeIds);

        if (!hasValidThemeIds) {
            logger.warn("Aucun thème valide trouvé parmi les IDs soumis : {}", themeIds);
            return Page.empty(pageable);
        }

        return quizRepository.findAllByDisabledAtIsNullAndThemeIdIn(themeIds, pageable)
                .map(quizMapper::toPublicDTO);
    }


    /**
     * Récupère les détails publics d'un quiz actif (non désactivé) à destination de l'utilisateur final.
     *
     * <p>Cette méthode recherche un quiz par son identifiant uniquement s'il n'est pas désactivé
     * (champ {@code disabledAt} à {@code null}). Si le quiz est trouvé, il est mappé vers un
     * {@link QuizPublicDetailsDTO} à l'aide du {@link QuizMapper}, en incluant notamment le
     * nombre de questions actives associées.</p>
     *
     * <p>Si aucun quiz correspondant n'est trouvé, une exception est levée.</p>
     *
     * @param id l'identifiant du quiz à rechercher
     * @return un {@link QuizPublicDetailsDTO} contenant les informations publiques du quiz
     * @throws NotFoundException si aucun quiz actif ne correspond à l'identifiant fourni
     */
    @Transactional
    public QuizPublicDetailsDTO findByIdForUser(long id) {
        Quiz quiz = quizRepository.findByIdAndDisabledAtIsNullAndThemeDisabledAtIsNullAndCategoryDisabledAtIsNull(id)
                .orElseThrow(() -> {
                    logger.warn("Le quiz avec l'ID {}, n'a pas été trouvé.", id);
                    return new NotFoundException("Le quiz n'a pas été trouvé.");
                });

        return quizMapper.toPublicDetailsDTO(quiz, questionRepository);
    }



    /**
     * Récupère tous les quiz associés à un thème pour l'administration, avec possibilité de filtrer par visibilité.
     *
     * <p>Cette méthode permet de récupérer tous les quiz d'un thème spécifique, en filtrant
     * selon leur visibilité (actif ou désactivé). Elle utilise le {@link QuizMapper} pour
     * convertir les entités {@link Quiz} en {@link QuizAdminDTO} pour l'affichage dans l'interface
     * d'administration.</p>
     *
     * @param themeId l'identifiant du thème dont on souhaite récupérer les quiz
     * @param visible un booléen indiquant si on souhaite récupérer les quiz visibles (actifs) ou non
     * @param pageable les informations de pagination et de tri
     * @return une page de {@link QuizAdminDTO} contenant les quiz du thème spécifié
     * @throws NotFoundException si le thème avec l'ID spécifié n'existe pas
     */
    @Transactional
    public Page<QuizAdminDTO> findAllByThemeIdForAdmin(long themeId, Boolean visible, Pageable pageable) {

        if (!themeRepository.existsById(themeId)) {
            logger.warn("Le thème avec l'ID {}, n'a pas été trouvé.", themeId);
            throw new NotFoundException("Le thème n'a pas été trouvé.");
        }

        Page<Quiz> quizzes;

        if (Boolean.TRUE.equals(visible)) {
            quizzes = quizRepository.findByThemeIdAndDisabledAtIsNull(themeId, pageable);
        } else if (Boolean.FALSE.equals(visible)) {
            quizzes = quizRepository.findByThemeIdAndDisabledAtIsNotNull(themeId, pageable);
        } else {
            quizzes = quizRepository.findByThemeId(themeId, pageable);
        }

        return quizzes.map(quizMapper::toAdminDTO);
    }


    /**
     * Récupère les détails d'un quiz pour l'administration.
     *
     * <p>Cette méthode recherche un quiz par son identifiant et le mappe vers un
     * {@link QuizAdminDetailsDTO} à l'aide du {@link QuizMapper}. Elle est destinée à être utilisée
     * par les administrateurs pour obtenir des informations détaillées sur un quiz.</p>
     *
     * @param id l'identifiant du quiz à rechercher
     * @return un {@link QuizAdminDetailsDTO} contenant les détails du quiz
     * @throws NotFoundException si aucun quiz ne correspond à l'identifiant fourni
     */
    @Transactional
    public QuizAdminDetailsDTO findByIdForAdmin(long id) {
        Quiz quiz = findById(id);

        return quizMapper.toAdminDetailsDTO(quiz);
    }


    /**
     * Récupère toutes les questions d'un quiz pour le jeu, en fonction du niveau de difficulté.
     *
     * <p>Cette méthode recherche un quiz par son identifiant et un niveau de difficulté spécifique,
     * puis récupère les questions associées à ce quiz. Les questions sont mélangées et mappées vers
     * des DTOs adaptés pour le jeu, en respectant le nombre maximum de réponses affichées par le niveau
     * de difficulté.</p>
     *
     * @param id l'identifiant du quiz
     * @param difficultyLevelId l'identifiant du niveau de difficulté
     * @return une liste de {@link QuestionForPlayDTO} contenant les questions pour le jeu
     * @throws NotFoundException si le quiz ou le niveau de difficulté n'est pas trouvé
     */
    @Transactional
    public List<QuestionForPlayDTO> findAllQuestionsByQuizIdForPlay(long id, long difficultyLevelId) {

        if (!quizRepository.existsByIdAndDisabledAtIsNull(id)) {
            logger.warn("Le quiz avec l'ID {}, n'a pas été trouvé.", id);
            throw new NotFoundException("Le quiz n'a pas été trouvé.");
        }

        byte answerOptionsCount = difficultyLevelRepository.findAnswerOptionsCountByIdAndDisabledAtIsNull(difficultyLevelId)
                .orElseThrow(() -> {
                    logger.warn("Le niveau de difficulté avec l'ID {}, n'a pas été trouvé.", difficultyLevelId);
                    return new NotFoundException("Le niveau de difficulté n'a pas été trouvé.");
                });

        List<QuestionForPlayProjection> questions = new ArrayList<>(questionRepository.findByQuizIdAndDisabledAtIsNullAndAnswersDisabledAtIsNull(id));

        Collections.shuffle(questions);

        return questions.stream()
                .map(question -> mapToPlayDTO(question, answerOptionsCount))
                .toList();
    }


    /**
     * Crée un nouveau quiz à partir des informations fournies dans le DTO.
     *
     * <p>Cette méthode normalise le titre du quiz, vérifie s'il n'existe pas déjà un quiz avec
     * le même titre, puis crée et sauvegarde le nouveau quiz dans la base de données.</p>
     *
     * @param dto les informations du quiz à créer contenues dans un {@link QuizUpsertDTO}
     * @return un {@link QuizAdminDetailsDTO} contenant les détails du quiz créé
     * @throws AlreadyExistException si un quiz avec le même titre existe déjà
     */
    public QuizAdminDetailsDTO create(QuizUpsertDTO dto) {

        String normalizedNewText = TextUtils.normalizeText(dto.title());
        logger.debug("Titre normalisé : {}, depuis {}", normalizedNewText, dto.title());

        if (quizRepository.existsByTitleIgnoreCase(normalizedNewText)) {
            logger.warn("Le quiz avec le titre {}, existe déjà.", normalizedNewText);
            throw new AlreadyExistException("Le quiz existe déjà.");
        }

        Quiz quizToCreate = quizMapper.toModel(dto);

        Quiz quizCreated = quizRepository.save(quizToCreate);

        return quizMapper.toAdminDetailsDTO(quizCreated);
    }


    /**
     * Met à jour un quiz existant avec les nouvelles informations fournies dans le DTO.
     *
     * <p>Cette méthode vérifie d'abord si le quiz existe, puis si le titre normalisé du quiz
     * n'est pas déjà utilisé par un autre quiz. Si ces conditions sont remplies, elle met à jour
     * le quiz et le sauvegarde dans la base de données.</p>
     *
     * @param id l'identifiant du quiz à mettre à jour
     * @param dto les nouvelles informations du quiz contenues dans un {@link QuizUpsertDTO}
     * @return un {@link QuizAdminDetailsDTO} contenant les détails du quiz mis à jour
     * @throws NotFoundException si le quiz avec l'ID spécifié n'existe pas
     * @throws AlreadyExistException si un autre quiz porte déjà le même titre
     */
    @Transactional
    public QuizAdminDetailsDTO update(long id, QuizUpsertDTO dto) {

        Quiz quizToUpdate = findById(id);

        String normalizedNewText = TextUtils.normalizeText(dto.title());
        logger.debug("Titre normalisé : {}, depuis {}", normalizedNewText, dto.title());

        if (quizRepository.existsByTitleIgnoreCaseAndIdNot(normalizedNewText, id)) {
            logger.warn("Le quiz avec le titre {}, existe déjà.", normalizedNewText);
            throw new AlreadyExistException("Le quiz existe déjà.");
        }

        quizMapper.updateModelFromDTO(dto, quizToUpdate);

        Quiz updatedQuiz = quizRepository.save(quizToUpdate);

        return quizMapper.toAdminDetailsDTO(updatedQuiz);
    }


    /**
     * Supprime un quiz par son identifiant.
     *
     * <p>Cette méthode recherche le quiz par son identifiant, le supprime de la base de données,
     * puis désactive le thème associé si aucun autre quiz actif n'est lié à ce thème.</p>
     *
     * @param id l'identifiant du quiz à supprimer
     * @throws NotFoundException si le quiz avec l'ID spécifié n'existe pas
     */
    public void delete(long id) {

        Quiz quiz = findById(id);

        quizRepository.deleteById(id);

        disableThemeIfNoActiveQuizzes(quiz.getTheme().getId());
    }


    /**
     * Met à jour la visibilité d'un quiz.
     *
     * <p>Cette méthode active ou désactive un quiz en fonction de l'état de visibilité fourni.
     * Si le quiz est activé, elle vérifie qu'il contient au moins un nombre minimum de questions actives.
     * Si le quiz est désactivé, elle vérifie s'il reste des quiz actifs dans le thème associé et désactive
     * le thème si nécessaire.</p>
     *
     * <p>Si le quiz est déjà dans l'état de visibilité souhaité, aucune action n'est effectuée.</p>
     * <p>Si le quiz est activé, il doit contenir un minimum de questions actives.</p>
     * <p>Si le quiz est désactivé, le thème associé est vérifié pour voir s'il reste encore des quiz actifs.</p>
     *
     * @param id l'identifiant du quiz à mettre à jour
     * @param visible l'état de visibilité souhaité (true pour activer, false pour désactiver)
     */
    public void updateVisibility(long id, boolean visible) {

        Quiz quiz = findById(id);

        if (quiz.isVisible() == visible) return;

        if (visible) {
            logger.debug("Demande d'activation du quiz avec l'ID {}, vérification du nombre de questions actives.", id);
            hasMinimumQuestions(id);
        }

        quiz.setVisible(visible);

        quizRepository.save(quiz);

        if (!visible) {
            logger.debug("Désactivation demandée du quiz avec l'ID {}, vérification du nombre de quiz actifs restant.", id);
            disableThemeIfNoActiveQuizzes(quiz.getTheme().getId());
        }
    }


    /**
     * Recherche un quiz par son identifiant.
     *
     * <p>Cette méthode tente de trouver un quiz par son identifiant.
     * Si le quiz n'est pas trouvé, elle lève une exception.</p>
     *
     * @param id l'identifiant du quiz à rechercher
     * @return le {@link Quiz} trouvé
     * @throws NotFoundException si aucun quiz ne correspond à l'identifiant fourni
     */
    private Quiz findById(long id) {
        return quizRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Le quiz avec l'ID {}, n'a pas été trouvé.", id);
                    return new NotFoundException("Le quiz n'a pas été trouvé.");
                });
    }

    /**
     * Vérifie si un quiz contient au moins un nombre minimum de questions actives.
     *
     * <p>Cette méthode compte le nombre de questions actives associées à un quiz.
     * Si ce nombre est inférieur au minimum requis, elle lève une exception.</p>
     *
     * @param id l'identifiant du quiz à vérifier
     * @throws ActionNotAllowedException si le quiz ne contient pas suffisamment de questions actives
     */
    private void hasMinimumQuestions(long id) {

        int numberOfActiveQuestions = quizRepository.countByIdAndQuestionsDisabledAtIsNull(id);

        if (numberOfActiveQuestions < MINIMUM_QUIZ_QUESTIONS) {
            logger.warn("Le quiz avec l'ID {}, ne peut pas être activé car il n'a pas assez de questions.", id);
            throw new ActionNotAllowedException("Le quiz ne peut pas être activé, car il ne contient pas au moins " + MINIMUM_QUIZ_QUESTIONS + " questions.");
        } else {
            logger.debug("Le quiz avec l'ID {}, a suffisamment de questions : {} pour être activé.", id, numberOfActiveQuestions);
        }
    }


    /**
     * Désactive un thème si aucun quiz actif n'est associé à ce thème.
     *
     * <p>Cette méthode vérifie le nombre de quiz actifs associés à un thème.
     * Si aucun quiz actif n'est trouvé, elle désactive le thème.</p>
     *
     * @param themeId l'identifiant du thème à vérifier
     */
    private void disableThemeIfNoActiveQuizzes(long themeId) {

        int numberOfActiveQuizzes = quizRepository.countByThemeIdAndDisabledAtIsNull(themeId);

        if (numberOfActiveQuizzes == 0) {
            logger.warn("Le thème avec l'ID {} n'a pas de quiz actifs et va être désactivé.", themeId);
            themeService.updateVisibility(themeId, false);
        } else {
            logger.debug("Le thème avec l'ID {} a {} quiz actifs et reste donc actif.", themeId, numberOfActiveQuizzes);
        }
    }


    /**
     * Mappe les informations d'une question pour le jeu à partir d'une projection.
     *
     * <p>Cette méthode prend une projection de question et un nombre maximum de réponses,
     * puis crée un DTO de question pour le jeu en filtrant les réponses correctes et incorrectes,
     * en limitant le nombre de bonnes réponses si nécessaire, et en mélangeant les réponses finales.</p>
     *
     * @param question la {@link QuestionForPlayProjection} de la question à mapper
     * @param answerOptionsCount le nombre maximum de réponses à inclure dans le DTO
     * @return un {@link QuestionForPlayDTO} contenant les détails de la question et ses réponses
     */
    private QuestionForPlayDTO mapToPlayDTO(QuestionForPlayProjection question, byte answerOptionsCount) {
        List<QuestionForPlayProjection.AnswersForPlay> allAnswers = question.getAnswers();

        // Filtre les réponses correctes
        List<QuestionForPlayProjection.AnswersForPlay> correctAnswers = allAnswers.stream()
                .filter(QuestionForPlayProjection.AnswersForPlay::getIsCorrect)
                .toList();

        if (correctAnswers.isEmpty()) {
            logger.error("La question {} n'a pas de réponse correcte.", question.getId());
            throw new IllegalStateException("La question " + question.getId() + " n’a pas de réponse correcte.");
        }

        if (answerOptionsCount == 0) { // Réponse libre
            return new QuestionForPlayDTO(question.getId(), question.getText(), List.of());
        }

        // Réponses incorrectes
        List<QuestionForPlayProjection.AnswersForPlay> incorrects = allAnswers.stream()
                .filter(answer -> !answer.getIsCorrect())
                .toList();

        // Calcul du nombre restant de places pour les mauvaises réponses
        int slotsForIncorrects = answerOptionsCount - correctAnswers.size();

        // Si trop de bonnes réponses, on les limite (rare cas, mais à gérer)
        List<QuestionForPlayProjection.AnswersForPlay> limitedCorrects = correctAnswers.size() > answerOptionsCount
                ? correctAnswers.subList(0, answerOptionsCount)
                : correctAnswers;

        // Sélection d'un nombre aléatoire d'incorrects s’il reste des slots
        List<QuestionForPlayProjection.AnswersForPlay> selectedIncorrects = slotsForIncorrects > 0
                ? incorrects.stream().sorted((a, b) -> ThreadLocalRandom.current().nextInt(-1, 2))
                .limit(slotsForIncorrects)
                .toList()
                : List.of();

        // Fusion et mapping
        List<AnswerForPlayDTO> finalAnswers = Stream.concat(
                        limitedCorrects.stream(),
                        selectedIncorrects.stream()
                )
                .map(answer -> new AnswerForPlayDTO(answer.getId(), answer.getText()))
                .collect(Collectors.toList());

        // Mélange
        Collections.shuffle(finalAnswers);

        return new QuestionForPlayDTO(question.getId(), question.getText(), finalAnswers);
    }


}
