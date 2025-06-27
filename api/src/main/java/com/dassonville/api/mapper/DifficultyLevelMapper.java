package com.dassonville.api.mapper;


import com.dassonville.api.dto.response.DifficultyLevelAdminDTO;
import com.dassonville.api.dto.response.DifficultyLevelPublicDTO;
import com.dassonville.api.model.DifficultyLevel;
import com.dassonville.api.projection.PublicDifficultyLevelProjection;
import com.dassonville.api.util.DateUtils;
import com.dassonville.api.util.TextUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.util.List;

import static com.dassonville.api.constant.AppConstants.NEWNESS_THRESHOLD_DAYS;

@Mapper(componentModel = "spring")
public interface DifficultyLevelMapper {

    @Mappings({
            @Mapping(target = "isNew", expression = "java(isDifficultyLevelNew(projection.getCreatedAt()))")
    })
    DifficultyLevelPublicDTO toPublicDTO(PublicDifficultyLevelProjection projection);

    List<DifficultyLevelPublicDTO> toPublicDTOList(List<PublicDifficultyLevelProjection> projections);

    DifficultyLevelAdminDTO toAdminDTO(DifficultyLevel model);

    List<DifficultyLevelAdminDTO> toAdminDTOList(List<DifficultyLevel> models);


    default boolean isDifficultyLevelNew(LocalDateTime createdAt) {
        return DateUtils.isNew(createdAt, NEWNESS_THRESHOLD_DAYS);
    }

    @Named("normalizeText")
    default String normalizeText(String input) {
        return TextUtils.normalizeText(input);
    }
}
