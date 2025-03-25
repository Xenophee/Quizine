package com.dassonville.api.mapper;


import com.dassonville.api.dto.ThemeAdminDTO;
import com.dassonville.api.dto.ThemePublicDTO;
import com.dassonville.api.dto.ThemeUpsertDTO;
import com.dassonville.api.model.Theme;
import com.dassonville.api.projection.PublicThemeProjection;
import com.dassonville.api.util.DateUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.time.LocalDate;
import java.util.List;

import static com.dassonville.api.config.Constant.NEWNESS_THRESHOLD_DAYS;


@Mapper(componentModel = "spring")
public interface ThemeMapper {


    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "disabledAt", ignore = true),
            @Mapping(target = "categories", ignore = true)
    })
    Theme toModel(ThemeUpsertDTO dto);

    @Mappings({
            @Mapping(target = "isNew", expression = "java(isThemeNew(theme.getCreatedAt()))")
    })
    ThemePublicDTO toPublicDTO(PublicThemeProjection theme);

    List<ThemePublicDTO> toPublicDTOList(List<PublicThemeProjection> themes);

    ThemeAdminDTO toAdminDTO(Theme theme);

    List<ThemeAdminDTO> toAdminDTOList(List<Theme> themes);

    ThemeUpsertDTO toUpsertDTO(Theme theme);


    default boolean isThemeNew(LocalDate createdAt) {
        return DateUtils.isNew(createdAt, NEWNESS_THRESHOLD_DAYS);
    }
}
