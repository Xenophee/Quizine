package com.dassonville.api.mapper;


import com.dassonville.api.dto.ThemeDTO;
import com.dassonville.api.dto.ThemeUpsertDTO;
import com.dassonville.api.model.Theme;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


@Mapper(componentModel = "spring")
public interface ThemeMapper {


    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "disabledAt", ignore = true)
    })
    Theme toModel(ThemeUpsertDTO dto);

    ThemeDTO toDTO(Theme theme);

    ThemeUpsertDTO toUpsertDTO(Theme theme);
}
