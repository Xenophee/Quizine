package com.dassonville.api.service;


import com.dassonville.api.dto.AnswerAdminDTO;
import com.dassonville.api.dto.AnswerUpsertDTO;
import com.dassonville.api.exception.ActionNotAllowedException;
import com.dassonville.api.exception.AlreadyExistException;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.mapper.AnswerMapper;
import com.dassonville.api.model.Answer;
import com.dassonville.api.model.Question;
import com.dassonville.api.repository.AnswerRepository;
import com.dassonville.api.repository.DifficultyLevelRepository;
import com.dassonville.api.repository.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UNI - Service : Réponse")
public class AnswerServiceTest {

    @Mock
    private QuestionRepository questionRepository;
    @Mock
    private AnswerRepository answerRepository;
    @Mock
    private DifficultyLevelRepository difficultyLevelRepository;


    private AnswerService answerService;

    private final AnswerMapper answerMapper = Mappers.getMapper(AnswerMapper.class);

    private long id;
    private Answer answer;
    private AnswerUpsertDTO answerUpsertDTO;


    @BeforeEach
    void setUp() {
        answerService = new AnswerService(answerRepository, answerMapper, questionRepository, difficultyLevelRepository);

        id = 1L;

        answer = new Answer();
        answer.setId(id);
        answer.setText("Answer");
        answer.setIsCorrect(true);
        answer.setCreatedAt(LocalDateTime.now());
        answer.setDisabledAt(null);
        answer.setQuestion(new Question(1L));

        answerUpsertDTO = new AnswerUpsertDTO(" answer", true);
    }


    @Nested
    @DisplayName("Créer une réponse")
    class CreateTest {

        @Test
        @DisplayName("Succès")
        void create_newAnswer() {
            // Given
            when(questionRepository.existsById(anyLong()))
                    .thenReturn(true);
            when(answerRepository.existsByTextIgnoreCaseAndQuestionId(anyString(), anyLong()))
                    .thenReturn(false);
            when(answerRepository.save(any(Answer.class)))
                    .thenReturn(answer);

            // When
            AnswerAdminDTO result = answerService.create(id, answerUpsertDTO);

            // Then
            verify(questionRepository).existsById(anyLong());
            verify(answerRepository).existsByTextIgnoreCaseAndQuestionId(anyString(), anyLong());
            verify(answerRepository).save(any(Answer.class));

            assertThat(result).isNotNull();
            assertThat(result.text()).isEqualTo(answer.getText());
        }

        @Test
        @DisplayName("Erreur - Question non trouvée")
        void create_questionNotFound() {
            // Given
            when(questionRepository.existsById(anyLong()))
                    .thenReturn(false);

            // When
            assertThrows(NotFoundException.class, () -> answerService.create(id, answerUpsertDTO));

            // Then
            verify(questionRepository).existsById(anyLong());
            verify(answerRepository, never()).existsByTextIgnoreCaseAndQuestionId(anyString(), anyLong());
            verify(answerRepository, never()).save(any(Answer.class));
        }

        @Test
        @DisplayName("Erreur - Réponse déjà existante pour la question")
        void create_answerAlreadyExists() {
            // Given
            when(questionRepository.existsById(anyLong()))
                    .thenReturn(true);
            when(answerRepository.existsByTextIgnoreCaseAndQuestionId(anyString(), anyLong()))
                    .thenReturn(true);

            // When
            assertThrows(AlreadyExistException.class, () -> answerService.create(id, answerUpsertDTO));

            // Then
            verify(questionRepository).existsById(anyLong());
            verify(answerRepository).existsByTextIgnoreCaseAndQuestionId(anyString(), anyLong());
            verify(answerRepository, never()).save(any(Answer.class));
        }
    }


    @Nested
    @DisplayName("Mettre à jour une réponse")
    class UpdateTest {

