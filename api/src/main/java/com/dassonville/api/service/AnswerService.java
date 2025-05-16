package com.dassonville.api.service;


import com.dassonville.api.dto.AnswerAdminDTO;
import com.dassonville.api.dto.AnswerUpsertDTO;
import com.dassonville.api.exception.ActionNotAllowedException;
import com.dassonville.api.exception.AlreadyExistException;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.mapper.AnswerMapper;
import com.dassonville.api.model.Answer;
import com.dassonville.api.repository.AnswerRepository;
import com.dassonville.api.repository.DifficultyLevelRepository;
import com.dassonville.api.repository.QuestionRepository;
import com.dassonville.api.util.TextUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private static final Logger logger = LoggerFactory.getLogger(AnswerService.class);

    private final AnswerRepository answerRepository;
    private final AnswerMapper answerMapper;

    private final QuestionRepository questionRepository;
    private final DifficultyLevelRepository difficultyLevelRepository;



    public AnswerAdminDTO create(long questionId, AnswerUpsertDTO dto) {

        if (!questionRepository.existsById(questionId)) {
            logger.warn("La question avec l'ID {}, n'a pas été trouvée.", questionId);
            throw new NotFoundException("La question n'a pas été trouvée.");
        }

        String normalizedNewText = TextUtils.normalizeText(dto.text());
        logger.debug("Titre normalisé : {}, depuis {}", normalizedNewText, dto.text());

        if (answerRepository.existsByTextIgnoreCaseAndQuestionId(normalizedNewText, questionId)) {
            logger.warn("La réponse {}, existe déjà.", normalizedNewText);
            throw new AlreadyExistException("La réponse existe déjà.");
        }

        Answer answerToCreate = answerMapper.toModel(dto, questionId);

        Answer answerCreated = answerRepository.save(answerToCreate);

        return answerMapper.toAdminDTO(answerCreated);
    }


    public AnswerAdminDTO update(long id, AnswerUpsertDTO dto) {

        Answer answerToUpdate = findAnswerById(id);

        String normalizedNewText = TextUtils.normalizeText(dto.text());
        logger.debug("Titre normalisé : {}, depuis {}", normalizedNewText, dto.text());

        if (answerRepository.existsByTextIgnoreCaseAndIdNot(normalizedNewText, id)) {
            logger.warn("La réponse {}, existe déjà.", normalizedNewText);
            throw new AlreadyExistException("La réponse existe déjà.");
        }

        if (answerToUpdate.getIsCorrect() && !dto.isCorrect()) {
            logger.debug("La réponse concernée est initialement correcte, vérification de l'existence d'une autre réponse correcte active.");
            validateActiveCorrectAnswerExists(answerToUpdate.getQuestion().getId(), answerToUpdate.getId());
        }

        answerMapper.updateModelFromDTO(dto, answerToUpdate);

        Answer updatedAnswer = answerRepository.save(answerToUpdate);

        return answerMapper.toAdminDTO(updatedAnswer);
    }


    public void delete(long id) {

        Answer answer = findAnswerById(id);

        validateMinimumActiveAnswers(answer.getQuestion().getId());

        if (answer.getIsCorrect()) {
            logger.debug("La réponse concernée est correcte, vérification de l'existence d'une autre réponse correcte active.");
            validateActiveCorrectAnswerExists(answer.getQuestion().getId(), answer.getId());
        }

        answerRepository.deleteById(id);
    }


    public void updateVisibility(long id, boolean visible) {

        Answer answer = findAnswerById(id);

        if (answer.isVisible() == visible) return;

        if (!visible) {
            logger.debug("Désactivation demandée de la réponse avec l'ID {}, vérification du nombre de réponses actives restantes.", id);
            validateMinimumActiveAnswers(answer.getQuestion().getId());
        }

        if (answer.getIsCorrect()) {
            logger.debug("La réponse concernée est correcte, vérification de l'existence d'une autre réponse correcte active.");
            validateActiveCorrectAnswerExists(answer.getQuestion().getId(), answer.getId());
        }

        answer.setVisible(visible);

        answerRepository.save(answer);
    }


    private Answer findAnswerById(long id) {
        return answerRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("La réponse avec l'ID {}, n'a pas été trouvée.", id);
                    return new NotFoundException("La réponse n'a pas été trouvée.");
                });
    }

    private void validateActiveCorrectAnswerExists(long questionId, long excludedAnswerId) {

        boolean hasCorrectAnswer = answerRepository.existsByQuestionIdAndIsCorrectTrueAndIdNotAndDisabledAtIsNull(questionId, excludedAnswerId);

        if (!hasCorrectAnswer) {
            logger.warn("Il doit y avoir au moins une réponse correcte active pour la question avec l'ID {}.", questionId);
            throw new ActionNotAllowedException("Il doit y avoir au moins une réponse correcte active pour cette question.");
        }
    }

    private void validateMinimumActiveAnswers(long questionId) {

        int numberOfActiveAnswers = answerRepository.countByQuestionIdAndDisabledAtIsNull(questionId) -1;
        logger.debug("Nombre de réponses actives restantes pour la question {} après l'opération : {}", questionId, numberOfActiveAnswers);

        byte minimumNumberOfAnswers = difficultyLevelRepository.findReferenceLevelMaxAnswers()
                .orElseThrow(() -> {
                    logger.error("Le niveau de difficulté de référence n'a pas été trouvé.");
                    return new IllegalStateException("Le niveau de difficulté de référence n'a pas été trouvé.");
                });

        if (numberOfActiveAnswers < minimumNumberOfAnswers) {
            logger.warn("Il doit y avoir au moins {} réponses actives pour cette question.", minimumNumberOfAnswers);
            throw new ActionNotAllowedException("Il doit y avoir au moins " + minimumNumberOfAnswers + " réponses actives pour cette question.");
        }
    }

}
