package com.dassonville.api.integration;


import com.dassonville.api.dto.AnswerAdminDTO;
import com.dassonville.api.dto.AnswerUpsertDTO;
import com.dassonville.api.exception.ActionNotAllowedException;
import com.dassonville.api.exception.AlreadyExistException;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.model.Answer;
import com.dassonville.api.repository.AnswerRepository;
import com.dassonville.api.service.AnswerService;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DisplayName("IT - Service : Réponse")
public class AnswerServiceIT {

    @Autowired
    private AnswerService answerService;

    @Autowired
    private AnswerRepository answerRepository;


    @BeforeEach
    public void clearDatabase(@Autowired Flyway flyway) {
        flyway.clean();
        flyway.migrate();
    }


    @Nested
    @DisplayName("Création de réponse")
    class CreatingAnswer {

        @Test
        @DisplayName("Succès")
        public void shouldCreateAnswer() {
            // Given
            long questionId = 1L;
            AnswerUpsertDTO dto = new AnswerUpsertDTO(" réponse", true);

            // When
            AnswerAdminDTO createdAnswer = answerService.create(questionId, dto);

            // Then
            Answer answer = answerRepository.findById(createdAnswer.id()).get();
            assertThat(answer).isNotNull();
            assertThat(answer.getText()).isEqualTo("Réponse");
            assertThat(answer.getIsCorrect()).isTrue();
            assertThat(answer.getCreatedAt()).isNotNull();
            assertThat(answer.getDisabledAt()).isNull();
            assertThat(answer.getQuestion().getId()).isEqualTo(questionId);
        }

        @Test
        @DisplayName("Erreur - Question non trouvée")
        public void shouldFailToCreate_WhenQuestionNotFound() {
            // Given
            AnswerUpsertDTO dto = new AnswerUpsertDTO("Réponse", true);

            // When / Then
            assertThrows(NotFoundException.class, () -> answerService.create(9999L, dto));
        }

        @Test
        @DisplayName("Erreur - Réponse déjà existante")
        public void shouldFailToCreate_WhenAnswerAlreadyExists() {
            // Given
            AnswerUpsertDTO dto = new AnswerUpsertDTO(" platon", true);

            // When / Then
            assertThrows(AlreadyExistException.class, () -> answerService.create(1L, dto));
        }
    }


    @Nested
    @DisplayName("Mise à jour de réponse")
    class UpdatingAnswer {

        @Test
        @DisplayName("Succès")
        public void shouldUpdateAnswer() {
            // Given
            AnswerUpsertDTO dto = new AnswerUpsertDTO(" réponse modifiée", true);

            // When
            AnswerAdminDTO updatedAnswer = answerService.update(3L, dto);

            // Then
            Answer answer = answerRepository.findById(updatedAnswer.id()).get();
            assertThat(answer).isNotNull();
            assertThat(answer.getText()).isEqualTo("Réponse modifiée");
            assertThat(answer.getIsCorrect()).isTrue();
            assertThat(answer.getCreatedAt()).isNotNull();
            assertThat(answer.getUpdatedAt()).isNotNull();
            assertThat(answer.getDisabledAt()).isNull();
            assertThat(answer.getQuestion().getId()).isEqualTo(1L);
        }

        @Test
        @DisplayName("Erreur - Réponse non trouvée")
        public void shouldFailToUpdate_WhenAnswerNotFound() {
            // Given
            AnswerUpsertDTO dto = new AnswerUpsertDTO("Réponse", true);

            // When / Then
            assertThrows(NotFoundException.class, () -> answerService.update(9999L, dto));
        }

        @Test
        @DisplayName("Erreur - Réponse déjà existante")
        public void shouldFailToUpdate_WhenAnswerAlreadyExists() {
            // Given
            long answerId = 2L;
            AnswerUpsertDTO dto = new AnswerUpsertDTO(" socrate", true);

            // When / Then
            assertThrows(AlreadyExistException.class, () -> answerService.update(answerId, dto));
        }

