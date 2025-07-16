package com.dassonville.api.service;


import com.dassonville.api.constant.Type;
import com.dassonville.api.dto.request.QuizUpsertDTO;
import com.dassonville.api.dto.response.*;
import com.dassonville.api.exception.ActionNotAllowedException;
import com.dassonville.api.exception.AlreadyExistException;
import com.dassonville.api.exception.ErrorCode;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.mapper.QuizMapper;
import com.dassonville.api.model.*;
import com.dassonville.api.projection.PublicQuizProjection;
import com.dassonville.api.repository.*;
import com.dassonville.api.util.TestPublicQuizProjection;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Tag("service")
@SpringBootTest
@ExtendWith(MockitoExtension.class)
@DisplayName("UNI - Service : Quiz")
public class QuizServiceTest {

    @Mock
    private QuizRepository quizRepository;
    @Mock
    private QuestionRepository questionRepository;
    @Mock
    private ThemeRepository themeRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private MasteryLevelRepository masteryLevelRepository;


    private QuizService quizService;

    @Autowired
    private QuizMapper quizMapper;


    private long id;
    private Quiz quiz;
    private Question question;
    private QuizUpsertDTO quizUpsertDTO;


    @BeforeEach
    void setUp() {
        quizService = new QuizService(quizRepository, quizMapper, questionRepository, themeRepository, categoryRepository, masteryLevelRepository);

        id = 1L;

        question = Question.builder()
                .id(1L)
                .text("Une question")
                .answerExplanation("Une explication")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .disabledAt(null)
                .build();

        quiz = Quiz.builder()
                .id(id)
                .quizType(new QuizType("CLASSIC"))
                .title("Un titre")
                .description("Une description")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .disabledAt(LocalDateTime.now())
                .masteryLevel(MasteryLevel.builder()
                        .id(1L)
                        .name("Une maîtrise")
                        .build()
                )
                .category(Category.builder()
                        .id(1L)
                        .name("Une catégorie")
                        .build()
                )
                .theme(Theme.builder()
                        .id(1L)
                        .name("Un thème")
                        .build()
                )
                .questions(List.of(question))
                .build();

        quizUpsertDTO = new QuizUpsertDTO(
                Type.CLASSIC,
                " un titre  ",
                " une description  ",
                1L,
                1L,
                null
        );
    }


    @Nested
    @DisplayName("PUBLIC - Récupérer les quiz par thèmes")
    class GetQuizzesByThemeForUserTest {

        @Test
        @DisplayName("Retourne tous les quiz si la liste des thèmes est vide")
        void shouldReturnAllQuizzesWhenThemeIdsIsEmpty() {
            // Given
            List<Long> themeIds = Collections.emptyList();
            Pageable pageable = PageRequest.of(0, 10);

            TestPublicQuizProjection quizProjection = new TestPublicQuizProjection();

            Page<PublicQuizProjection> quizPage = new PageImpl<>(List.of(quizProjection), pageable, 1);

            when(quizRepository.findAllByDisabledAtIsNull(pageable))
                    .thenReturn(quizPage);

            // When
            Page<QuizPublicDTO> result = quizService.findAllByThemeIdsForUser(themeIds, pageable);
            QuizPublicDTO resultQuiz = result.getContent().getFirst();

            // Then
            assertAll("Verify methods calls",
                    () -> verify(quizRepository).findAllByDisabledAtIsNull(pageable),
                    () -> verifyNoInteractions(themeRepository),
                    () -> verifyNoMoreInteractions(quizRepository)
            );

            assertAll("Assertion for DTO",
                    () -> assertThat(result.getContent()).hasSize(1),
                    () -> assertThat(resultQuiz.id()).isEqualTo(quizProjection.getId()),
                    () -> assertThat(resultQuiz.title()).isEqualTo(quizProjection.getTitle()),
                    () -> assertThat(resultQuiz.isNew()).isTrue(),
                    () -> assertThat(resultQuiz.numberOfQuestions()).isEqualTo(quizProjection.getNumberOfQuestions()),
                    () -> assertThat(resultQuiz.quizType()).isEqualTo(quizProjection.getQuizTypeName()),
                    () -> assertThat(resultQuiz.masteryLevel()).isEqualTo(quizProjection.getMasteryLevelName()),
                    () -> assertThat(resultQuiz.category()).isEqualTo(quizProjection.getCategoryName()),
                    () -> assertThat(resultQuiz.theme()).isEqualTo(quizProjection.getThemeName())
            );

            assertAll("Assertion for page",
                    () -> assertThat(result.getContent()).hasSize(1),
                    () -> assertThat(result.getTotalElements()).isEqualTo(1),
                    () -> assertThat(result.getNumber()).isEqualTo(0),
                    () -> assertThat(result.getSize()).isEqualTo(10)
            );
        }

