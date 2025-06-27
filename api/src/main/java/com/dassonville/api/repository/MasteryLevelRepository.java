package com.dassonville.api.repository;


import com.dassonville.api.model.MasteryLevel;
import com.dassonville.api.projection.IdAndNameProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MasteryLevelRepository extends JpaRepository<MasteryLevel, Long> {

    /**
     * Récupère tous les niveaux de maîtrise triés par ordre de rang.
     *
     * @return une {@link IdAndNameProjection} qui contient l'ID et le nom de chaque niveau de maîtrise, triés par rang.
     */
    List<IdAndNameProjection> findAllByOrderByRank();
}
