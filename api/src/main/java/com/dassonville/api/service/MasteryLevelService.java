package com.dassonville.api.service;


import com.dassonville.api.projection.IdAndNameProjection;
import com.dassonville.api.repository.MasteryLevelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MasteryLevelService {

    private final MasteryLevelRepository masteryLevelRepository;


    /**
     * Récupère tous les niveaux de maîtrise disponibles dans la base de données.
     *
     * @return une list de {@link IdAndNameProjection } représentant les niveaux de maîtrise avec leur ID et nom.
     */
    public List<IdAndNameProjection> findAllMasteryLevel() {
        return masteryLevelRepository.findAllByOrderByRank();
    }
}