        @Test
        @DisplayName("Retourne une page vide si aucun thème valide n'est trouvé")
        void shouldReturnEmptyPageWhenNoValidThemeIds() {
            // Given
            List<Long> themeIds = List.of(1L, 2L);
            Pageable pageable = PageRequest.of(0, 10);

            when(themeRepository.existsByDisabledAtIsNullAndIdIn(themeIds))
                    .thenReturn(false);

            // When
            Page<QuizPublicDTO> result = quizService.findAllByThemeIdsForUser(themeIds, pageable);

            // Then
            assertAll("Verify methods calls",
                    () -> verify(themeRepository).existsByDisabledAtIsNullAndIdIn(themeIds),
                    () -> verifyNoInteractions(quizRepository)
            );

            assertAll("Assertion for page",
                    () -> assertThat(result.getContent()).isEmpty(),
                    () -> assertThat(result.getTotalElements()).isEqualTo(0),
                    () -> assertThat(result.getNumber()).isEqualTo(0),
                    () -> assertThat(result.getSize()).isEqualTo(10)
            );
        }

        @Test
        @DisplayName("Retourne les quiz associés aux thèmes valides")
        void shouldReturnQuizzesForValidThemeIds() {
            // Given
            List<Long> themeIds = List.of(1L, 2L);
            Pageable pageable = PageRequest.of(0, 10);

            TestPublicQuizProjection quizProjection = new TestPublicQuizProjection();

            Page<PublicQuizProjection> quizPage = new PageImpl<>(List.of(quizProjection), pageable, 1);

            when(themeRepository.existsByDisabledAtIsNullAndIdIn(themeIds))
                    .thenReturn(true);
            when(quizRepository.findAllByDisabledAtIsNullAndThemeIdIn(themeIds, pageable))
                    .thenReturn(quizPage);

            // When
            Page<QuizPublicDTO> result = quizService.findAllByThemeIdsForUser(themeIds, pageable);
            QuizPublicDTO resultQuiz = result.getContent().getFirst();

            // Then
            assertAll("Verify methods calls",
                    () -> verify(themeRepository).existsByDisabledAtIsNullAndIdIn(themeIds),
                    () -> verify(quizRepository).findAllByDisabledAtIsNullAndThemeIdIn(themeIds, pageable)
            );

            assertAll("Assertion for DTO",
                    () -> assertThat(result.getContent()).hasSize(1),
                    () -> assertThat(resultQuiz.id()).isEqualTo(quizProjection.getId()),
                    () -> assertThat(resultQuiz.title()).isEqualTo(quizProjection.getTitle()),
                    () -> assertThat(resultQuiz.isNew()).isTrue(),
                    () -> assertThat(resultQuiz.numberOfQuestions()).isEqualTo(quizProjection.getNumberOfQuestions()),
                    () -> assertThat(resultQuiz.quizType()).isEqualTo(quizProjection.getQuizTypeName()),
                    () -> assertThat(resultQuiz.masteryLevel()).isEqualTo(quizProjection.getMasteryLevelName()),
                    () -> assertThat(resultQuiz.category()).isEqualTo(quizProjection.getCategoryName()),
                    () -> assertThat(resultQuiz.theme()).isEqualTo(quizProjection.getThemeName())
            );

            assertAll("Assertion for page",
                    () -> assertThat(result.getContent()).hasSize(1),
                    () -> assertThat(result.getTotalElements()).isEqualTo(1),
                    () -> assertThat(result.getNumber()).isEqualTo(0),
                    () -> assertThat(result.getSize()).isEqualTo(10)
            );
        }
    }


