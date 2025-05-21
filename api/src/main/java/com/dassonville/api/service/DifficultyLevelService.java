package com.dassonville.api.service;

import com.dassonville.api.dto.DifficultyLevelAdminDTO;
import com.dassonville.api.dto.DifficultyLevelPublicDTO;
import com.dassonville.api.dto.DifficultyLevelUpsertDTO;
import com.dassonville.api.exception.ActionNotAllowedException;
import com.dassonville.api.exception.AlreadyExistException;
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


    public List<DifficultyLevelAdminDTO> getAllDifficultyLevels() {
        List<DifficultyLevel> difficultyLevels = difficultyLevelRepository.findAllByOrderByDisplayOrder();
        return difficultyLevelMapper.toAdminDTOList(difficultyLevels);
    }


    public List<DifficultyLevelPublicDTO> getAllActiveDifficultyLevels() {
        List<PublicDifficultyLevelProjection> difficultyLevels = difficultyLevelRepository.findByDisabledAtIsNullOrderByDisplayOrder();
        return difficultyLevelMapper.toPublicDTOList(difficultyLevels);
    }


    public DifficultyLevelAdminDTO findById(long id) {
        DifficultyLevel difficultyLevel = findDifficultyLevelById(id);
        return difficultyLevelMapper.toAdminDTO(difficultyLevel);
    }


    public DifficultyLevelAdminDTO create(DifficultyLevelUpsertDTO dto) {

        String normalizedNewText = TextUtils.normalizeText(dto.name());
        logger.debug("Nom normalisé : {}, depuis {}", normalizedNewText, dto.name());


        if (difficultyLevelRepository.existsByNameIgnoreCase(normalizedNewText)) {
            logger.warn("Un niveau de difficulté existe déjà avec le même nom : {}", normalizedNewText);
            throw new AlreadyExistException("Un niveau de difficulté existe déjà avec le même nom.");
        }

        DifficultyLevel difficultyLevelToCreate = difficultyLevelMapper.toModel(dto);

        short maxDisplayOrder = difficultyLevelRepository.findMaxDisplayOrder()
                .orElse((short) 0);

        difficultyLevelToCreate.setDisplayOrder((short) (maxDisplayOrder +1));

        DifficultyLevel difficultyLevelCreated = difficultyLevelRepository.save(difficultyLevelToCreate);

        return difficultyLevelMapper.toAdminDTO(difficultyLevelCreated);
    }


    public DifficultyLevelAdminDTO update(long id, DifficultyLevelUpsertDTO dto) {

        DifficultyLevel existingDifficultyLevel = findDifficultyLevelById(id);

        String normalizedNewText = TextUtils.normalizeText(dto.name());
        logger.debug("Nom normalisé : {}, depuis {}", normalizedNewText, dto.name());

        if (difficultyLevelRepository.existsByNameIgnoreCaseAndIdNot(normalizedNewText, id)) {
            logger.warn("Un niveau de difficulté existe déjà avec le même nom : {}", normalizedNewText);
            throw new AlreadyExistException("Un niveau de difficulté existe déjà avec le même nom.");
        }

        difficultyLevelMapper.updateModelFromDTO(dto, existingDifficultyLevel);

        DifficultyLevel difficultyLevelUpdated = difficultyLevelRepository.save(existingDifficultyLevel);

        return difficultyLevelMapper.toAdminDTO(difficultyLevelUpdated);
    }


    public void delete(long id) {

        DifficultyLevel difficultyLevel = findDifficultyLevelById(id);

        if (Boolean.TRUE.equals(difficultyLevel.getIsReference())) {
            logger.warn("Le niveau de difficulté avec l'ID {}, est une référence et ne peut pas être supprimé.", id);
            throw new ActionNotAllowedException("Le niveau de difficulté est une référence et ne peut pas être supprimé.");
        }

        difficultyLevelRepository.deleteById(id);
    }


    public void updateVisibility(long id, boolean visible) {

        DifficultyLevel difficultyLevel = findDifficultyLevelById(id);

        if (difficultyLevel.isVisible() == visible) return;

        difficultyLevel.setVisible(visible);

        difficultyLevelRepository.save(difficultyLevel);
    }


    @Transactional
    public void updateDisplayOrder(List<Long> newOrder) {
        // Vérification : tous les IDs doivent exister
        List<DifficultyLevel> levels = difficultyLevelRepository.findAllById(newOrder);
        if (levels.size() != newOrder.size()) {
            throw new NotFoundException("Certains IDs fournis ne correspondent pas à des niveaux de difficulté existants.");
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


    private DifficultyLevel findDifficultyLevelById(long id) {
        return difficultyLevelRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Le niveau de difficulté avec l'ID {}, n'a pas été trouvé.", id);
                    return new NotFoundException("Le niveau de difficulté n'a pas été trouvé.");
                });
    }

}