        @Test
        @DisplayName("Succès")
        void update_answer() {
            // Given
            when(answerRepository.findById(anyLong()))
                    .thenReturn(Optional.of(answer));
            when(answerRepository.existsByTextIgnoreCaseAndIdNot(anyString(), anyLong()))
                    .thenReturn(false);
            when(answerRepository.save(any(Answer.class)))
                    .thenReturn(answer);

            // When
            AnswerAdminDTO result = answerService.update(id, answerUpsertDTO);

            // Then
            verify(answerRepository).findById(anyLong());
            verify(answerRepository).existsByTextIgnoreCaseAndIdNot(anyString(), anyLong());
            verify(answerRepository, never()).existsByQuestionIdAndIsCorrectTrueAndIdNotAndDisabledAtIsNull(anyLong(), anyLong());
            verify(answerRepository).save(any(Answer.class));

            assertThat(result).isNotNull();
            assertThat(result.text()).isEqualTo(answer.getText());
        }

        @Test
        @DisplayName("Erreur - Réponse non trouvée")
        void update_answerNotFound() {
            // Given
            when(answerRepository.findById(anyLong()))
                    .thenReturn(Optional.empty());

            // When
            assertThrows(NotFoundException.class, () -> answerService.update(id, answerUpsertDTO));

            // Then
            verify(answerRepository).findById(anyLong());
            verify(answerRepository, never()).existsByTextIgnoreCaseAndIdNot(anyString(), anyLong());
            verify(answerRepository, never()).existsByQuestionIdAndIsCorrectTrueAndIdNotAndDisabledAtIsNull(anyLong(), anyLong());
            verify(answerRepository, never()).save(any(Answer.class));
        }

        @Test
        @DisplayName("Erreur - Réponse déjà existante")
        void update_answerAlreadyExists() {
            // Given
            when(answerRepository.findById(anyLong()))
                    .thenReturn(Optional.of(answer));
            when(answerRepository.existsByTextIgnoreCaseAndIdNot(anyString(), anyLong()))
                    .thenReturn(true);

            // When
            assertThrows(AlreadyExistException.class, () -> answerService.update(id, answerUpsertDTO));

            // Then
            verify(answerRepository).findById(anyLong());
            verify(answerRepository).existsByTextIgnoreCaseAndIdNot(anyString(), anyLong());
            verify(answerRepository, never()).existsByQuestionIdAndIsCorrectTrueAndIdNotAndDisabledAtIsNull(anyLong(), anyLong());
            verify(answerRepository, never()).save(any(Answer.class));
        }

        @Test
        @DisplayName("Erreur - Plus de réponse correcte active")
        void update_noCorrectAnswer() {
            // Given
            answerUpsertDTO = new AnswerUpsertDTO("Updated Answer", false);
            when(answerRepository.findById(anyLong()))
                    .thenReturn(Optional.of(answer));
            when(answerRepository.existsByTextIgnoreCaseAndIdNot(anyString(), anyLong()))
                    .thenReturn(false);
            when(answerRepository.existsByQuestionIdAndIsCorrectTrueAndIdNotAndDisabledAtIsNull(anyLong(), anyLong()))
                    .thenReturn(false);

            // When
            assertThrows(ActionNotAllowedException.class, () -> answerService.update(id, answerUpsertDTO));

            // Then
            verify(answerRepository).findById(anyLong());
            verify(answerRepository).existsByTextIgnoreCaseAndIdNot(anyString(), anyLong());
            verify(answerRepository).existsByQuestionIdAndIsCorrectTrueAndIdNotAndDisabledAtIsNull(anyLong(), anyLong());
            verify(answerRepository, never()).save(any(Answer.class));
        }
    }


    @Nested
    @DisplayName("Supprimer une réponse")
    class DeleteTest {

        @Test
        @DisplayName("Succès")
        void delete_answer() {
            // Given
            when(answerRepository.findById(anyLong()))
                    .thenReturn(Optional.of(answer));
            when(answerRepository.countByQuestionIdAndDisabledAtIsNull(anyLong()))
                    .thenReturn(2);
            when(difficultyLevelRepository.findReferenceLevelMaxAnswers())
                    .thenReturn(Optional.of((byte) 1));
            when(answerRepository.existsByQuestionIdAndIsCorrectTrueAndIdNotAndDisabledAtIsNull(anyLong(), anyLong()))
                    .thenReturn(true);

            // When
            answerService.delete(id);

            // Then
            verify(answerRepository).findById(anyLong());
            verify(answerRepository).countByQuestionIdAndDisabledAtIsNull(anyLong());
            verify(difficultyLevelRepository).findReferenceLevelMaxAnswers();
            verify(answerRepository).existsByQuestionIdAndIsCorrectTrueAndIdNotAndDisabledAtIsNull(anyLong(), anyLong());
            verify(answerRepository).deleteById(anyLong());
        }

