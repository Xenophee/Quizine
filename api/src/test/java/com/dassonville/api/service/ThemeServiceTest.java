package com.dassonville.api.service;


import com.dassonville.api.dto.ThemeAdminDTO;
import com.dassonville.api.dto.ThemePublicDTO;
import com.dassonville.api.dto.ThemeUpsertDTO;
import com.dassonville.api.dto.BooleanRequestDTO;
import com.dassonville.api.exception.AlreadyExistException;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.mapper.ThemeMapper;
import com.dassonville.api.model.Theme;
import com.dassonville.api.projection.PublicThemeProjection;
import com.dassonville.api.repository.ThemeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@DisplayName("UNI - ThemeService")
public class ThemeServiceTest {

    @Mock
    private ThemeRepository themeRepository;

    private ThemeService themeService;

    private final ThemeMapper themeMapper = Mappers.getMapper(ThemeMapper.class);


    private long id;
    private BooleanRequestDTO booleanRequestDTO;
    private Theme theme;
    private PublicThemeProjection publicThemeProjection;
    private Theme themeToUpdate;
    private ThemePublicDTO themePublicDTO;
    private ThemeAdminDTO themeAdminDTO;
    private ThemeUpsertDTO themeToCreateDTO;
    private ThemeUpsertDTO themeToUpdateDTO;



    @BeforeEach
    void setUp() {
        themeService = new ThemeService(themeRepository, themeMapper);

        id = 1L;
        booleanRequestDTO = new BooleanRequestDTO(true);

        theme = new Theme();
        theme.setId(1);
        theme.setName("informatique");
        theme.setDescription("");
        theme.setCreatedAt(LocalDate.now());

        publicThemeProjection = mock(PublicThemeProjection.class);
        when(publicThemeProjection.getId()).thenReturn(1L);
        when(publicThemeProjection.getName()).thenReturn("informatique");
        when(publicThemeProjection.getDescription()).thenReturn("");
        when(publicThemeProjection.getCreatedAt()).thenReturn(LocalDate.now());

        themeToUpdate = new Theme();
        themeToUpdate.setId(1);
        themeToUpdate.setName("Nouveau nom");
        themeToUpdate.setDescription("Nouvelle description");

        themePublicDTO = themeMapper.toPublicDTO(publicThemeProjection);
        themeAdminDTO = themeMapper.toAdminDTO(theme);
        themeToCreateDTO = themeMapper.toUpsertDTO(theme);
        themeToUpdateDTO = themeMapper.toUpsertDTO(themeToUpdate);
    }


    @Nested
    @DisplayName("Tests de la méthode getAllThemes")
    class getAllThemesTest {
        @Test
        @DisplayName("Récupérer tous les thèmes")
        public void getAllThemes() {
            // Given
            when(themeRepository.findAll())
                    .thenReturn(List.of(theme));

            // When
            themeService.getAllThemes();

            // Then
            verify(themeRepository).findAll();
        }

        @Test
        @DisplayName("Récupérer tous les thèmes actifs")
        public void getAllActiveThemes() {
            // Given
            when(themeRepository.findByDisabledAtIsNull())
                    .thenReturn(List.of(publicThemeProjection));

            // When
            themeService.getAllActiveThemes();

            // Then
            verify(themeRepository).findByDisabledAtIsNull();
        }
    }


    @Nested
    @DisplayName("Tests de la méthode findById")
    class FindByIdTests {

        @Test
        @DisplayName("Récupérer un thème par son ID")
        public void findById_existingId() {
            // Given
            when(themeRepository.findByIdAndDisabledAtIsNull(any(Long.class)))
                    .thenReturn(Optional.of(publicThemeProjection));

            // When
            ThemePublicDTO result = themeService.findByIdForUser(id);

            // Then
            verify(themeRepository).findByIdAndDisabledAtIsNull(any(Long.class));
            assertThat(result).isNotNull();
            assertThat(result.name()).isEqualTo(themePublicDTO.name());
        }

