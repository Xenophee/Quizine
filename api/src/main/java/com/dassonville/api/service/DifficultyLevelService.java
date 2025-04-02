package com.dassonville.api.service;

import com.dassonville.api.dto.DifficultyLevelAdminDTO;
import com.dassonville.api.dto.DifficultyLevelPublicDTO;
import com.dassonville.api.dto.DifficultyLevelUpsertDTO;
import com.dassonville.api.dto.ToggleDisableRequestDTO;
import com.dassonville.api.exception.AlreadyExistException;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.mapper.DifficultyLevelMapper;
import com.dassonville.api.model.DifficultyLevel;
import com.dassonville.api.projection.PublicDifficultyLevelProjection;
import com.dassonville.api.repository.DifficultyLevelRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.util.StringUtils.capitalize;


@Service
@RequiredArgsConstructor
public class DifficultyLevelService {

    private static final Logger logger = LoggerFactory.getLogger(DifficultyLevelService.class);

    private final DifficultyLevelRepository difficultyLevelRepository;
    private final DifficultyLevelMapper difficultyLevelMapper;



    public List<DifficultyLevelAdminDTO> getAllDifficultyLevels() {
        List<DifficultyLevel> difficultyLevels = difficultyLevelRepository.findAll();
        return difficultyLevelMapper.toAdminDTOList(difficultyLevels);
    }


    public List<DifficultyLevelPublicDTO> getAllActiveDifficultyLevels() {
        List<PublicDifficultyLevelProjection> difficultyLevels = difficultyLevelRepository.findByDisabledAtIsNull();
        return difficultyLevelMapper.toPublicDTOList(difficultyLevels);
    }


    public DifficultyLevelAdminDTO findById(long id) {
        DifficultyLevel difficultyLevel = difficultyLevelRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Le niveau de difficulté avec l'ID {}, n'a pas été trouvé.", id);
                    return new NotFoundException("Le niveau de difficulté n'a pas été trouvé.");
                });

        return difficultyLevelMapper.toAdminDTO(difficultyLevel);
    }


    public DifficultyLevelAdminDTO create(DifficultyLevelUpsertDTO dto) {
        DifficultyLevel difficultyLevelToCreate = difficultyLevelMapper.toModel(dto);

        difficultyLevelToCreate.setName(capitalize(difficultyLevelToCreate.getName()));

        if (difficultyLevelRepository.existsByNameIgnoreCase(difficultyLevelToCreate.getName())) {
            logger.warn("Un niveau de difficulté existe déjà avec le même nom : {}", difficultyLevelToCreate.getName());
            throw new AlreadyExistException("Un niveau de difficulté existe déjà avec le même nom.");
        }

        DifficultyLevel difficultyLevelCreated = difficultyLevelRepository.save(difficultyLevelToCreate);

        return difficultyLevelMapper.toAdminDTO(difficultyLevelCreated);
    }


    public DifficultyLevelAdminDTO update(long id, DifficultyLevelUpsertDTO dto) {
        DifficultyLevel existingDifficultyLevel = difficultyLevelRepository.findById(id).orElseThrow(() -> {
            logger.warn("Le niveau de difficulté à modifier avec l'ID {}, n'a pas été trouvé.", id);
            return new NotFoundException("Le niveau de difficulté à modifier n'a pas été trouvé.");
        });

        if (!existingDifficultyLevel.getName().equalsIgnoreCase(dto.name()) && difficultyLevelRepository.existsByNameIgnoreCase(dto.name())) {
            logger.warn("Un niveau de difficulté existe déjà avec le même nom : {}", dto.name());
            throw new AlreadyExistException("Un niveau de difficulté existe déjà avec le même nom.");
        }

        existingDifficultyLevel.setName(capitalize(dto.name()));
        existingDifficultyLevel.setMaxResponses(dto.maxResponses());
        existingDifficultyLevel.setTimerSeconds(dto.timerSeconds());
        existingDifficultyLevel.setPointsPerQuestion(dto.pointsPerQuestion());

        DifficultyLevel difficultyLevelUpdated = difficultyLevelRepository.save(existingDifficultyLevel);
        return difficultyLevelMapper.toAdminDTO(difficultyLevelUpdated);
    }


    public void delete(long id) {
        if (!difficultyLevelRepository.existsById(id)) {
            logger.warn("Le niveau de difficulté à supprimer avec l'ID {}, n'a pas été trouvé.", id);
            throw new NotFoundException("Le niveau de difficulté à supprimer n'a pas été trouvé.");
        }

        difficultyLevelRepository.deleteById(id);
    }


    public void toggleDisable(long id, ToggleDisableRequestDTO toggleDisableRequestDTO) {
        DifficultyLevel difficultyLevel = difficultyLevelRepository.findById(id).orElseThrow(() -> {
            logger.warn("Le niveau de difficulté avec l'ID {}, n'a pas été trouvé.", id);
            return new NotFoundException("Le niveau de difficulté n'a pas été trouvé.");
        });

        difficultyLevel.setDisabledAt(toggleDisableRequestDTO.isDisabled() ? LocalDate.now() : null);

        difficultyLevelRepository.save(difficultyLevel);
    }

}