    @Nested
    @DisplayName("ADMIN - Récupérer les quiz par thème")
    class GetQuizzesByThemeTest {

        private Quiz quiz2;

        @BeforeEach
        void init() {
            quiz2 = Quiz.builder()
                    .id(id)
                    .quizType(new QuizType("TRUE_FALSE"))
                    .title("Un autre titre")
                    .description("Une autre description")
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .disabledAt(null)
                    .masteryLevel(MasteryLevel.builder()
                            .id(2L)
                            .name("Une autre maîtrise")
                            .build()
                    )
                    .category(Category.builder()
                            .id(2L)
                            .name("Une autre catégorie")
                            .build()
                    )
                    .theme(Theme.builder()
                            .id(2L)
                            .name("Un autre thème")
                            .build()
                    )
                    .questions(List.of())
                    .build();
        }

        @Test
        @DisplayName("Succès - visible = null")
        void shouldReturnAllQuizzes_WhenVisibleIsNull() {
            // Given
            long themeId = 1L;
            Pageable pageable = PageRequest.of(0, 10);
            List<Quiz> quizzes = List.of(quiz, quiz2);
            Page<Quiz> quizPage = new PageImpl<>(quizzes, pageable, quizzes.size());

            when(themeRepository.existsById(themeId))
                    .thenReturn(true);
            when(quizRepository.findByThemeId(themeId, pageable))
                    .thenReturn(quizPage);

            // When
            Page<QuizAdminDTO> result = quizService.findAllByThemeIdForAdmin(themeId, null, pageable);
            QuizAdminDTO quizAdminDTO = result.getContent().getFirst();

            // Then
            assertAll("Verify methods calls",
                    () -> verify(themeRepository).existsById(themeId),
                    () -> verify(quizRepository).findByThemeId(themeId, pageable),
                    () -> verifyNoMoreInteractions(quizRepository)
            );

            Quiz firstQuiz = quizzes.getFirst();

            assertAll("Assertion for DTO",
                    () -> assertThat(quizAdminDTO.id()).isEqualTo(firstQuiz.getId()),
                    () -> assertThat(quizAdminDTO.title()).isEqualTo(firstQuiz.getTitle()),
                    () -> assertThat(quizAdminDTO.quizType()).isEqualTo(firstQuiz.getQuizType().getName()),
                    () -> assertThat(quizAdminDTO.masteryLevel()).isEqualTo(firstQuiz.getMasteryLevel().getName()),
                    () -> assertThat(quizAdminDTO.numberOfQuestions()).isEqualTo((byte) firstQuiz.getQuestions().size()),
                    () -> assertThat(quizAdminDTO.hasEnoughQuestionsForActivation()).isFalse(),
                    () -> assertThat(quizAdminDTO.createdAt()).isEqualTo(firstQuiz.getCreatedAt()),
                    () -> assertThat(quizAdminDTO.updatedAt()).isEqualTo(firstQuiz.getUpdatedAt()),
                    () -> assertThat(quizAdminDTO.disabledAt()).isEqualTo(firstQuiz.getDisabledAt()),
                    () -> assertThat(quizAdminDTO.category()).isEqualTo(firstQuiz.getCategory().getName())
            );

            assertAll("Assertion for page",
                    () -> assertThat(result.getContent()).hasSize(2),
                    () -> assertThat(result.getTotalElements()).isEqualTo(2),
                    () -> assertThat(result.getNumber()).isEqualTo(0),
                    () -> assertThat(result.getSize()).isEqualTo(10)
            );
        }