        @Test
        @DisplayName("Récupérer un thème par un ID inexistant - USER")
        public void findByIdForUser_nonExistingId() {
            // Given
            when(themeRepository.findByIdAndDisabledAtIsNull(any(Long.class)))
                    .thenReturn(Optional.empty());

            // When & Then
            assertThrows(NotFoundException.class, () -> themeService.findByIdForUser(id));

            verify(themeRepository).findByIdAndDisabledAtIsNull(any(Long.class));
        }

        @Test
        @DisplayName("Récupérer un thème par son ID")
        public void findByIdForAdmin_existingId() {
            // Given
            when(themeRepository.findById(any(Long.class)))
                    .thenReturn(Optional.of(theme));

            // When
            ThemeAdminDTO result = themeService.findByIdForAdmin(id);

            // Then
            verify(themeRepository).findById(any(Long.class));
            assertThat(result).isNotNull();
            assertThat(result.name()).isEqualTo(themeAdminDTO.name());
        }

        @Test
        @DisplayName("Récupérer un thème par un ID inexistant - ADMIN")
        public void findByIdForAdmin_nonExistingId() {
            // Given
            when(themeRepository.findById(any(Long.class)))
                    .thenReturn(Optional.empty());

            // When & Then
            assertThrows(NotFoundException.class, () -> themeService.findByIdForAdmin(id));

            verify(themeRepository).findById(any(Long.class));
        }

    }


    @Nested
    @DisplayName("Tests de la méthode create")
    class CreateTests {

        @Test
        @DisplayName("Créer un nouveau thème")
        public void create_newTheme() {
            // Given
            when(themeRepository.existsByNameIgnoreCase(any(String.class)))
                    .thenReturn(false);
            when(themeRepository.save(any(Theme.class)))
                    .thenReturn(theme);

            // When
            ThemeAdminDTO result = themeService.create(themeToCreateDTO);

            // Then
            verify(themeRepository).existsByNameIgnoreCase(any(String.class));
            verify(themeRepository).save(any(Theme.class));
            assertThat(result).isNotNull();
            assertThat(result.name()).isEqualTo(themeToCreateDTO.name());
        }

        @Test
        @DisplayName("Créer un thème déjà existant")
        public void create_existingTheme() {
            // Given
            when(themeRepository.existsByNameIgnoreCase(any(String.class)))
                    .thenReturn(true);

            // When & Then
            assertThrows(AlreadyExistException.class, () -> themeService.create(themeToCreateDTO));

            verify(themeRepository).existsByNameIgnoreCase(any(String.class));
            verify(themeRepository, never()).save(any(Theme.class));
        }

    }


    @Nested
    @DisplayName("Tests de la méthode update")
    class UpdateTests {

        @Test
        @DisplayName("Mettre à jour un thème existant")
        public void update_existingTheme() {
            // Given
            when(themeRepository.findById(any(Long.class)))
                    .thenReturn(Optional.of(theme));
            when(themeRepository.existsByNameIgnoreCase(any(String.class)))
                    .thenReturn(false);
            when(themeRepository.save(any(Theme.class)))
                    .thenReturn(themeToUpdate);

            // When
            ThemeAdminDTO result = themeService.update(id, themeToUpdateDTO);

            // Then
            verify(themeRepository).findById(any(Long.class));
            verify(themeRepository).existsByNameIgnoreCase(any(String.class));
            verify(themeRepository).save(any(Theme.class));
            assertThat(result).isNotNull();
            assertThat(result.name()).isEqualTo(themeToUpdateDTO.name());
        }

        @Test
        @DisplayName("Mettre à jour un thème sans changer le nom")
        public void update_existingThemeWithoutChangingName() {
            // Given
            when(themeRepository.findById(any(Long.class)))
                    .thenReturn(Optional.of(themeToUpdate));
            when(themeRepository.save(any(Theme.class)))
                    .thenReturn(themeToUpdate);

            // When
            ThemeAdminDTO result = themeService.update(id, themeToUpdateDTO);

            // Then
            verify(themeRepository).findById(any(Long.class));
            verify(themeRepository, never()).existsByNameIgnoreCase(any(String.class));
            verify(themeRepository).save(any(Theme.class));
            assertThat(result).isNotNull();
            assertThat(result.name()).isEqualTo(themeToUpdateDTO.name());
        }

