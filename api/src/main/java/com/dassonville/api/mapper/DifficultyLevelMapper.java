package com.dassonville.api.mapper;


import com.dassonville.api.dto.DifficultyLevelAdminDTO;
import com.dassonville.api.dto.DifficultyLevelPublicDTO;
import com.dassonville.api.dto.DifficultyLevelUpsertDTO;
import com.dassonville.api.model.DifficultyLevel;
import com.dassonville.api.projection.PublicDifficultyLevelProjection;
import com.dassonville.api.util.DateUtils;
import com.dassonville.api.util.TextUtils;
import org.mapstruct.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.dassonville.api.constant.AppConstants.NEWNESS_THRESHOLD_DAYS;

@Mapper(componentModel = "spring")
public interface DifficultyLevelMapper {

    @Mappings({
            @Mapping(target = "name", expression = "java(normalizeText(dto.name()))"),
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "disabledAt", ignore = true)
    })
    DifficultyLevel toModel(DifficultyLevelUpsertDTO dto);

    @Mappings({
            @Mapping(target = "isNew", expression = "java(isDifficultyLevelNew(projection.getCreatedAt()))")
    })
    DifficultyLevelPublicDTO toPublicDTO(PublicDifficultyLevelProjection projection);

    List<DifficultyLevelPublicDTO> toPublicDTOList(List<PublicDifficultyLevelProjection> projections);

    DifficultyLevelAdminDTO toAdminDTO(DifficultyLevel model);

    List<DifficultyLevelAdminDTO> toAdminDTOList(List<DifficultyLevel> models);

    @Mappings({
            @Mapping(target = "name", expression = "java(normalizeText(dto.name()))"),
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "isReference", ignore = true),
            @Mapping(target = "displayOrder", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "disabledAt", ignore = true),
    })
    void updateModelFromDTO(DifficultyLevelUpsertDTO dto, @MappingTarget DifficultyLevel model);


    default boolean isDifficultyLevelNew(LocalDateTime createdAt) {
        return DateUtils.isNew(createdAt, NEWNESS_THRESHOLD_DAYS);
    }

    @Named("normalizeText")
    default String normalizeText(String input) {
        return TextUtils.normalizeText(input);
    }
}
