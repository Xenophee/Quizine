package com.dassonville.api.integration;


import com.dassonville.api.dto.response.AnswerAdminDTO;
import com.dassonville.api.dto.request.ClassicAnswerUpsertDTO;
import com.dassonville.api.exception.ActionNotAllowedException;
import com.dassonville.api.exception.AlreadyExistException;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.model.ClassicAnswer;
import com.dassonville.api.repository.ClassicAnswerRepository;
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
public class ClassicAnswerServiceIT {

    @Autowired
    private AnswerService answerService;

    @Autowired
    private ClassicAnswerRepository classicAnswerRepository;


    @BeforeEach
    public void clearDatabase(@Autowired Flyway flyway) {
        flyway.clean();
        flyway.migrate();
    }


    @Nested
    @DisplayName("Création de réponse")
    class CreatingClassicAnswer {

        @Test
        @DisplayName("Succès")
        public void shouldCreateAnswer() {
            // Given
            long questionId = 1L;
            ClassicAnswerUpsertDTO dto = new ClassicAnswerUpsertDTO(" réponse", true);

            // When
            AnswerAdminDTO createdAnswer = answerService.create(questionId, dto);

            // Then
            ClassicAnswer classicAnswer = classicAnswerRepository.findById(createdAnswer.id()).get();
            assertThat(classicAnswer).isNotNull();
            assertThat(classicAnswer.getText()).isEqualTo("Réponse");
            assertThat(classicAnswer.getIsCorrect()).isTrue();
            assertThat(classicAnswer.getCreatedAt()).isNotNull();
            assertThat(classicAnswer.getDisabledAt()).isNull();
            assertThat(classicAnswer.getQuestion().getId()).isEqualTo(questionId);
        }

        @Test
        @DisplayName("Erreur - Question non trouvée")
        public void shouldFailToCreate_WhenQuestionNotFound() {
            // Given
            ClassicAnswerUpsertDTO dto = new ClassicAnswerUpsertDTO("Réponse", true);

            // When / Then
            assertThrows(NotFoundException.class, () -> answerService.create(9999L, dto));
        }

        @Test
        @DisplayName("Erreur - Réponse déjà existante")
        public void shouldFailToCreate_WhenAnswerAlreadyExists() {
            // Given
            ClassicAnswerUpsertDTO dto = new ClassicAnswerUpsertDTO(" platon", true);

            // When / Then
            assertThrows(AlreadyExistException.class, () -> answerService.create(1L, dto));
        }
    }


    @Nested
    @DisplayName("Mise à jour de réponse")
    class UpdatingClassicAnswer {

        @Test
        @DisplayName("Succès")
        public void shouldUpdateAnswer() {
            // Given
            ClassicAnswerUpsertDTO dto = new ClassicAnswerUpsertDTO(" réponse modifiée", true);

            // When
            AnswerAdminDTO updatedAnswer = answerService.update(3L, dto);

            // Then
            ClassicAnswer classicAnswer = classicAnswerRepository.findById(updatedAnswer.id()).get();
            assertThat(classicAnswer).isNotNull();
            assertThat(classicAnswer.getText()).isEqualTo("Réponse modifiée");
            assertThat(classicAnswer.getIsCorrect()).isTrue();
            assertThat(classicAnswer.getCreatedAt()).isNotNull();
            assertThat(classicAnswer.getUpdatedAt()).isNotNull();
            assertThat(classicAnswer.getDisabledAt()).isNull();
            assertThat(classicAnswer.getQuestion().getId()).isEqualTo(1L);
        }

        @Test
        @DisplayName("Erreur - Réponse non trouvée")
        public void shouldFailToUpdate_WhenAnswerNotFound() {
            // Given
            ClassicAnswerUpsertDTO dto = new ClassicAnswerUpsertDTO("Réponse", true);

            // When / Then
            assertThrows(NotFoundException.class, () -> answerService.update(9999L, dto));
        }

        @Test
        @DisplayName("Erreur - Réponse déjà existante")
        public void shouldFailToUpdate_WhenAnswerAlreadyExists() {
            // Given
            long answerId = 2L;
            ClassicAnswerUpsertDTO dto = new ClassicAnswerUpsertDTO(" socrate", true);

            // When / Then
            assertThrows(AlreadyExistException.class, () -> answerService.update(answerId, dto));
        }

        @Test
        @DisplayName("Erreur - Réponse correcte absente")
        public void shouldFailToUpdate_WhenCorrectAnswerDisabled() {
            // Given
            ClassicAnswerUpsertDTO dto = new ClassicAnswerUpsertDTO("Réponse", false);

            // When / Then
            assertThrows(ActionNotAllowedException.class, () -> answerService.update(1L, dto));
        }
    }


    @Nested
    @DisplayName("Suppression de réponse")
    class DeletingClassicAnswer {

        @Test
        @DisplayName("Succès")
        public void shouldDeleteAnswer() {
            // Given
            long answerId = 3L;
            answerService.create(1L, new ClassicAnswerUpsertDTO("Réponse", true));

            // When
            answerService.delete(answerId);

            // Then
            assertThat(classicAnswerRepository.existsById(answerId)).isFalse();
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
            answerService.create(1L, new ClassicAnswerUpsertDTO("Réponse", false));

            // When / Then
            assertThrows(ActionNotAllowedException.class, () -> answerService.delete(answerId));
        }
    }


    @Nested
    @DisplayName("Désactivation / activation de réponse")
    class DisablingClassicAnswer {

        @Test
        @DisplayName("Succès - Désactivation de réponse")
        public void shouldDisableAnswer() {
            // Given
            long answerId = 1L;
            answerService.create(1L, new ClassicAnswerUpsertDTO("Réponse", true));

            // When
            answerService.updateVisibility(answerId, false);

            // Then
            ClassicAnswer classicAnswer = classicAnswerRepository.findById(answerId).get();
            assertThat(classicAnswer.getDisabledAt()).isNotNull();
        }

        @Test
        @DisplayName("Succès - Activation de réponse")
        public void shouldEnableAnswer() {
            // Given
            long answerId = 1L;
            answerService.create(1L, new ClassicAnswerUpsertDTO("Réponse", true));
            answerService.updateVisibility(answerId, false);

            // When
            answerService.updateVisibility(answerId, true);

            // Then
            ClassicAnswer classicAnswer = classicAnswerRepository.findById(answerId).get();
            assertThat(classicAnswer.getDisabledAt()).isNull();
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
            answerService.create(1L, new ClassicAnswerUpsertDTO("Réponse", false));

            // When / Then
            assertThrows(ActionNotAllowedException.class, () -> answerService.updateVisibility(answerId, false));
        }
    }
}
