package com.dassonville.api.service;

import com.dassonville.api.dto.response.DifficultyLevelAdminDTO;
import com.dassonville.api.dto.response.DifficultyLevelPublicDTO;
import com.dassonville.api.exception.ErrorCode;
import com.dassonville.api.exception.MisconfiguredException;
import com.dassonville.api.exception.NotFoundException;
import com.dassonville.api.mapper.DifficultyLevelMapper;
import com.dassonville.api.model.DifficultyLevel;
import com.dassonville.api.projection.PublicDifficultyLevelProjection;
import com.dassonville.api.repository.DifficultyLevelRepository;
import com.dassonville.api.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class DifficultyLevelService {

    private final DifficultyLevelRepository difficultyLevelRepository;
    private final DifficultyLevelMapper difficultyLevelMapper;

    private final QuizRepository quizRepository;


    /**
     * Récupère tous les niveaux de difficulté triés par ordre d'affichage.
     *
     * @return une liste de {@link DifficultyLevelAdminDTO} contenant les niveaux de difficulté
     */
    public List<DifficultyLevelAdminDTO> getAllDifficultyLevels() {
        List<DifficultyLevel> difficultyLevels = difficultyLevelRepository.findAllByOrderByRank();
        return difficultyLevelMapper.toAdminDTOList(difficultyLevels);
    }


    /**
     * Récupère tous les niveaux de difficulté actifs triés par ordre d'affichage.
     *
     * @return une liste de {@link DifficultyLevelPublicDTO} contenant les niveaux de difficulté actifs
     * @throws NotFoundException si le quiz avec l'ID spécifié n'existe pas ou est désactivé
     * @throws MisconfiguredException si aucun niveau de difficulté actif n'est trouvé pour le quiz spécifié
     */
    public List<DifficultyLevelPublicDTO> getAllActiveDifficultyLevels(long quizId) {

        if (!quizRepository.existsByIdAndDisabledAtIsNull(quizId)) {
            log.warn("Le quiz avec l'ID {} n'existe pas ou est désactivé.", quizId);
            throw new NotFoundException(ErrorCode.QUIZ_NOT_FOUND);
        }

        List<PublicDifficultyLevelProjection> difficultyLevels = difficultyLevelRepository.findByDisabledAtIsNullOrderByRank(quizId);

        if (difficultyLevels.isEmpty()) {
            log.warn("Aucun niveau de difficulté actif trouvé pour le quiz avec l'ID {}. Certains paramètres semblent inexistants ou désactivés.", quizId);
            throw new MisconfiguredException(ErrorCode.QUIZ_MISCONFIGURED);
        }

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
                    log.warn("Le niveau de difficulté avec l'ID {}, n'a pas été trouvé.", id);
                    return new NotFoundException(ErrorCode.DIFFICULTY_NOT_FOUND);
                });
    }


    @Scheduled(cron = "0 0 0 * * *")
    public void disableExpiredSpecialLevels() {
        log.info("Désactivation des niveaux de difficulté spéciaux expirés...");
        difficultyLevelRepository.disableExpiredSpecialLevels();
        log.info("Désactivation des niveaux de difficulté spéciaux expirés terminée.");
    }


}
