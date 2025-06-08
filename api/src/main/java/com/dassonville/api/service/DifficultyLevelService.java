package com.dassonville.api.service;

import com.dassonville.api.dto.DifficultyLevelAdminDTO;
import com.dassonville.api.dto.DifficultyLevelPublicDTO;
import com.dassonville.api.dto.DifficultyLevelUpsertDTO;
import com.dassonville.api.exception.ActionNotAllowedException;
import com.dassonville.api.exception.AlreadyExistException;
import com.dassonville.api.exception.ErrorCode;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.mapper.DifficultyLevelMapper;
import com.dassonville.api.model.DifficultyLevel;
import com.dassonville.api.projection.PublicDifficultyLevelProjection;
import com.dassonville.api.repository.DifficultyLevelRepository;
import com.dassonville.api.util.TextUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Service
@RequiredArgsConstructor
public class DifficultyLevelService {

    private static final Logger logger = LoggerFactory.getLogger(DifficultyLevelService.class);

    private final DifficultyLevelRepository difficultyLevelRepository;
    private final DifficultyLevelMapper difficultyLevelMapper;


    /**
     * Récupère tous les niveaux de difficulté triés par ordre d'affichage.
     *
     * @return une liste de {@link DifficultyLevelAdminDTO} contenant les niveaux de difficulté
     */
    public List<DifficultyLevelAdminDTO> getAllDifficultyLevels() {
        List<DifficultyLevel> difficultyLevels = difficultyLevelRepository.findAllByOrderByDisplayOrder();
        return difficultyLevelMapper.toAdminDTOList(difficultyLevels);
    }


    /**
     * Récupère tous les niveaux de difficulté actifs triés par ordre d'affichage.
     *
     * @return une liste de {@link DifficultyLevelPublicDTO} contenant les niveaux de difficulté actifs
     */
    public List<DifficultyLevelPublicDTO> getAllActiveDifficultyLevels() {
        List<PublicDifficultyLevelProjection> difficultyLevels = difficultyLevelRepository.findByDisabledAtIsNullOrderByDisplayOrder();
        return difficultyLevelMapper.toPublicDTOList(difficultyLevels);
    }


    /**
     * Récupère les détails d'un niveau de difficulté par son identifiant.
     *
     * @param id l'identifiant du niveau de difficulté
     * @return un {@link DifficultyLevelAdminDTO} contenant les détails du niveau de difficulté
     * @throws NotFoundException si le niveau de difficulté avec l'ID spécifié n'existe pas
     */
    public DifficultyLevelAdminDTO findById(long id) {
        DifficultyLevel difficultyLevel = findDifficultyLevelById(id);
        return difficultyLevelMapper.toAdminDTO(difficultyLevel);
    }


    /**
     * Crée un nouveau niveau de difficulté.
     *
     * <p>Cette méthode normalise le nom du niveau de difficulté, vérifie qu'aucun niveau
     * avec le même nom n'existe déjà, puis crée et sauvegarde le nouveau niveau dans la base
     * de données avec un ordre d'affichage incrémenté.</p>
     *
     * @param dto les informations du niveau de difficulté à créer contenues dans un {@link DifficultyLevelUpsertDTO}
     * @return un {@link DifficultyLevelAdminDTO} contenant les détails du niveau de difficulté créé
     * @throws AlreadyExistException si un niveau de difficulté avec le même nom existe déjà
     */
    public DifficultyLevelAdminDTO create(DifficultyLevelUpsertDTO dto) {

        String normalizedNewText = TextUtils.normalizeText(dto.name());
        logger.debug("Nom normalisé : {}, depuis {}", normalizedNewText, dto.name());


        if (difficultyLevelRepository.existsByNameIgnoreCase(normalizedNewText)) {
            logger.warn("Un niveau de difficulté existe déjà avec le même nom : {}", normalizedNewText);
            throw new AlreadyExistException(ErrorCode.DIFFICULTY_ALREADY_EXISTS, normalizedNewText);
        }

        DifficultyLevel difficultyLevelToCreate = difficultyLevelMapper.toModel(dto);

        short maxDisplayOrder = difficultyLevelRepository.findMaxDisplayOrder()
                .orElse((short) 0);

        difficultyLevelToCreate.setDisplayOrder((short) (maxDisplayOrder +1));

        DifficultyLevel difficultyLevelCreated = difficultyLevelRepository.save(difficultyLevelToCreate);

        return difficultyLevelMapper.toAdminDTO(difficultyLevelCreated);
    }


