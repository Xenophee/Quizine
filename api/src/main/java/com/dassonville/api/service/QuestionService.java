package com.dassonville.api.service;


import com.dassonville.api.dto.QuestionAdminDTO;
import com.dassonville.api.dto.QuestionInsertDTO;
import com.dassonville.api.dto.QuestionUpdateDTO;
import com.dassonville.api.exception.AlreadyExistException;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.mapper.QuestionMapper;
import com.dassonville.api.model.Question;
import com.dassonville.api.repository.QuestionRepository;
import com.dassonville.api.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.dassonville.api.constant.AppConstants.MINIMUM_QUIZ_QUESTIONS;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private static final Logger logger = LoggerFactory.getLogger(QuestionService.class);

    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;

    private final QuizRepository quizRepository;
    private final QuizService quizService;


    public QuestionAdminDTO create(long quizId, QuestionInsertDTO dto) {

        if (!quizRepository.existsById(quizId)) {
            logger.warn("Le quiz avec l'ID {}, n'a pas été trouvé.", quizId);
            throw new NotFoundException("Le quiz n'a pas été trouvé.");
        }

        if (questionRepository.existsByQuizIdAndTextIgnoreCase(quizId, dto.text())) {
            logger.warn("La question {}, existe déjà.", dto.text());
            throw new AlreadyExistException("La question existe déjà.");
        }

        if (dto.answers().stream()
                .map(answer -> answer.text().trim().toLowerCase())
                .distinct()
                .count() != dto.answers().size()) {
            logger.warn("Certaines réponses ont un texte en double (insensible à la casse et aux espaces).");
            throw new AlreadyExistException("Chaque réponse doit avoir un texte unique.");
        }

        Question questionToCreate = questionMapper.toModel(dto, quizId);

        Question questionCreated = questionRepository.save(questionToCreate);

        return questionMapper.toAdminDTO(questionCreated);
    }


    public QuestionAdminDTO update(long id, QuestionUpdateDTO dto) {
        Question questionToUpdate = questionRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("La question avec l'ID {}, n'a pas été trouvée.", id);
                    return new NotFoundException("La question n'a pas été trouvée.");
                });

        questionMapper.updateModelFromDTO(dto, questionToUpdate);

        if (questionRepository.existsByQuizIdAndTextIgnoreCaseAndIdNot(questionToUpdate.getQuiz().getId(), dto.text(), id)) {
            logger.warn("La question {}, existe déjà.", dto.text());
            throw new AlreadyExistException("La question existe déjà.");
        }

        Question updatedQuestion = questionRepository.save(questionToUpdate);

        return questionMapper.toAdminDTO(updatedQuestion);
    }


    public void delete(long id) {

        Question question = questionRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("La question avec l'ID {}, n'a pas été trouvée.", id);
                    return new NotFoundException("La question n'a pas été trouvée.");
                });

        questionRepository.deleteById(id);

        disableQuizIfTooFewQuestions(question.getQuiz().getId());
    }


    public void toggleVisibility(long id, boolean disable) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("La question avec l'ID {}, n'a pas été trouvée.", id);
                    return new NotFoundException("La question n'a pas été trouvée.");
                });

        question.setDisabledAt(disable ? LocalDateTime.now() : null);

        questionRepository.save(question);

        disableQuizIfTooFewQuestions(question.getQuiz().getId());
    }


    public void disableQuizIfTooFewQuestions (long quizId) {
        if (questionRepository.countByQuizIdAndDisabledAtIsNull(quizId) < MINIMUM_QUIZ_QUESTIONS) {
            logger.warn("Le quiz avec l'ID {}, n'a pas suffisamment de questions ; il a donc été désactivé.", quizId);
            quizService.toggleVisibility(quizId, true);
        }
    }
    
}
