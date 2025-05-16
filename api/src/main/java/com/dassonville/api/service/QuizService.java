package com.dassonville.api.service;


import com.dassonville.api.dto.QuizAdminDetailsDTO;
import com.dassonville.api.dto.QuizInactiveAdminDTO;
import com.dassonville.api.dto.QuizUpsertDTO;
import com.dassonville.api.dto.QuizzesByThemeAdminDTO;
import com.dassonville.api.exception.ActionNotAllowedException;
import com.dassonville.api.exception.AlreadyExistException;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.mapper.QuizMapper;
import com.dassonville.api.model.Quiz;
import com.dassonville.api.model.Theme;
import com.dassonville.api.repository.QuestionRepository;
import com.dassonville.api.repository.QuizRepository;
import com.dassonville.api.repository.ThemeRepository;
import com.dassonville.api.util.TextUtils;
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
    private final ThemeService themeService;


    // TODO: A reconsidérer pour une meilleure UX / performance
    @Transactional
    public List<QuizzesByThemeAdminDTO> getAllActiveQuizGroupedByTheme() {
        List<Theme> themes = themeRepository.findAllByQuizzesDisabledAtIsNull();
        themes.sort(Comparator.comparing(Theme::getName, String.CASE_INSENSITIVE_ORDER));
        return quizMapper.toQuizzesByThemeList(themes);
    }

    @Transactional
    public List<QuizInactiveAdminDTO> getAllInactiveQuiz() {
        List<Quiz> quizzes = quizRepository.findAllByDisabledAtIsNotNull();
        quizzes.sort(Comparator.comparing(quiz -> quiz.getTheme().getName(), String.CASE_INSENSITIVE_ORDER));
        return quizMapper.toInactiveAdminDTOList(quizzes);
    }

    @Transactional
    public QuizAdminDetailsDTO findByIdForAdmin(long id) {
        Quiz quiz = findQuizById(id);

        return quizMapper.toAdminDetailsDTO(quiz);
    }

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

    public QuizAdminDetailsDTO update(long id, QuizUpsertDTO dto) {

        Quiz quizToUpdate = findQuizById(id);

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

    public void delete(long id) {

        Quiz quiz = findQuizById(id);

        quizRepository.deleteById(id);

        disableThemeIfNoActiveQuizzes(quiz.getTheme().getId());
    }

    public void updateVisibility(long id, boolean visible) {

        Quiz quiz = findQuizById(id);

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


    private Quiz findQuizById(long id) {
        return quizRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Le quiz avec l'ID {}, n'a pas été trouvé.", id);
                    return new NotFoundException("Le quiz n'a pas été trouvé.");
                });
    }

    private void hasMinimumQuestions(long quizId) {

        int numberOfActiveQuestions = questionRepository.countByQuizIdAndDisabledAtIsNull(quizId);

        if (numberOfActiveQuestions < MINIMUM_QUIZ_QUESTIONS) {
            logger.warn("Le quiz avec l'ID {}, ne peut pas être activé car il n'a pas assez de questions.", quizId);
            throw new ActionNotAllowedException("Le quiz ne peut pas être activé car il ne contient pas au moins " + MINIMUM_QUIZ_QUESTIONS + " questions.");
        } else {
            logger.debug("Le quiz avec l'ID {}, a suffisamment de questions : {} pour être activé.", quizId, numberOfActiveQuestions);
        }
    }

    private void disableThemeIfNoActiveQuizzes(long themeId) {

        int numberOfActiveQuizzes = quizRepository.countByThemeIdAndDisabledAtIsNull(themeId);

        if (numberOfActiveQuizzes == 0) {
            logger.warn("Le thème avec l'ID {} n'a pas de quiz actifs et va être désactivé.", themeId);
            themeService.updateVisibility(themeId, false);
        } else {
            logger.debug("Le thème avec l'ID {} a {} quiz actifs et reste donc actif.", themeId, numberOfActiveQuizzes);
        }
    }

}
