package com.dassonville.api.mapper;


import com.dassonville.api.dto.CategoryAdminDTO;
import com.dassonville.api.dto.CategoryUpsertDTO;
import com.dassonville.api.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "disabledAt", ignore = true),
            @Mapping(target = "theme.id", source = "themeId")
    })
    Category toModel(CategoryUpsertDTO dto);

    @Mappings({
            @Mapping(target = "themeId", source = "theme.id")
    })
    CategoryAdminDTO toAdminDTO(Category category);

    @Mappings({
            @Mapping(target = "themeId", source = "theme.id")
    })
    CategoryUpsertDTO toUpsertDTO(Category category);
}
