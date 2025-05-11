package com.dassonville.api.service;


import com.dassonville.api.dto.AnswerAdminDTO;
import com.dassonville.api.dto.AnswerUpsertDTO;
import com.dassonville.api.exception.AlreadyExistException;
import com.dassonville.api.exception.InvalidStateException;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.mapper.AnswerMapper;
import com.dassonville.api.model.Answer;
import com.dassonville.api.repository.AnswerRepository;
import com.dassonville.api.repository.DifficultyLevelRepository;
import com.dassonville.api.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

        if (answerRepository.existsByTextIgnoreCaseAndQuestionId(dto.text(), questionId)) {
            logger.warn("La réponse {}, existe déjà.", dto.text());
            throw new AlreadyExistException("La réponse existe déjà.");
        }

        Answer answerToCreate = answerMapper.toModel(dto, questionId);

        Answer answerCreated = answerRepository.save(answerToCreate);

        return answerMapper.toAdminDTO(answerCreated);
    }


    public AnswerAdminDTO update(long id, AnswerUpsertDTO dto) {
        Answer answerToUpdate = answerRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("La réponse avec l'ID {}, n'a pas été trouvée.", id);
                    return new NotFoundException("La réponse n'a pas été trouvée.");
                });

        if (answerRepository.existsByTextIgnoreCaseAndIdNot(dto.text(), id)) {
            logger.warn("La réponse {}, existe déjà.", dto.text());
            throw new AlreadyExistException("La réponse existe déjà.");
        }

        if (answerToUpdate.getIsCorrect() && !dto.isCorrect()) {
            validateCorrectAnswerExists(answerToUpdate.getQuestion().getId(), answerToUpdate.getId());
        }

        answerMapper.updateModelFromDTO(dto, answerToUpdate);

        Answer updatedAnswer = answerRepository.save(answerToUpdate);

        return answerMapper.toAdminDTO(updatedAnswer);
    }


    public void delete(long id) {

        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("La réponse avec l'ID {}, n'a pas été trouvée.", id);
                    return new NotFoundException("La réponse n'a pas été trouvée.");
                });

        validateMinimumActiveAnswers(answer.getQuestion().getId());

        if (answer.getIsCorrect()) {
            validateCorrectAnswerExists(answer.getQuestion().getId(), answer.getId());
        }

        answerRepository.deleteById(id);
    }


    public void toggleVisibility(long id, boolean disable) {
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("La réponse avec l'ID {}, n'a pas été trouvée.", id);
                    return new NotFoundException("La réponse n'a pas été trouvée.");
                });

        validateMinimumActiveAnswers(answer.getQuestion().getId());

        if (answer.getIsCorrect()) {
            validateCorrectAnswerExists(answer.getQuestion().getId(), answer.getId());
        }

        answer.setDisabledAt((disable) ? LocalDateTime.now() : null);

        answerRepository.save(answer);
    }


    public void validateCorrectAnswerExists(long questionId, long excludedAnswerId) {
        boolean hasCorrectAnswer = answerRepository.existsByQuestionIdAndIsCorrectTrueAndIdNotAndDisabledAtIsNull(questionId, excludedAnswerId);

        if (!hasCorrectAnswer) {
            throw new InvalidStateException("Il doit y avoir au moins une réponse correcte active pour cette question.");
        }
    }


    public void validateMinimumActiveAnswers(long questionId) {
        int count = answerRepository.countByQuestionIdAndDisabledAtIsNull(questionId) -1;

        byte maxAnswers = difficultyLevelRepository.findReferenceLevelMaxAnswers()
                .orElseThrow(() -> {
                    logger.error("Le niveau de difficulté de référence n'a pas été trouvé.");
                    return new IllegalStateException("Le niveau de difficulté de référence n'a pas été trouvé.");
                });

        if (count < maxAnswers) {
            logger.warn("Il doit y avoir au moins {} réponses actives pour cette question.", maxAnswers);
            throw new InvalidStateException("Il doit y avoir au moins " + maxAnswers + " réponses actives pour cette question.");
        }
    }

}
