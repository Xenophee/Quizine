package com.dassonville.api.service;


import com.dassonville.api.dto.request.ClassicQuestionInsertDTO;
import com.dassonville.api.dto.request.ClassicQuestionUpdateDTO;
import com.dassonville.api.dto.request.QuestionUpsertDTO;
import com.dassonville.api.dto.request.TrueFalseQuestionUpsertDTO;
import com.dassonville.api.dto.response.QuestionAdminDTO;
import com.dassonville.api.exception.ActionNotAllowedException;
import com.dassonville.api.exception.AlreadyExistException;
import com.dassonville.api.exception.ErrorCode;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.mapper.QuestionMapper;
import com.dassonville.api.model.Question;
import com.dassonville.api.model.Quiz;
import com.dassonville.api.repository.QuestionRepository;
import com.dassonville.api.repository.QuestionTypeRepository;
import com.dassonville.api.repository.QuizRepository;
import com.dassonville.api.util.TextUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;

    private final QuizRepository quizRepository;

    private final QuestionTypeRepository questionTypeRepository;



    /**
     * Crée une nouvelle question pour un quiz donné.
     *
     * <p>Cette méthode vérifie si le quiz existe, normalise le texte de la question,
     * vérifie qu'aucune question avec le même texte n'existe déjà pour le quiz, puis
     * crée et sauvegarde la nouvelle question dans la base de données.</p>
     *
     * @param quizId l'identifiant du quiz auquel la question sera associée
     * @param dto les informations de la question à créer contenues dans un {@link ClassicQuestionInsertDTO}
     * @return un {@link QuestionAdminDTO} contenant les détails de la question créée
     * @throws NotFoundException si le quiz avec l'ID spécifié n'existe pas
     * @throws ActionNotAllowedException si le type de question fourni n'est pas supporté pour le quiz
     * @throws AlreadyExistException si une question avec le même texte existe déjà pour le quiz
     */
    @Transactional
    public QuestionAdminDTO create(long quizId, QuestionUpsertDTO dto) {

        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> {
                    log.warn("Le quiz avec l'ID {}, n'a pas été trouvé.", quizId);
                    return new NotFoundException(ErrorCode.QUIZ_NOT_FOUND);
                });

        if (!questionTypeRepository.existsByCodeAndQuizTypes_Quizzes_Id(dto.type().getType(), quizId)) {
            log.warn("Le type de question « {} » n'est pas supporté pour le quiz avec l'id {}.", dto.type(), quizId);
            throw new ActionNotAllowedException(ErrorCode.QUESTION_TYPE_NOT_SUPPORTED);
        }

        String normalizedNewText = TextUtils.normalizeText(dto.text());
        log.debug("Titre normalisé : {}, depuis {}", normalizedNewText, dto.text());

        if (questionRepository.existsByQuizzesIdAndTextIgnoreCase(quizId, normalizedNewText)) {
            log.warn("La question {}, existe déjà pour ce quiz.", normalizedNewText);
            throw new AlreadyExistException(ErrorCode.QUESTION_ALREADY_EXISTS);
        }

        Question questionToCreate;

        if (dto instanceof ClassicQuestionInsertDTO) {
            questionToCreate = questionMapper.toModel((ClassicQuestionInsertDTO) dto, quizId);
        } else {
            questionToCreate = questionMapper.toModel((TrueFalseQuestionUpsertDTO) dto, quizId);
        }

        questionToCreate.getQuizzes().add(quiz);
        quiz.getQuestions().add(questionToCreate);

        Question questionCreated = questionRepository.save(questionToCreate);

        return questionMapper.toAdminDTO(questionCreated);
    }


    /**
     * Met à jour une question existante avec les nouvelles informations fournies dans le DTO.
     *
     * <p>Cette méthode recherche la question par son ID, normalise le nouveau texte,
     * vérifie qu'aucune autre question avec le même texte n'existe déjà pour le quiz,
     * puis met à jour et sauvegarde la question dans la base de données.</p>
     *
     *
     * @param questionId l'identifiant de la question à mettre à jour
     * @param dto les nouvelles informations de la question contenues dans un {@link ClassicQuestionUpdateDTO}
     * @return un {@link QuestionAdminDTO} contenant les détails de la question mise à jour
     * @throws NotFoundException si la question avec l'ID spécifié n'existe pas
     * @throws ActionNotAllowedException si le type de question fourni ne correspond pas à celui attendu pour la question
     * @throws AlreadyExistException si une autre question avec le même texte existe déjà pour le quiz
     */
    @Transactional
    public QuestionAdminDTO update(long questionId, QuestionUpsertDTO dto) {

        Question question = findQuestionById(questionId);

        if (!questionRepository.existsByIdAndQuestionTypeCode(questionId, dto.type().getType())) {
            log.warn("Les informations fournies ne correspondent pas au schéma attendu pour la question avec l'ID {}.", questionId);
            throw new ActionNotAllowedException(ErrorCode.QUESTION_AND_QUESTION_TYPE_MISMATCH);
        }

        String normalizedNewText = TextUtils.normalizeText(dto.text());
        log.debug("Titre normalisé : {}, depuis {}", normalizedNewText, dto.text());

        if (questionRepository.existsByQuizzesIdAndTextIgnoreCaseAndIdNot(question.getQuizzes().getFirst().getId(), normalizedNewText, questionId)) {
            log.warn("La question {}, existe déjà.", normalizedNewText);
            throw new AlreadyExistException(ErrorCode.QUESTION_ALREADY_EXISTS);
        }

        if (dto instanceof ClassicQuestionUpdateDTO) {
            questionMapper.updateModelFromDTO((ClassicQuestionUpdateDTO) dto, question);
        } else {
            questionMapper.updateModelFromDTO((TrueFalseQuestionUpsertDTO) dto, question);
        }

        return questionMapper.toAdminDTO(question);
    }


    /**
     * Supprime une question par son identifiant.
     *
     * <p>Cette méthode recherche la question par son ID, la supprime de la base de données,
     * puis désactive le quiz associé si le nombre de questions actives devient insuffisant.</p>
     *
     * @param id l'identifiant de la question à supprimer
     * @throws NotFoundException si la question avec l'ID spécifié n'existe pas
     */
    public void delete(long id) {

        if (!questionRepository.existsById(id)) {
            log.warn("La question avec l'ID {}, n'existe pas.", id);
            throw new NotFoundException(ErrorCode.QUESTION_NOT_FOUND);
        }

        questionRepository.deleteById(id);
    }


    /**
     * Met à jour la visibilité d'une question.
     *
     * <p>Cette méthode active ou désactive une question en fonction de l'état de visibilité fourni.
     * Si la question est désactivée, elle vérifie si le quiz associé doit également être désactivé faute de questions actives suffisantes.</p>
     *
     * @param id l'identifiant de la question à mettre à jour
     * @param visible l'état de visibilité souhaité ({@code true} pour activer, {@code false} pour désactiver)
     * @throws NotFoundException si la question avec l'ID spécifié n'existe pas
     */
    @Transactional
    public void updateVisibility(long id, boolean visible) {

        Question question = findQuestionById(id);

        question.setVisible(visible);
    }


    /**
     * Recherche une question par son identifiant.
     *
     * <p>Cette méthode tente de trouver une question par son identifiant.
     * Si la question n'est pas trouvée, elle lève une exception.</p>
     *
     * @param id l'identifiant de la question à rechercher
     * @return la {@link Question} trouvée
     * @throws NotFoundException si aucune question ne correspond à l'identifiant fourni
     */
    private Question findQuestionById(long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("La question avec l'ID {}, n'a pas été trouvée.", id);
                    return new NotFoundException(ErrorCode.QUESTION_NOT_FOUND);
                });
    }
    
}
