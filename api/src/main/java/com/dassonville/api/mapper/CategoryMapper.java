package com.dassonville.api.mapper;


import com.dassonville.api.dto.response.CategoryAdminDTO;
import com.dassonville.api.dto.request.CategoryUpsertDTO;
import com.dassonville.api.model.Category;
import com.dassonville.api.model.Theme;
import com.dassonville.api.util.TextUtils;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mappings({
            @Mapping(target = "name", expression = "java(normalizeText(dto.name()))"),
            @Mapping(target = "description", expression = "java(normalizeText(dto.description()))"),
            @Mapping(target = "theme", expression = "java(mapThemeIdToTheme(themeId))"),
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "disabledAt", ignore = true),
    })
    Category toModel(CategoryUpsertDTO dto, @Context long themeId);

    @Mappings({
            @Mapping(target = "themeId", source = "theme.id")
    })
    CategoryAdminDTO toAdminDTO(Category model);

    @Mappings({
            @Mapping(target = "name", expression = "java(normalizeText(dto.name()))"),
            @Mapping(target = "description", expression = "java(normalizeText(dto.description()))")
    })
    void updateModelFromDTO(CategoryUpsertDTO dto, @MappingTarget Category model);


    // Pas de @Named ici car utilisation d'un @Context
    default Theme mapThemeIdToTheme(long themeId) {
        return new Theme(themeId);
    }

    @Named("normalizeText")
    default String normalizeText(String input) {
        return TextUtils.normalizeText(input);
    }
}
