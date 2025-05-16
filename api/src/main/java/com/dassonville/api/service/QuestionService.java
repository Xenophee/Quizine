package com.dassonville.api.service;


import com.dassonville.api.dto.QuestionAdminDTO;
import com.dassonville.api.dto.QuestionInsertDTO;
import com.dassonville.api.dto.QuestionUpdateDTO;
import com.dassonville.api.exception.ActionNotAllowedException;
import com.dassonville.api.exception.AlreadyExistException;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.mapper.QuestionMapper;
import com.dassonville.api.model.Question;
import com.dassonville.api.repository.QuestionRepository;
import com.dassonville.api.repository.QuizRepository;
import com.dassonville.api.util.TextUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static com.dassonville.api.constant.AppConstants.MINIMUM_QUIZ_QUESTIONS;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private static final Logger logger = LoggerFactory.getLogger(QuestionService.class);

    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;

    private final QuizRepository quizRepository;
    private final QuizService quizService;


    @Transactional
    public QuestionAdminDTO create(long quizId, QuestionInsertDTO dto) {

        if (!quizRepository.existsById(quizId)) {
            logger.warn("Le quiz avec l'ID {}, n'a pas été trouvé.", quizId);
            throw new NotFoundException("Le quiz n'a pas été trouvé.");
        }

        String normalizedNewText = TextUtils.normalizeText(dto.text());
        logger.debug("Titre normalisé : {}, depuis {}", normalizedNewText, dto.text());

        if (questionRepository.existsByQuizIdAndTextIgnoreCase(quizId, normalizedNewText)) {
            logger.warn("La question {}, existe déjà pour ce quiz.", normalizedNewText);
            throw new AlreadyExistException("La question existe déjà pour ce quiz.");
        }

        Question questionToCreate = questionMapper.toModel(dto, quizId);

        Question questionCreated = questionRepository.save(questionToCreate);

        return questionMapper.toAdminDTO(questionCreated);
    }


    public QuestionAdminDTO update(long id, QuestionUpdateDTO dto) {

        Question questionToUpdate = findQuestionById(id);

        String normalizedNewText = TextUtils.normalizeText(dto.text());
        logger.debug("Titre normalisé : {}, depuis {}", normalizedNewText, dto.text());

        if (questionRepository.existsByQuizIdAndTextIgnoreCaseAndIdNot(questionToUpdate.getQuiz().getId(), normalizedNewText, id)) {
            logger.warn("La question {}, existe déjà.", normalizedNewText);
            throw new AlreadyExistException("La question existe déjà.");
        }

        questionMapper.updateModelFromDTO(dto, questionToUpdate);

        Question updatedQuestion = questionRepository.save(questionToUpdate);

        return questionMapper.toAdminDTO(updatedQuestion);
    }


    public void delete(long id) {

        Question question = findQuestionById(id);

        questionRepository.deleteById(id);

        disableQuizIfTooFewActiveQuestions(question.getQuiz().getId());
    }


    public void updateVisibility(long id, boolean visible) {

        Question question = findQuestionById(id);

        if (question.isVisible() == visible) return;

        question.setVisible(visible);

        questionRepository.save(question);

        disableQuizIfTooFewActiveQuestions(question.getQuiz().getId());
    }


    private Question findQuestionById(long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("La question avec l'ID {}, n'a pas été trouvée.", id);
                    return new NotFoundException("La question n'a pas été trouvée.");
                });
    }

    private void disableQuizIfTooFewActiveQuestions(long quizId) {

        int numberOfActiveQuestions = questionRepository.countByQuizIdAndDisabledAtIsNull(quizId);

        if (numberOfActiveQuestions < MINIMUM_QUIZ_QUESTIONS) {
            logger.warn("Le quiz avec l'ID {}, n'a pas suffisamment de questions : {}, il a donc été désactivé.", quizId, numberOfActiveQuestions);
            quizService.updateVisibility(quizId, false);
        } else {
            logger.debug("Le quiz avec l'ID {}, a suffisamment de questions : {}, il reste actif.", quizId, numberOfActiveQuestions);
        }
    }
    
}