        /*@Test
        @DisplayName("Succès - visible = true")
        void shouldReturnVisibleQuizzes_WhenVisibleIsTrue() {
            // Given
            long themeId = 1L;
            Pageable pageable = PageRequest.of(0, 10);
            List<Quiz> quizzes = List.of(new Quiz());
            Page<Quiz> quizPage = new PageImpl<>(quizzes, pageable, quizzes.size());

            when(themeRepository.existsById(themeId))
                    .thenReturn(true);
            when(quizRepository.findByThemeIdAndDisabledAtIsNull(themeId, pageable))
                    .thenReturn(quizPage);

            // When
            Page<QuizAdminDTO> result = quizService.findAllByThemeIdForAdmin(themeId, true, pageable);
            QuizAdminDTO quizAdminDTO = result.getContent().getFirst();

            // Then
            assertAll("Verify methods calls",
                    () -> verify(themeRepository).existsById(themeId),
                    () -> verify(quizRepository).findByThemeIdAndDisabledAtIsNull(themeId, pageable),
                    () -> verifyNoMoreInteractions(quizRepository)
            );

            Quiz firstQuiz = quizzes.getFirst();

            assertThat(result).isNotNull();
            assertThat(result.getContent()).hasSize(1);

            assertAll("Assertion for DTO",
                    () -> assertThat(quizAdminDTO.id()).isEqualTo(firstQuiz.getId()),
                    () -> assertThat(quizAdminDTO.title()).isEqualTo(firstQuiz.getTitle()),
                    () -> assertThat(quizAdminDTO.quizType()).isEqualTo(firstQuiz.getQuizType().getName()),
                    () -> assertThat(quizAdminDTO.masteryLevel()).isEqualTo(firstQuiz.getMasteryLevel().getName()),
                    () -> assertThat(quizAdminDTO.numberOfQuestions()).isEqualTo((byte) firstQuiz.getQuestions().size()),
                    () -> assertThat(quizAdminDTO.hasEnoughQuestionsForActivation()).isFalse(),
                    () -> assertThat(quizAdminDTO.createdAt()).isEqualTo(firstQuiz.getCreatedAt()),
                    () -> assertThat(quizAdminDTO.updatedAt()).isEqualTo(firstQuiz.getUpdatedAt()),
                    () -> assertThat(quizAdminDTO.disabledAt()).isEqualTo(firstQuiz.getDisabledAt()),
                    () -> assertThat(quizAdminDTO.category()).isEqualTo(firstQuiz.getCategory().getName())
            );
        }

        @Test
        @DisplayName("Succès - visible = false")
        void shouldReturnDisabledQuizzes_WhenVisibleIsFalse() {
            // Given
            long themeId = 1L;
            Pageable pageable = PageRequest.of(0, 10);
            List<Quiz> quizzes = List.of(new Quiz());
            Page<Quiz> quizPage = new PageImpl<>(quizzes);

            when(themeRepository.existsById(themeId))
                    .thenReturn(true);
            when(quizRepository.findByThemeIdAndDisabledAtIsNotNull(themeId, pageable))
                    .thenReturn(quizPage);

            // When
            Page<QuizAdminDTO> result = quizService.findAllByThemeIdForAdmin(themeId, false, pageable);

            // Then
            verify(themeRepository).existsById(themeId);
            verify(quizRepository).findByThemeIdAndDisabledAtIsNotNull(themeId, pageable);
            verify(quizRepository, never()).findByThemeId(anyLong(), any(Pageable.class));
            verify(quizRepository, never()).findByThemeIdAndDisabledAtIsNull(anyLong(), any(Pageable.class));


            assertThat(result).isNotNull();
            assertThat(result.getContent()).hasSize(1);
        }*/

