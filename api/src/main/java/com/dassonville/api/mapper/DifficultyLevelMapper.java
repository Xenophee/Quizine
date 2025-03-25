package com.dassonville.api.mapper;


import com.dassonville.api.dto.DifficultyLevelAdminDTO;
import com.dassonville.api.dto.DifficultyLevelPublicDTO;
import com.dassonville.api.dto.DifficultyLevelUpsertDTO;
import com.dassonville.api.model.DifficultyLevel;
import com.dassonville.api.projection.PublicDifficultyLevelProjection;
import com.dassonville.api.util.DateUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.time.LocalDate;
import java.util.List;

import static com.dassonville.api.constant.AppConstants.NEWNESS_THRESHOLD_DAYS;

@Mapper(componentModel = "spring")
public interface DifficultyLevelMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "disabledAt", ignore = true)
    })
    DifficultyLevel toModel(DifficultyLevelUpsertDTO dto);

    @Mappings({
            @Mapping(target = "isNew", expression = "java(isDifficultyLevelNew(difficultyLevel.getCreatedAt()))")
    })
    DifficultyLevelPublicDTO toPublicDTO(PublicDifficultyLevelProjection difficultyLevel);

    List<DifficultyLevelPublicDTO> toPublicDTOList(List<PublicDifficultyLevelProjection> difficultyLevels);

    DifficultyLevelAdminDTO toAdminDTO(DifficultyLevel difficultyLevel);

    List<DifficultyLevelAdminDTO> toAdminDTOList(List<DifficultyLevel> difficultyLevels);

    DifficultyLevelUpsertDTO toUpsertDto(DifficultyLevel difficultyLevel);


    default boolean isDifficultyLevelNew(LocalDate createdAt) {
        return DateUtils.isNew(createdAt, NEWNESS_THRESHOLD_DAYS);
    }
}