        @Test
        @DisplayName("Erreur - Réponse non trouvée")
        void delete_answerNotFound() {
            // Given
            when(answerRepository.findById(anyLong()))
                    .thenReturn(Optional.empty());

            // When
            assertThrows(NotFoundException.class, () -> answerService.delete(id));

            // Then
            verify(answerRepository).findById(anyLong());
            verify(answerRepository, never()).countByQuestionIdAndDisabledAtIsNull(anyLong());
            verify(difficultyLevelRepository, never()).findReferenceLevelMaxAnswers();
            verify(answerRepository, never()).existsByQuestionIdAndIsCorrectTrueAndIdNotAndDisabledAtIsNull(anyLong(), anyLong());
            verify(answerRepository, never()).deleteById(anyLong());
        }

        @Test
        @DisplayName("Erreur - Pas assez de réponses actives")
        void delete_notEnoughActiveAnswers() {
            // Given
            when(answerRepository.findById(anyLong()))
                    .thenReturn(Optional.of(answer));
            when(answerRepository.countByQuestionIdAndDisabledAtIsNull(anyLong()))
                    .thenReturn(1);
            when(difficultyLevelRepository.findReferenceLevelMaxAnswers())
                    .thenReturn(Optional.of((byte) 2));

            // When
            assertThrows(ActionNotAllowedException.class, () -> answerService.delete(id));

            // Then
            verify(answerRepository).findById(anyLong());
            verify(answerRepository).countByQuestionIdAndDisabledAtIsNull(anyLong());
            verify(difficultyLevelRepository).findReferenceLevelMaxAnswers();
            verify(answerRepository, never()).existsByQuestionIdAndIsCorrectTrueAndIdNotAndDisabledAtIsNull(anyLong(), anyLong());
            verify(answerRepository, never()).deleteById(anyLong());
        }

        @Test
        @DisplayName("Erreur - Pas de réponse correcte active")
        void delete_noActiveCorrectAnswer() {
            // Given
            when(answerRepository.findById(anyLong()))
                    .thenReturn(Optional.of(answer));
            when(answerRepository.countByQuestionIdAndDisabledAtIsNull(anyLong()))
                    .thenReturn(2);
            when(difficultyLevelRepository.findReferenceLevelMaxAnswers())
                    .thenReturn(Optional.of((byte) 1));
            when(answerRepository.existsByQuestionIdAndIsCorrectTrueAndIdNotAndDisabledAtIsNull(anyLong(), anyLong()))
                    .thenReturn(false);

            // When
            assertThrows(ActionNotAllowedException.class, () -> answerService.delete(id));

            // Then
            verify(answerRepository).findById(anyLong());
            verify(answerRepository).countByQuestionIdAndDisabledAtIsNull(anyLong());
            verify(difficultyLevelRepository).findReferenceLevelMaxAnswers();
            verify(answerRepository).existsByQuestionIdAndIsCorrectTrueAndIdNotAndDisabledAtIsNull(anyLong(), anyLong());
            verify(answerRepository, never()).deleteById(anyLong());
        }
    }


    @Nested
    @DisplayName("Basculer la visibilité d'une réponse")
    class UpdateVisibilityTest {

        @Test
        @DisplayName("Succès")
        void toggleVisibility_answer() {
            // Given
            when(answerRepository.findById(anyLong()))
                    .thenReturn(Optional.of(answer));
            when(answerRepository.countByQuestionIdAndDisabledAtIsNull(anyLong()))
                    .thenReturn(3);
            when(difficultyLevelRepository.findReferenceLevelMaxAnswers())
                    .thenReturn(Optional.of((byte) 2));
            when(answerRepository.existsByQuestionIdAndIsCorrectTrueAndIdNotAndDisabledAtIsNull(anyLong(), anyLong()))
                    .thenReturn(true);

            // When
            answerService.updateVisibility(id, false);

            // Then
            verify(answerRepository).findById(anyLong());
            verify(answerRepository).countByQuestionIdAndDisabledAtIsNull(anyLong());
            verify(difficultyLevelRepository).findReferenceLevelMaxAnswers();
            verify(answerRepository).existsByQuestionIdAndIsCorrectTrueAndIdNotAndDisabledAtIsNull(anyLong(), anyLong());
            verify(answerRepository).save(any(Answer.class));
        }