        @Test
        @DisplayName("Erreur - Thème non trouvé")
        void shouldThrowNotFoundException_WhenThemeNotFound() {
            // Given
            long themeId = 9999L;
            Pageable pageable = PageRequest.of(0, 10);

            when(themeRepository.existsById(themeId))
                    .thenReturn(false);

            // When
            NotFoundException exception = assertThrows(NotFoundException.class, () -> quizService.findAllByThemeIdForAdmin(themeId, null, pageable));

            // Then
            assertAll(
                    () -> assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.THEME_NOT_FOUND),
                    () -> verify(themeRepository).existsById(themeId),
                    () -> verifyNoInteractions(quizRepository)
            );
        }
    }


    @Nested
    @DisplayName("ADMIN - Récupérer un quiz par son ID")
    class FindByIdForAdminTest {

        @Test
        @DisplayName("Succès")
        void findByIdForAdmin_quizFound() {
            // Given
            when(quizRepository.findById(anyLong()))
                    .thenReturn(Optional.of(quiz));

            // When
            QuizAdminDetailsDTO result = quizService.findByIdForAdmin(id);

            // Then
            assertAll("Verify methods calls",
                    () -> verify(quizRepository).findById(id)
            );

            assertAll("Assertion for DTO",
                    () -> assertThat(result.id()).isEqualTo(quiz.getId()),
                    () -> assertThat(result.title()).isEqualTo(quiz.getTitle()),
                    () -> assertThat(result.description()).isEqualTo(quiz.getDescription()),
                    () -> assertThat(result.createdAt()).isEqualTo(quiz.getCreatedAt()),
                    () -> assertThat(result.updatedAt()).isEqualTo(quiz.getUpdatedAt()),
                    () -> assertThat(result.disabledAt()).isEqualTo(quiz.getDisabledAt()),
                    () -> assertThat(result.themeId()).isEqualTo(quiz.getTheme().getId()),
                    () -> assertThat(result.categoryId()).isEqualTo(quiz.getCategory() != null ? quiz.getCategory().getId() : null),
                    () -> assertThat(result.quizType()).isEqualTo(quiz.getQuizType().getName()),
                    () -> assertThat(result.masteryLevelId()).isEqualTo(quiz.getMasteryLevel().getId())
            );
        }

        @Test
        @DisplayName("Erreur - Quiz non trouvé")
        void findByIdForAdmin_quizNotFound() {
            // Given
            when(quizRepository.findById(anyLong()))
                    .thenReturn(Optional.empty());

            // When
            NotFoundException exception = assertThrows(NotFoundException.class, () -> quizService.findByIdForAdmin(id));

            // Then
            assertAll(
                    () -> assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.QUIZ_NOT_FOUND),
                    () -> verify(quizRepository).findById(id)
            );
        }
    }


    @Nested
    @DisplayName("PUBLIC - Récupérer un quiz par son ID")
    class FindByIdForUserTest {

        @Test
        @DisplayName("Succès")
        void findByIdForUser_quizFound() {
            // Given
            when(quizRepository.findByIdAndDisabledAtIsNullEverywhere(anyLong()))
                    .thenReturn(Optional.of(quiz));

            // When
            QuizPublicDetailsDTO result = quizService.findByIdForUser(id);

            // Then
            assertAll("Verify methods calls",
                    () -> verify(quizRepository).findByIdAndDisabledAtIsNullEverywhere(id)
            );

            assertAll("Assertion for DTO",
                    () -> assertThat(result.id()).isEqualTo(quiz.getId()),
                    () -> assertThat(result.title()).isEqualTo(quiz.getTitle()),
                    () -> assertThat(result.description()).isEqualTo(quiz.getDescription()),
                    () -> assertThat(result.isNew()).isTrue(),
                    () -> assertThat(result.theme()).isEqualTo(quiz.getTheme().getName()),
                    () -> assertThat(result.category()).isEqualTo(quiz.getCategory() != null ? quiz.getCategory().getName() : null),
                    () -> assertThat(result.quizType()).isEqualTo(quiz.getQuizType().getName()),
                    () -> assertThat(result.masteryLevel()).isEqualTo(quiz.getMasteryLevel().getName())
            );
        }

        @Test
        @DisplayName("Erreur - Quiz non trouvé")
        void findByIdForUser_quizNotFound() {
            // Given
            when(quizRepository.findByIdAndDisabledAtIsNullEverywhere(anyLong()))
                    .thenReturn(Optional.empty());

            // When
            NotFoundException exception = assertThrows(NotFoundException.class, () -> quizService.findByIdForUser(id));

            // Then
            assertAll(
                    () -> assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.QUIZ_NOT_FOUND),
                    () -> verify(quizRepository).findByIdAndDisabledAtIsNullEverywhere(id)
            );
        }
    }


    @Nested
    @DisplayName("Créer un quiz")
    class CreateTest {

        @Test
        @DisplayName("Succès")
        void create_newQuiz() {
            // Given
            quiz.setQuestions(List.of());

            when(quizRepository.existsByTitleIgnoreCase(anyString()))
                    .thenReturn(false);
            when(themeRepository.existsById(anyLong()))
                    .thenReturn(true);
            when(masteryLevelRepository.existsById(anyLong()))
                    .thenReturn(true);
            when(quizRepository.save(any(Quiz.class)))
                    .thenReturn(quiz);

            // When
            QuizAdminDetailsDTO result = quizService.create(quizUpsertDTO);

            // Then
            assertAll("Verify methods calls",
                    () -> verify(quizRepository).existsByTitleIgnoreCase(anyString()),
                    () -> verify(themeRepository).existsById(quizUpsertDTO.themeId()),
                    () -> verifyNoInteractions(categoryRepository),
                    () -> verify(masteryLevelRepository).existsById(quizUpsertDTO.masteryLevelId()),
                    () -> verify(quizRepository).save(any(Quiz.class))
            );

            assertAll("Assertion for DTO",
                    () -> assertThat(result.id()).isEqualTo(quiz.getId()),
                    () -> assertThat(result.title()).isEqualTo(quiz.getTitle()),
                    () -> assertThat(result.description()).isEqualTo(quiz.getDescription()),
                    () -> assertThat(result.createdAt()).isEqualTo(quiz.getCreatedAt()),
                    () -> assertThat(result.updatedAt()).isEqualTo(quiz.getUpdatedAt()),
                    () -> assertThat(result.disabledAt()).isEqualTo(quiz.getDisabledAt()),
                    () -> assertThat(result.themeId()).isEqualTo(quiz.getTheme().getId()),
                    () -> assertThat(result.categoryId()).isEqualTo(quiz.getCategory() != null ? quiz.getCategory().getId() : null),
                    () -> assertThat(result.quizType()).isEqualTo(quiz.getQuizType().getName()),
                    () -> assertThat(result.masteryLevelId()).isEqualTo(quiz.getMasteryLevel().getId()),
                    () -> assertThat(result.questions()).isEmpty()
            );
        }

        @Test
        @DisplayName("Erreur - Titre déjà existant")
        void create_existingTitle() {
            // Given
            when(quizRepository.existsByTitleIgnoreCase(anyString()))
                    .thenReturn(true);

            // When
            AlreadyExistException exception = assertThrows(AlreadyExistException.class, () -> quizService.create(quizUpsertDTO));

            // Then
            assertAll(
                    () -> assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.QUIZ_ALREADY_EXISTS),
                    () -> verify(quizRepository).existsByTitleIgnoreCase(anyString()),
                    () -> verifyNoMoreInteractions(quizRepository)
            );
        }
    }


    @Nested
    @DisplayName("Mettre à jour un quiz")
    class UpdateTest {

        private Quiz quizUpdated;
        private QuizUpsertDTO quizToUpdateDTO;

        @BeforeEach
        void init() {
            quizUpdated = Quiz.builder()
                    .id(id)
                    .quizType(quiz.getQuizType())
                    .title("Un titre mis à jour")
                    .description("Une description mise à jour")
                    .createdAt(quiz.getCreatedAt())
                    .updatedAt(LocalDateTime.now())
                    .disabledAt(null)
                    .masteryLevel(new MasteryLevel(2))
                    .category(new Category(2))
                    .theme(new Theme(2))
                    .questions(quiz.getQuestions())
                    .build();

            quizToUpdateDTO = new QuizUpsertDTO(
                    Type.CLASSIC,
                    " un titre mis à jour  ",
                    " une description mise à jour  ",
                    2L,
                    2L,
                    2L
            );
        }

        @Test
        @DisplayName("Succès")
        void update_quiz() {
            // Given
            when(quizRepository.findById(anyLong()))
                    .thenReturn(Optional.of(quiz));
            when(quizRepository.existsByTitleIgnoreCaseAndIdNot(anyString(), anyLong()))
                    .thenReturn(false);
            when(quizRepository.existsInvalidQuestionTypeForQuiz(anyLong(), anyString()))
                    .thenReturn(false);
            when(themeRepository.existsById(anyLong()))
                    .thenReturn(true);
            when(categoryRepository.existsById(anyLong()))
                    .thenReturn(true);
            when(masteryLevelRepository.existsById(anyLong()))
                    .thenReturn(true);

            // When
            QuizAdminDetailsDTO result = quizService.update(id, quizToUpdateDTO);
            QuestionAdminDTO resultQuestion = result.questions().getFirst();

            // Then
            assertAll("Verify methods calls",
                    () -> verify(quizRepository).findById(id),
                    () -> verify(quizRepository).existsByTitleIgnoreCaseAndIdNot(anyString(), eq(id)),
                    () -> verify(quizRepository).existsInvalidQuestionTypeForQuiz(id, quizToUpdateDTO.type().getType()),
                    () -> verify(themeRepository).existsById(quizToUpdateDTO.themeId()),
                    () -> verify(categoryRepository).existsById(quizToUpdateDTO.categoryId()),
                    () -> verify(masteryLevelRepository).existsById(quizToUpdateDTO.masteryLevelId())
            );

            assertAll("Verify updated entity",
                    () -> assertThat(quiz.getTitle()).isEqualTo(quizUpdated.getTitle()),
                    () -> assertThat(quiz.getDescription()).isEqualTo(quizUpdated.getDescription()),
                    () -> assertThat(quiz.getQuizType().getName()).isEqualTo(quizUpdated.getQuizType().getName()),
                    () -> assertThat(quiz.getTheme().getId()).isEqualTo(quizUpdated.getTheme().getId()),
                    () -> assertThat(quiz.getCategory().getId()).isEqualTo(quizUpdated.getCategory().getId()),
                    () -> assertThat(quiz.getMasteryLevel().getId()).isEqualTo(quizUpdated.getMasteryLevel().getId())
            );

            assertAll("Assertion for DTO",
                    () -> assertThat(result.id()).isEqualTo(quiz.getId()),
                    () -> assertThat(result.title()).isEqualTo(quiz.getTitle()),
                    () -> assertThat(result.description()).isEqualTo(quiz.getDescription()),
                    () -> assertThat(result.createdAt()).isEqualTo(quiz.getCreatedAt()),
                    () -> assertThat(result.updatedAt()).isEqualTo(quiz.getUpdatedAt()),
                    () -> assertThat(result.disabledAt()).isEqualTo(quiz.getDisabledAt()),
                    () -> assertThat(result.themeId()).isEqualTo(quiz.getTheme().getId()),
                    () -> assertThat(result.categoryId()).isEqualTo(quiz.getCategory() != null ? quiz.getCategory().getId() : null),
                    () -> assertThat(result.quizType()).isEqualTo(quiz.getQuizType().getName()),
                    () -> assertThat(result.masteryLevelId()).isEqualTo(quiz.getMasteryLevel().getId()),

                    () -> assertThat(result.questions()).hasSize(1),
                    () -> assertThat(resultQuestion.id()).isEqualTo(question.getId()),
                    () -> assertThat(resultQuestion.text()).isEqualTo(question.getText()),
                    () -> assertThat(resultQuestion.answerExplanation()).isEqualTo(question.getAnswerExplanation()),
                    () -> assertThat(resultQuestion.createdAt()).isEqualTo(question.getCreatedAt()),
                    () -> assertThat(resultQuestion.updatedAt()).isEqualTo(question.getUpdatedAt()),
                    () -> assertThat(resultQuestion.disabledAt()).isEqualTo(question.getDisabledAt())
            );
        }

        @Test
        @DisplayName("Erreur - Quiz non trouvé")
        void update_quizNotFound() {
            // Given
            when(quizRepository.findById(anyLong()))
                    .thenReturn(Optional.empty());

            // When
            NotFoundException exception = assertThrows(NotFoundException.class, () -> quizService.update(id, quizToUpdateDTO));

            // Then
            assertAll(
                    () -> assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.QUIZ_NOT_FOUND),
                    () -> verify(quizRepository).findById(id),
                    () -> verifyNoMoreInteractions(quizRepository)
            );
        }

        @Test
        @DisplayName("Erreur - Titre déjà existant")
        void update_existingTitle() {
            // Given
            when(quizRepository.findById(anyLong()))
                    .thenReturn(Optional.of(quiz));
            when(quizRepository.existsByTitleIgnoreCaseAndIdNot(anyString(), anyLong()))
                    .thenReturn(true);

            // When
            AlreadyExistException exception = assertThrows(AlreadyExistException.class, () -> quizService.update(id, quizToUpdateDTO));

            // Then
            assertAll(
                    () -> assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.QUIZ_ALREADY_EXISTS),
                    () -> verify(quizRepository).findById(id),
                    () -> verify(quizRepository).existsByTitleIgnoreCaseAndIdNot(anyString(), eq(id)),
                    () -> verifyNoMoreInteractions(quizRepository)
            );
        }
    }


    @Nested
    @DisplayName("Supprimer un quiz")
    class DeleteTest {

        @Test
        @DisplayName("Succès")
        void delete_quiz() {
            // Given
            when(quizRepository.existsById(anyLong()))
                    .thenReturn(true);

            // When
            quizService.delete(id);

            // Then
            assertAll("Verify methods calls",
                    () -> verify(quizRepository).existsById(id),
                    () -> verify(quizRepository).deleteById(id)
            );
        }

        @Test
        @DisplayName("Erreur - Quiz non trouvé")
        void delete_quizNotFound() {
            // Given
            when(quizRepository.existsById(anyLong()))
                    .thenReturn(false);

            // When
            NotFoundException exception = assertThrows(NotFoundException.class, () -> quizService.delete(id));

            // Then
            assertAll(
                    () -> assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.QUIZ_NOT_FOUND),
                    () -> verify(quizRepository).existsById(id),
                    () -> verifyNoMoreInteractions(quizRepository)
            );
        }
    }


    @Nested
    @DisplayName("Basculer la visibilité d'un quiz")
    class UpdateVisibilityTest {

        @Test
        @DisplayName("Succès - Quiz activé")
        void updateVisibility_quizEnable() {
            // Given
            when(quizRepository.findById(anyLong()))
                    .thenReturn(Optional.of(quiz));
            when(quizRepository.countByIdAndQuestionsDisabledAtIsNull(anyLong()))
                    .thenReturn(10);

            // When
            quizService.updateVisibility(id, true);

            // Then
            assertAll(
                    () -> verify(quizRepository).findById(id),
                    () -> verify(quizRepository).countByIdAndQuestionsDisabledAtIsNull(id),
                    () -> assertThat(quiz.isVisible()).isTrue()
            );
        }

        @Test
        @DisplayName("Erreur - Impossible d'activer le quiz : pas assez de questions")
        void updateVisibility_quizEnableNotEnoughQuestions() {
            // Given
            when(quizRepository.findById(anyLong()))
                    .thenReturn(Optional.of(quiz));
            when(quizRepository.countByIdAndQuestionsDisabledAtIsNull(anyLong()))
                    .thenReturn(0);

            // When
            ActionNotAllowedException exception = assertThrows(ActionNotAllowedException.class, () -> quizService.updateVisibility(id, true));

            // Then
            assertAll(
                    () -> assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.QUIZ_CONTAINS_NOT_ENOUGH_QUESTIONS),
                    () -> verify(quizRepository).findById(id),
                    () -> verify(quizRepository).countByIdAndQuestionsDisabledAtIsNull(id),
                    () -> assertThat(quiz.isVisible()).isFalse()
            );
        }

        @Test
        @DisplayName("RAS - Quiz déjà inactif")
        void updateVisibility_quizAlreadyInactive() {
            // Given
            when(quizRepository.findById(anyLong()))
                    .thenReturn(Optional.of(quiz));

            // When
            quizService.updateVisibility(id, false);

            // Then
            assertAll(
                    () -> verify(quizRepository).findById(id),
                    () -> verifyNoMoreInteractions(quizRepository),
                    () -> assertThat(quiz.isVisible()).isFalse()
            );
        }

        @Test
        @DisplayName("Erreur - Quiz non trouvé")
        void updateVisibility_quizNotFound() {
            // Given
            when(quizRepository.findById(anyLong()))
                    .thenReturn(Optional.empty());

            // When
            NotFoundException exception = assertThrows(NotFoundException.class, () -> quizService.updateVisibility(id, true));

            // Then
            assertAll(
                    () -> assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.QUIZ_NOT_FOUND),
                    () -> verify(quizRepository).findById(id),
                    () -> verifyNoMoreInteractions(quizRepository),
                    () -> assertThat(quiz.isVisible()).isFalse()
            );
        }
    }
}
