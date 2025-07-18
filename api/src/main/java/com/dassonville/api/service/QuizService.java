package com.dassonville.api.service;


import com.dassonville.api.dto.request.QuizUpsertDTO;
import com.dassonville.api.dto.response.QuizAdminDTO;
import com.dassonville.api.dto.response.QuizAdminDetailsDTO;
import com.dassonville.api.dto.response.QuizPublicDTO;
import com.dassonville.api.dto.response.QuizPublicDetailsDTO;
import com.dassonville.api.exception.ActionNotAllowedException;
import com.dassonville.api.exception.AlreadyExistException;
import com.dassonville.api.exception.ErrorCode;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.mapper.QuizMapper;
import com.dassonville.api.model.Quiz;
import com.dassonville.api.repository.*;
import com.dassonville.api.util.TextUtils;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static com.dassonville.api.constant.AppConstants.MIN_ACTIVE_QUESTIONS_PER_QUIZ;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuizMapper quizMapper;

    private final QuestionRepository questionRepository;
    private final ThemeRepository themeRepository;
    private final CategoryRepository categoryRepository;
    private final MasteryLevelRepository masteryLevelRepository;




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
            log.warn("Aucun thème valide trouvé parmi les IDs soumis : {}", themeIds);
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
        Quiz quiz = quizRepository.findByIdAndDisabledAtIsNullEverywhere(id)
                .orElseThrow(() -> {
                    log.warn("Le quiz avec l'ID {}, n'a pas été trouvé.", id);
                    return new NotFoundException(ErrorCode.QUIZ_NOT_FOUND);
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
            log.warn("Le thème avec l'ID {}, n'a pas été trouvé.", themeId);
            throw new NotFoundException(ErrorCode.THEME_NOT_FOUND);
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

        log.debug("Type du quiz : {}", dto.type().getType());

        String normalizedNewText = TextUtils.normalizeText(dto.title());
        log.debug("Titre normalisé : {}, depuis {}", normalizedNewText, dto.title());

        if (quizRepository.existsByTitleIgnoreCase(normalizedNewText)) {
            log.warn("Le quiz avec le titre {}, existe déjà.", normalizedNewText);
            throw new AlreadyExistException(ErrorCode.QUIZ_ALREADY_EXISTS);
        }

        if (!themeRepository.existsById(dto.themeId())) {
            log.warn("L'ID {} n'est pas un thème valide. Enregistrement du quiz impossible.", dto.themeId());
            throw new NotFoundException(ErrorCode.THEME_NOT_FOUND);
        }

        if (dto.categoryId() != null && !categoryRepository.existsById(dto.categoryId())) {
            log.warn("L'ID {} n'est pas une catégorie valide. Enregistrement du quiz impossible.", dto.categoryId());
            throw new NotFoundException(ErrorCode.CATEGORY_NOT_FOUND);
        }

        if (!masteryLevelRepository.existsById(dto.masteryLevelId())) {
            log.warn("L'ID {} n'est pas un niveau de maîtrise valide. Enregistrement du quiz impossible.", dto.masteryLevelId());
            throw new NotFoundException(ErrorCode.MASTERY_LEVEL_NOT_FOUND);
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

        Quiz quiz = findById(id);

        String normalizedNewText = TextUtils.normalizeText(dto.title());
        log.debug("Titre normalisé : {}, depuis {}", normalizedNewText, dto.title());

        if (quizRepository.existsByTitleIgnoreCaseAndIdNot(normalizedNewText, id)) {
            log.warn("Le quiz avec le titre {}, existe déjà.", normalizedNewText);
            throw new AlreadyExistException(ErrorCode.QUIZ_ALREADY_EXISTS);
        }

        if (quizRepository.existsInvalidQuestionTypeForQuiz(id, dto.type().getType())) {
            log.warn("Le quiz avec l'ID {}, contient des questions de type invalide pour le type de quiz souhaité {}. Pas de modification possible.", id, dto.type().getType());
            throw new ActionNotAllowedException(ErrorCode.QUIZ_CONTAINS_INVALID_QUESTION_TYPE);
        }

        if (!themeRepository.existsById(dto.themeId())) {
            log.warn("Le quiz avec l'ID {}, contient un thème invalide (ID {}). Pas de modification possible.", id, dto.themeId());
            throw new NotFoundException(ErrorCode.THEME_NOT_FOUND);
        }

        if (dto.categoryId() != null && !categoryRepository.existsById(dto.categoryId())) {
            log.warn("Le quiz avec l'ID {}, contient une catégorie invalide (ID {}). Pas de modification possible.", id, dto.categoryId());
            throw new NotFoundException(ErrorCode.CATEGORY_NOT_FOUND);
        }

        if (!masteryLevelRepository.existsById(dto.masteryLevelId())) {
            log.warn("Le quiz avec l'ID {}, contient un niveau de maîtrise invalide (ID {}). Pas de modification possible.", id, dto.masteryLevelId());
            throw new NotFoundException(ErrorCode.MASTERY_LEVEL_NOT_FOUND);
        }

        quizMapper.updateModelFromDTO(dto, quiz);

        return quizMapper.toAdminDetailsDTO(quiz);
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
    @Transactional
    public void delete(long id) {

        if (!quizRepository.existsById(id)) {
            log.warn("Le quiz avec l'ID {}, n'a pas été trouvé.", id);
            throw new NotFoundException(ErrorCode.QUIZ_NOT_FOUND);
        }

        quizRepository.deleteById(id);
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
     * @throws NotFoundException si le quiz avec l'ID spécifié n'existe pas
     * @throws ActionNotAllowedException si le quiz ne peut pas être activé en raison d'un nombre insuffisant de questions actives
     */
    @Transactional
    public void updateVisibility(long id, boolean visible) {

        Quiz quiz = findById(id);

        if (quiz.isVisible() == visible) return;

        if (visible) {
            log.debug("Demande d'activation du quiz avec l'ID {}, vérification du nombre de questions actives.", id);
            assertMinimumActiveQuestions(id);
        }

        quiz.setVisible(visible);
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
                    log.warn("Le quiz avec l'ID {}, n'a pas été trouvé.", id);
                    return new NotFoundException(ErrorCode.QUIZ_NOT_FOUND);
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
    private void assertMinimumActiveQuestions(long id) {

        int numberOfActiveQuestions = quizRepository.countByIdAndQuestionsDisabledAtIsNull(id);

        if (numberOfActiveQuestions < MIN_ACTIVE_QUESTIONS_PER_QUIZ) {
            log.warn("Le quiz avec l'ID {}, ne peut pas être activé car il n'a pas assez de questions.", id);
            throw new ActionNotAllowedException(ErrorCode.QUIZ_CONTAINS_NOT_ENOUGH_QUESTIONS, MIN_ACTIVE_QUESTIONS_PER_QUIZ);
        } else {
            log.debug("Le quiz avec l'ID {}, a suffisamment de questions : {} pour être activé.", id, numberOfActiveQuestions);
        }
    }
}
