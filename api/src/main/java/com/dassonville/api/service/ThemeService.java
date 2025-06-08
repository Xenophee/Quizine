package com.dassonville.api.service;


import com.dassonville.api.dto.ThemeAdminDTO;
import com.dassonville.api.dto.ThemePublicDTO;
import com.dassonville.api.dto.ThemeUpsertDTO;
import com.dassonville.api.exception.ActionNotAllowedException;
import com.dassonville.api.exception.AlreadyExistException;
import com.dassonville.api.exception.ErrorCode;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.mapper.ThemeMapper;
import com.dassonville.api.model.Theme;
import com.dassonville.api.projection.IdAndNameProjection;
import com.dassonville.api.projection.PublicThemeProjection;
import com.dassonville.api.repository.ThemeRepository;
import com.dassonville.api.util.TextUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ThemeService {

    private static final Logger logger = LoggerFactory.getLogger(ThemeService.class);

    private final ThemeRepository themeRepository;
    private final ThemeMapper themeMapper;



    /**
     * Récupère tous les thèmes actifs (non désactivés) pour l'utilisateur final.
     *
     * <p>Cette méthode retourne une liste de thèmes actifs triés par ordre alphabétique, mappés
     * vers des DTOs publics pour l'affichage utilisateur.</p>
     *
     * @return une liste de {@link ThemePublicDTO} contenant les thèmes actifs
     */
    public List<ThemePublicDTO> getAllActiveThemes() {
        List<PublicThemeProjection> themes = themeRepository.findByDisabledAtIsNullOrderByName();
        return themeMapper.toPublicDTOList(themes);
    }


    /**
     * Récupère tous les détails des thèmes pour l'administration.
     *
     * <p>Cette méthode retourne une liste de thèmes triés par ordre alphabétique et par nom de catégorie,
     * mappés vers des DTOs d'administration.</p>
     *
     * @return une liste de {@link ThemeAdminDTO} contenant les détails des thèmes
     */
    public List<ThemeAdminDTO> getAllThemesDetails() {
        List<Theme> themes = themeRepository.findAllByOrderByNameAndCategoryName();
        return themeMapper.toAdminDTOList(themes);
    }


    /**
     * Récupère tous les thèmes avec leurs identifiants et noms.
     *
     * <p>Cette méthode retourne une liste de projections contenant les IDs et noms
     * des thèmes triés par nom.</p>
     *
     * @return une liste de {@link IdAndNameProjection} contenant les IDs et noms des thèmes
     */
    public List<IdAndNameProjection> getAllThemes() {
        return themeRepository.findAllByOrderByName();
    }


    /**
     * Récupère les détails d'un thème pour l'administration.
     *
     * <p>Cette méthode recherche un thème par son identifiant et le mappe vers un
     * {@link ThemeAdminDTO} pour l'administration.</p>
     *
     * @param id l'identifiant du thème à rechercher
     * @return un {@link ThemeAdminDTO} contenant les détails du thème
     * @throws NotFoundException si le thème avec l'ID spécifié n'existe pas
     */
    @Transactional
    public ThemeAdminDTO findByIdForAdmin(long id) {

        Theme theme = findThemeById(id);

        return themeMapper.toAdminDTO(theme);
    }


    /**
     * Crée un nouveau thème.
     *
     * <p>Cette méthode normalise le nom du thème, vérifie qu'aucun thème avec le même nom
     * n'existe déjà, puis crée et sauvegarde le nouveau thème dans la base de données.</p>
     *
     * @param dto les informations du thème à créer contenues dans un {@link ThemeUpsertDTO}
     * @return un {@link ThemeAdminDTO} contenant les détails du thème créé
     * @throws AlreadyExistException si un thème avec le même nom existe déjà
     */
    public ThemeAdminDTO create(ThemeUpsertDTO dto) {

        String normalizedNewText = TextUtils.normalizeText(dto.name());
        logger.debug("Nom normalisé : {}, depuis {}", normalizedNewText, dto.name());

        if (themeRepository.existsByNameIgnoreCase(normalizedNewText)) {
            logger.warn("Un thème existe déjà avec le même nom : {}", normalizedNewText);
            throw new AlreadyExistException(ErrorCode.THEME_ALREADY_EXISTS, normalizedNewText);
        }

        Theme themeToCreate = themeMapper.toModel(dto);

        Theme themeCreated = themeRepository.save(themeToCreate);

        return themeMapper.toAdminDTO(themeCreated);
    }


    /**
     * Met à jour un thème existant avec les nouvelles informations fournies dans le DTO.
     *
     * <p>Cette méthode recherche le thème par son ID, normalise le nouveau nom,
     * vérifie qu'aucun autre thème avec le même nom n'existe déjà, puis met à jour
     * et sauvegarde le thème dans la base de données.</p>
     *
     * @param id l'identifiant du thème à mettre à jour
     * @param dto les nouvelles informations du thème contenues dans un {@link ThemeUpsertDTO}
     * @return un {@link ThemeAdminDTO} contenant les détails du thème mis à jour
     * @throws NotFoundException si le thème avec l'ID spécifié n'existe pas
     * @throws AlreadyExistException si un autre thème avec le même nom existe déjà
     */
    @Transactional
    public ThemeAdminDTO update(long id, ThemeUpsertDTO dto) {

        Theme themeToUpdate = findThemeById(id);

        String normalizedNewText = TextUtils.normalizeText(dto.name());
        logger.debug("Nom normalisé : {}, depuis {}", normalizedNewText, dto.name());

        if (themeRepository.existsByNameIgnoreCaseAndIdNot(normalizedNewText, id)) {
            logger.warn("Un thème existe déjà avec le même nom : {}", normalizedNewText);
            throw new AlreadyExistException(ErrorCode.THEME_ALREADY_EXISTS, normalizedNewText);
        }

        themeMapper.updateModelFromDTO(dto, themeToUpdate);

        Theme themeUpdated = themeRepository.save(themeToUpdate);

        return themeMapper.toAdminDTO(themeUpdated);
    }


    /**
     * Supprime un thème par son identifiant.
     *
     * <p>Cette méthode vérifie si le thème existe et s'il ne contient pas de quiz.
     * Si ces conditions sont remplies, elle supprime le thème de la base de données.</p>
     *
     * @param id l'identifiant du thème à supprimer
     * @throws NotFoundException si le thème avec l'ID spécifié n'existe pas
     * @throws ActionNotAllowedException si le thème contient des quiz
     */
    public void delete(long id) {

        if (!themeRepository.existsById(id)) {
            logger.warn("Le thème à supprimer avec l'ID {}, n'a pas été trouvé.", id);
            throw new NotFoundException(ErrorCode.THEME_NOT_FOUND, id);
        }

        if (themeRepository.existsByIdAndQuizzesIsNotEmpty(id)) {
            logger.warn("Le thème à supprimer avec l'ID {}, contient des quiz.", id);
            throw new ActionNotAllowedException(ErrorCode.THEME_CONTAINS_QUIZZES, id);
        }

        themeRepository.deleteById(id);
    }


    /**
     * Met à jour la visibilité d'un thème.
     *
     * <p>Cette méthode active ou désactive un thème en fonction de l'état de visibilité fourni.
     * Si le thème est activé, elle vérifie qu'il contient au moins un quiz actif.</p>
     *
     * @param id l'identifiant du thème à mettre à jour
     * @param visible l'état de visibilité souhaité ({@code true} pour activer, {@code false} pour désactiver)
     * @throws NotFoundException si le thème avec l'ID spécifié n'existe pas
     * @throws ActionNotAllowedException si le thème ne contient pas de quiz actif
     */
    public void updateVisibility(long id, boolean visible) {

        Theme theme = findThemeById(id);

        if (theme.isVisible() == visible) return;

        if (visible) {
            logger.debug("Demande d'activation du thème avec l'ID {}, vérification du nombre de quiz actifs", id);
            hasMinimumQuizzes(id);
        }

        theme.setVisible(visible);

        themeRepository.save(theme);
    }



    /**
     * Recherche un thème par son identifiant.
     *
     * <p>Cette méthode tente de trouver un thème par son identifiant.
     * Si le thème n'est pas trouvé, elle lève une exception.</p>
     *
     * @param id l'identifiant du thème à rechercher
     * @return le {@link Theme} trouvé
     * @throws NotFoundException si aucun thème ne correspond à l'identifiant fourni
     */
    private Theme findThemeById(long id) {
        return themeRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Le thème avec l'ID {}, n'a pas été trouvé.", id);
                    return new NotFoundException(ErrorCode.THEME_NOT_FOUND, id);
                });
    }

    /**
     * Vérifie si un thème contient au moins un quiz actif.
     *
     * <p>Cette méthode compte le nombre de quiz actifs associés à un thème.
     * Si aucun quiz actif n'est trouvé, elle lève une exception.</p>
     *
     * @param id l'identifiant du thème à vérifier
     * @throws ActionNotAllowedException si le thème ne contient pas de quiz actif
     */
    private void hasMinimumQuizzes(long id) {

        int numberOfActiveQuizzes = themeRepository.countByIdAndQuizzesDisabledAtIsNull(id);

        if (numberOfActiveQuizzes == 0) {
            logger.warn("Le thème avec l'ID {}, n'a pas de quiz actif.", id);
            throw new ActionNotAllowedException(ErrorCode.THEME_CONTAINS_NO_QUIZZES, id);
        } else {
            logger.debug("Le thème avec l'ID {}, a {} quiz actifs.", id, numberOfActiveQuizzes);
        }
    }
}