        @Test
        @DisplayName("Erreur - Réponse correcte absente")
        public void shouldFailToUpdate_WhenCorrectAnswerDisabled() {
            // Given
            AnswerUpsertDTO dto = new AnswerUpsertDTO("Réponse", false);

            // When / Then
            assertThrows(ActionNotAllowedException.class, () -> answerService.update(1L, dto));
        }
    }


    @Nested
    @DisplayName("Suppression de réponse")
    class DeletingAnswer {

        @Test
        @DisplayName("Succès")
        public void shouldDeleteAnswer() {
            // Given
            long answerId = 3L;
            answerService.create(1L, new AnswerUpsertDTO("Réponse", true));

            // When
            answerService.delete(answerId);

            // Then
            assertThat(answerRepository.existsById(answerId)).isFalse();
        }

        @Test
        @DisplayName("Erreur - Réponse non trouvée")
        public void shouldFailToDelete_WhenAnswerNotFound() {
            // Given
            long answerId = 9999L;

            // When / Then
            assertThrows(NotFoundException.class, () -> answerService.delete(answerId));
        }

        @Test
        @DisplayName("Erreur - Nombre de réponses restantes insuffisant")
        public void shouldFailToDelete_WhenMinimumAnswersNotMet() {
            // Given
            long answerId = 2L;

            // When / Then
            assertThrows(ActionNotAllowedException.class, () -> answerService.delete(answerId));
        }

        @Test
        @DisplayName("Erreur - Réponse correcte absente")
        public void shouldFailToDelete_WhenCorrectAnswerDeleted() {
            // Given
            long answerId = 1L;
            answerService.create(1L, new AnswerUpsertDTO("Réponse", false));

            // When / Then
            assertThrows(ActionNotAllowedException.class, () -> answerService.delete(answerId));
        }
    }


    @Nested
    @DisplayName("Désactivation / activation de réponse")
    class DisablingAnswer {

        @Test
        @DisplayName("Succès - Désactivation de réponse")
        public void shouldDisableAnswer() {
            // Given
            long answerId = 1L;
            answerService.create(1L, new AnswerUpsertDTO("Réponse", true));

            // When
            answerService.updateVisibility(answerId, false);

            // Then
            Answer answer = answerRepository.findById(answerId).get();
            assertThat(answer.getDisabledAt()).isNotNull();
        }

        @Test
        @DisplayName("Succès - Activation de réponse")
        public void shouldEnableAnswer() {
            // Given
            long answerId = 1L;
            answerService.create(1L, new AnswerUpsertDTO("Réponse", true));
            answerService.updateVisibility(answerId, false);

            // When
            answerService.updateVisibility(answerId, true);

            // Then
            Answer answer = answerRepository.findById(answerId).get();
            assertThat(answer.getDisabledAt()).isNull();
        }

        @Test
        @DisplayName("Erreur - Réponse non trouvée")
        public void shouldFailToDisable_WhenAnswerNotFound() {
            // Given
            long answerId = 9999L;

            // When / Then
            assertThrows(NotFoundException.class, () -> answerService.updateVisibility(answerId, false));
        }

        @Test
        @DisplayName("Erreur - Nombre de réponses restantes insuffisant")
        public void shouldFailToDisable_WhenMinimumAnswersNotMet() {
            // Given
            long answerId = 2L;

            // When / Then
            assertThrows(ActionNotAllowedException.class, () -> answerService.updateVisibility(answerId, false));
        }

        @Test
        @DisplayName("Erreur - Réponse correcte absente")
        public void shouldFailToDisable_WhenCorrectAnswerDeleted() {
            // Given
            long answerId = 1L;
            answerService.create(1L, new AnswerUpsertDTO("Réponse", false));

            // When / Then
            assertThrows(ActionNotAllowedException.class, () -> answerService.updateVisibility(answerId, false));
        }
    }
}
