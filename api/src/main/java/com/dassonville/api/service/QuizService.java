package com.dassonville.api.service;


import com.dassonville.api.dto.*;
import com.dassonville.api.exception.AlreadyExistException;
import com.dassonville.api.exception.InvalidStateException;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.mapper.QuizMapper;
import com.dassonville.api.model.Quiz;
import com.dassonville.api.model.Theme;
import com.dassonville.api.repository.QuestionRepository;
import com.dassonville.api.repository.QuizRepository;
import com.dassonville.api.repository.ThemeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

import static com.dassonville.api.constant.AppConstants.MINIMUM_QUIZ_QUESTIONS;

@Service
@RequiredArgsConstructor
public class QuizService {

    private static final Logger logger = LoggerFactory.getLogger(QuizService.class);

    private final QuizRepository quizRepository;
    private final QuizMapper quizMapper;

    private final QuestionRepository questionRepository;
    private final ThemeRepository themeRepository;


    public List<QuizActiveAdminDTO> getAllQuiz() {
        List<Quiz> quizzes = quizRepository.findAll();
        return quizMapper.toActiveAdminDTOList(quizzes);
    }

    /*@Transactional
    public List<QuizzesByTheme> getAllActiveQuizGroupedByTheme() {
        List<Theme> themes = themeRepository.findAllByQuizzesDisabledAtIsNull();
        return themes.stream()
                .map(quizMapper::toQuizzesByTheme)
                .toList();
    }*/

    @Transactional
    public List<QuizzesByThemeAdminDTO> getAllActiveQuizGroupedByTheme() {
        List<Theme> themes = themeRepository.findAllByQuizzesDisabledAtIsNull();
        return themes.stream()
                .map(theme -> {
                    theme.getQuizzes()
                            .sort((quiz1, quiz2) -> quiz1.getCategory().getName()
                                    .compareToIgnoreCase(quiz2.getCategory().getName()));
                    return quizMapper.toQuizzesByTheme(theme);
                })
                .toList();
    }

    @Transactional
    public List<QuizInactiveAdminDTO> getAllInactiveQuiz() {
        List<Quiz> quizzes = quizRepository.findAllByDisabledAtIsNotNull();
        return quizzes.stream()
                .sorted(Comparator.comparing(quiz -> quiz.getTheme().getName(), String.CASE_INSENSITIVE_ORDER))
                .map(quizMapper::toInactiveAdminDTO)
                .toList();
    }

    @Transactional
    public QuizAdminDetailsDTO findByIdForAdmin(long id) {
        Quiz quiz = findQuizById(id);

        return quizMapper.toAdminDetailsDTO(quiz);
    }

    public QuizAdminDetailsDTO create(QuizUpsertDTO dto) {

        if (quizRepository.existsByTitleIgnoreCase(dto.title())) {
            logger.warn("Le quiz avec le titre {}, existe déjà.", dto.title());
            throw new AlreadyExistException("Le quiz existe déjà.");
        }

        Quiz quizToCreate = quizMapper.toModel(dto);

        Quiz quizCreated = quizRepository.save(quizToCreate);

        return quizMapper.toAdminDetailsDTO(quizCreated);
    }

    public QuizAdminDetailsDTO update(long id, QuizUpsertDTO dto) {

        Quiz quizToUpdate = findQuizById(id);

        if (quizRepository.existsByTitleIgnoreCaseAndIdNot(dto.title(), id)) {
            logger.warn("Le quiz avec le titre {}, existe déjà.", dto.title());
            throw new AlreadyExistException("Le quiz existe déjà.");
        }

        quizMapper.updateModelFromDTO(dto, quizToUpdate);

        Quiz updatedQuiz = quizRepository.save(quizToUpdate);

        return quizMapper.toAdminDetailsDTO(updatedQuiz);
    }

    public void delete(long id) {

        if (!quizRepository.existsById(id)) {
            logger.warn("Le quiz avec l'ID {}, n'a pas été trouvé.", id);
            throw new NotFoundException("Le quiz n'a pas été trouvé.");
        }

        quizRepository.deleteById(id);
    }

    public void toggleVisibility(long id, boolean visible) {
        Quiz quiz = findQuizById(id);

        if (quiz.isVisible() == visible) return;

        if (visible && !hasMinimumQuestions(id)) {
            logger.warn("Le quiz avec l'ID {}, ne peut pas être activé car il n'a pas assez de questions.", id);
            throw new InvalidStateException("Le quiz ne peut pas être activé car il ne contient pas au moins " + MINIMUM_QUIZ_QUESTIONS + " questions.");
        }

        quiz.setVisible(visible);

        quizRepository.save(quiz);
    }


    private Quiz findQuizById(long id) {
        return quizRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Le quiz avec l'ID {}, n'a pas été trouvé.", id);
                    return new NotFoundException("Le quiz n'a pas été trouvé.");
                });
    }

    private boolean hasMinimumQuestions(long quizId) {
        return questionRepository.countByQuizIdAndDisabledAtIsNull(quizId) >= MINIMUM_QUIZ_QUESTIONS;
    }
}
