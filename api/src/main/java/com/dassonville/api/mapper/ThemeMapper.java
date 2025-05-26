package com.dassonville.api.mapper;


import com.dassonville.api.dto.ThemeAdminDTO;
import com.dassonville.api.dto.ThemePublicDTO;
import com.dassonville.api.dto.ThemeUpsertDTO;
import com.dassonville.api.model.Theme;
import com.dassonville.api.projection.PublicThemeProjection;
import com.dassonville.api.util.DateUtils;
import com.dassonville.api.util.TextUtils;
import org.mapstruct.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.dassonville.api.constant.AppConstants.NEWNESS_THRESHOLD_DAYS;


@Mapper(componentModel = "spring")
public interface ThemeMapper {


    @Mappings({
            @Mapping(target = "name", expression = "java(normalizeText(dto.name()))"),
            @Mapping(target = "description", expression = "java(normalizeText(dto.description()))"),
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "disabledAt", ignore = true),
            @Mapping(target = "categories", ignore = true)
    })
    Theme toModel(ThemeUpsertDTO dto);

    @Mappings({
            @Mapping(target = "isNew", expression = "java(isThemeNew(projection.getCreatedAt()))")
    })
    ThemePublicDTO toPublicDTO(PublicThemeProjection projection);

    List<ThemePublicDTO> toPublicDTOList(List<PublicThemeProjection> projections);

    ThemeAdminDTO toAdminDTO(Theme model);

    List<ThemeAdminDTO> toAdminDTOList(List<Theme> models);


    @Mappings({
            @Mapping(target = "name", expression = "java(normalizeText(dto.name()))"),
            @Mapping(target = "description", expression = "java(normalizeText(dto.description()))"),
    })
    void updateModelFromDTO(ThemeUpsertDTO dto, @MappingTarget Theme model);


    default boolean isThemeNew(LocalDateTime createdAt) {
        return DateUtils.isNew(createdAt, NEWNESS_THRESHOLD_DAYS);
    }

    @Named("normalizeText")
    default String normalizeText(String input) {
        return TextUtils.normalizeText(input);
    }
}