        @Test
        @DisplayName("Erreur - Réponse non trouvée")
        void toggleVisibility_answerNotFound() {
            // Given
            when(answerRepository.findById(anyLong()))
                    .thenReturn(Optional.empty());

            // When
            assertThrows(NotFoundException.class, () -> answerService.updateVisibility(id, false));

            // Then
            verify(answerRepository).findById(anyLong());
            verify(answerRepository, never()).countByQuestionIdAndDisabledAtIsNull(anyLong());
            verify(difficultyLevelRepository, never()).findReferenceLevelMaxAnswers();
            verify(answerRepository, never()).existsByQuestionIdAndIsCorrectTrueAndIdNotAndDisabledAtIsNull(anyLong(), anyLong());
            verify(answerRepository, never()).save(any(Answer.class));
        }

        @Test
        @DisplayName("Erreur - Pas assez de réponses actives")
        void toggleVisibility_notEnoughActiveAnswers() {
            // Given
            when(answerRepository.findById(anyLong()))
                    .thenReturn(Optional.of(answer));
            when(answerRepository.countByQuestionIdAndDisabledAtIsNull(anyLong()))
                    .thenReturn(1);
            when(difficultyLevelRepository.findReferenceLevelMaxAnswers())
                    .thenReturn(Optional.of((byte) 2));

            // When
            assertThrows(ActionNotAllowedException.class, () -> answerService.updateVisibility(id, false));

            // Then
            verify(answerRepository).findById(anyLong());
            verify(answerRepository).countByQuestionIdAndDisabledAtIsNull(anyLong());
            verify(difficultyLevelRepository).findReferenceLevelMaxAnswers();
            verify(answerRepository, never()).existsByQuestionIdAndIsCorrectTrueAndIdNotAndDisabledAtIsNull(anyLong(), anyLong());
            verify(answerRepository, never()).save(any(Answer.class));
        }

        @Test
        @DisplayName("Erreur - Pas de réponse correcte active")
        void toggleVisibility_noActiveCorrectAnswer() {
            // Given
            when(answerRepository.findById(anyLong()))
                    .thenReturn(Optional.of(answer));
            when(answerRepository.countByQuestionIdAndDisabledAtIsNull(anyLong()))
                    .thenReturn(3);
            when(difficultyLevelRepository.findReferenceLevelMaxAnswers())
                    .thenReturn(Optional.of((byte) 2));
            when(answerRepository.existsByQuestionIdAndIsCorrectTrueAndIdNotAndDisabledAtIsNull(anyLong(), anyLong()))
                    .thenReturn(false);

            // When
            assertThrows(ActionNotAllowedException.class, () -> answerService.updateVisibility(id, false));

            // Then
            verify(answerRepository).findById(anyLong());
            verify(answerRepository).countByQuestionIdAndDisabledAtIsNull(anyLong());
            verify(difficultyLevelRepository).findReferenceLevelMaxAnswers();
            verify(answerRepository).existsByQuestionIdAndIsCorrectTrueAndIdNotAndDisabledAtIsNull(anyLong(), anyLong());
            verify(answerRepository, never()).save(any(Answer.class));
        }

        @Test
        @DisplayName("Même statut - Pas de changement de visibilité")
        void toggleVisibility_noChange() {
            // Given
            when(answerRepository.findById(anyLong()))
                    .thenReturn(Optional.of(answer));

            // When
            answerService.updateVisibility(id, true);

            // Then
            verify(answerRepository).findById(anyLong());
            verify(answerRepository, never()).countByQuestionIdAndDisabledAtIsNull(anyLong());
            verify(difficultyLevelRepository, never()).findReferenceLevelMaxAnswers();
            verify(answerRepository, never()).existsByQuestionIdAndIsCorrectTrueAndIdNotAndDisabledAtIsNull(anyLong(), anyLong());
            verify(answerRepository, never()).save(any(Answer.class));
        }
    }
}