    /**
     * Met à jour un niveau de difficulté existant avec les nouvelles informations fournies dans le DTO.
     *
     * <p>Cette méthode recherche le niveau de difficulté par son ID, normalise le nouveau nom,
     * vérifie qu'aucun autre niveau avec le même nom n'existe déjà, puis met à jour et sauvegarde
     * le niveau dans la base de données.</p>
     *
     * @param id l'identifiant du niveau de difficulté à mettre à jour
     * @param dto les nouvelles informations du niveau de difficulté contenues dans un {@link DifficultyLevelUpsertDTO}
     * @return un {@link DifficultyLevelAdminDTO} contenant les détails du niveau de difficulté mis à jour
     * @throws NotFoundException si le niveau de difficulté avec l'ID spécifié n'existe pas
     * @throws AlreadyExistException si un autre niveau de difficulté avec le même nom existe déjà
     */
    public DifficultyLevelAdminDTO update(long id, DifficultyLevelUpsertDTO dto) {

        DifficultyLevel existingDifficultyLevel = findDifficultyLevelById(id);

        String normalizedNewText = TextUtils.normalizeText(dto.name());
        logger.debug("Nom normalisé : {}, depuis {}", normalizedNewText, dto.name());

        if (difficultyLevelRepository.existsByNameIgnoreCaseAndIdNot(normalizedNewText, id)) {
            logger.warn("Un niveau de difficulté existe déjà avec le même nom : {}", normalizedNewText);
            throw new AlreadyExistException(ErrorCode.DIFFICULTY_ALREADY_EXISTS, normalizedNewText);
        }

        difficultyLevelMapper.updateModelFromDTO(dto, existingDifficultyLevel);

        DifficultyLevel difficultyLevelUpdated = difficultyLevelRepository.save(existingDifficultyLevel);

        return difficultyLevelMapper.toAdminDTO(difficultyLevelUpdated);
    }


    /**
     * Supprime un niveau de difficulté par son identifiant.
     *
     * <p>Cette méthode vérifie si le niveau de difficulté est une référence avant de le supprimer.
     * Si c'est une référence, une exception est levée.</p>
     *
     * @param id l'identifiant du niveau de difficulté à supprimer
     * @throws NotFoundException si le niveau de difficulté avec l'ID spécifié n'existe pas
     * @throws ActionNotAllowedException si le niveau de difficulté est une référence
     */
    public void delete(long id) {

        DifficultyLevel difficultyLevel = findDifficultyLevelById(id);

        if (Boolean.TRUE.equals(difficultyLevel.getIsReference())) {
            logger.warn("Le niveau de difficulté avec l'ID {}, est une référence et ne peut pas être supprimé.", id);
            throw new ActionNotAllowedException(ErrorCode.DIFFICULTY_IS_REFERENCE, id);
        }

        difficultyLevelRepository.deleteById(id);
    }


    /**
     * Met à jour la visibilité d'un niveau de difficulté.
     *
     * <p>Cette méthode active ou désactive un niveau de difficulté en fonction de l'état de visibilité fourni.
     * Si le niveau est déjà dans l'état souhaité, aucune action n'est effectuée.</p>
     *
     * @param id l'identifiant du niveau de difficulté à mettre à jour
     * @param visible l'état de visibilité souhaité ({@code true} pour activer, {@code false} pour désactiver)
     * @throws NotFoundException si le niveau de difficulté avec l'ID spécifié n'existe pas
     */
    public void updateVisibility(long id, boolean visible) {

        DifficultyLevel difficultyLevel = findDifficultyLevelById(id);

        if (difficultyLevel.isVisible() == visible) return;

        difficultyLevel.setVisible(visible);

        difficultyLevelRepository.save(difficultyLevel);
    }


    /**
     * Met à jour l'ordre d'affichage des niveaux de difficulté.
     *
     * <p>Cette méthode vérifie que tous les IDs fournis existent, puis réaffecte les ordres
     * d'affichage en fonction de la nouvelle liste d'IDs fournie.</p>
     *
     * @param newOrder une liste d'IDs représentant le nouvel ordre d'affichage
     * @throws NotFoundException si certains IDs ne correspondent pas à des niveaux de difficulté existants
     */
    @Transactional
    public void updateDisplayOrder(List<Long> newOrder) {

        // Vérification : tous les IDs doivent exister
        List<DifficultyLevel> levels = difficultyLevelRepository.findAllById(newOrder);
        if (levels.size() != newOrder.size()) {
            logger.warn("Certains IDs fournis ne correspondent pas à des niveaux de difficulté existants.");
            throw new NotFoundException(ErrorCode.DIFFICULTIES_NOT_FOUND, newOrder.toString());
        }

        // Étape 1 : Décale temporairement les display_order pour éviter les conflits en base
        levels.forEach(level -> level.setDisplayOrder((short) (level.getDisplayOrder() + 1000)));
        difficultyLevelRepository.saveAll(levels);
        difficultyLevelRepository.flush();

        // Étape 2 : Réaffecte les bons ordres d'affichage indiqués dans newOrder
        Map<Long, DifficultyLevel> levelMap = levels.stream() // Map les niveaux de difficulté à leurs IDs
                .collect(Collectors.toMap(DifficultyLevel::getId, level -> level));

        IntStream.range(0, newOrder.size())
                .forEach(index -> levelMap
                        .get(newOrder.get(index))
                        .setDisplayOrder((short) (index + 1)));

        difficultyLevelRepository.saveAll(levels);
    }


    /**
     * Recherche un niveau de difficulté par son identifiant.
     *
     * <p>Cette méthode tente de trouver un niveau de difficulté par son identifiant.
     * Si le niveau n'est pas trouvé, elle lève une exception.</p>
     *
     * @param id l'identifiant du niveau de difficulté à rechercher
     * @return le {@link DifficultyLevel} trouvé
     * @throws NotFoundException si aucun niveau de difficulté ne correspond à l'identifiant fourni
     */
    private DifficultyLevel findDifficultyLevelById(long id) {
        return difficultyLevelRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Le niveau de difficulté avec l'ID {}, n'a pas été trouvé.", id);
                    return new NotFoundException(ErrorCode.DIFFICULTY_NOT_FOUND, id);
                });
    }

}