        @Test
        @DisplayName("Mettre à jour un thème inexistant")
        public void update_nonExistingTheme() {
            // Given
            when(themeRepository.findById(any(Long.class)))
                    .thenReturn(Optional.empty());

            // When & Then
            assertThrows(NotFoundException.class, () -> themeService.update(id, themeToUpdateDTO));

            verify(themeRepository).findById(any(Long.class));
            verify(themeRepository, never()).existsByNameIgnoreCase(any(String.class));
            verify(themeRepository, never()).save(any(Theme.class));
        }

        @Test
        @DisplayName("Mettre à jour un thème avec un nom déjà existant")
        public void update_existingThemeWithExistingName() {
            // Given
            when(themeRepository.findById(any(Long.class)))
                    .thenReturn(Optional.of(theme));
            when(themeRepository.existsByNameIgnoreCase(any(String.class)))
                    .thenReturn(true);

            // When & Then
            assertThrows(AlreadyExistException.class, () -> themeService.update(id, themeToUpdateDTO));

            verify(themeRepository).findById(any(Long.class));
            verify(themeRepository).existsByNameIgnoreCase(any(String.class));
            verify(themeRepository, never()).save(any(Theme.class));
        }

    }


    @Nested
    @DisplayName("Tests de la méthode delete")
    class DeleteTests {

        @Test
        @DisplayName("Supprimer un thème existant")
        public void delete_existingTheme() {
            // Given
            when(themeRepository.existsById(any(Long.class)))
                    .thenReturn(true);

            // When
            themeService.delete(id);

            // Then
            verify(themeRepository).existsById(any(Long.class));
            verify(themeRepository).deleteById(any(Long.class));
        }

        @Test
        @DisplayName("Supprimer un thème inexistant")
        public void delete_nonExistingTheme() {
            // Given
            when(themeRepository.existsById(any(Long.class)))
                    .thenReturn(false);

            // When & Then
            assertThrows(NotFoundException.class, () -> themeService.delete(id));

            verify(themeRepository).existsById(any(Long.class));
            verify(themeRepository, never()).deleteById(any(Long.class));
        }

    }


    @Nested
    @DisplayName("Tests de la méthode toggleDisable")
    class ToggleDisableTests {

        @Test
        @DisplayName("Désactiver un thème actif")
        public void disable_activeTheme() {
            // Given
            when(themeRepository.findById(any(Long.class)))
                    .thenReturn(Optional.of(theme));

            // When
            themeService.toggleDisable(id, booleanRequestDTO);

            // Then
            verify(themeRepository).findById(any(Long.class));
            verify(themeRepository).save(any(Theme.class));
        }

        @Test
        @DisplayName("Réactiver un thème désactivé")
        public void enable_disabledTheme() {
            // Given
            booleanRequestDTO = new BooleanRequestDTO(false);
            theme.setDisabledAt(theme.getCreatedAt());
            when(themeRepository.findById(any(Long.class)))
                    .thenReturn(Optional.of(theme));

            // When
            themeService.toggleDisable(id, booleanRequestDTO);

            // Then
            verify(themeRepository).findById(any(Long.class));
            verify(themeRepository).save(any(Theme.class));
        }

        @Test
        @DisplayName("Désactiver un thème inexistant")
        public void disable_nonExistingTheme() {
            // Given
            when(themeRepository.findById(any(Long.class)))
                    .thenReturn(Optional.empty());

            // When & Then
            assertThrows(NotFoundException.class, () -> themeService.toggleDisable(id, booleanRequestDTO));

            verify(themeRepository).findById(any(Long.class));
            verify(themeRepository, never()).save(any(Theme.class));
        }

    }

}
